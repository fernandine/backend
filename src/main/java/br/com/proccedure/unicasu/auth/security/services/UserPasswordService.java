package br.com.proccedure.unicasu.auth.security.services;

import br.com.proccedure.unicasu.auth.security.PasswordTokenPublicData;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPasswordService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Token token;

    @SneakyThrows
    public String generateToken(String email) {
        Optional<Usuario> optionalUser = userRepository.findByEmail(email);
        optionalUser.ifPresent(user -> {
            KeyBasedPersistenceTokenService tokenService = getInstanceFor(user);
            token = tokenService.allocateToken(user.getEmail());
        });
        return token.getKey();
    }

    @SneakyThrows
    public void changePassword(String newPassword, String rawToken) {
        PasswordTokenPublicData publicData = readPublicData(rawToken);

        if(isExpired(publicData)) {
            throw new RuntimeException("Token expirado");
        }

        Usuario userEntity = userRepository.findByEmail(publicData.getEmail())
                .orElseThrow(EntityNotFoundException::new);

        KeyBasedPersistenceTokenService tokenService = this.getInstanceFor(userEntity);
        tokenService.verifyToken(rawToken);

        userEntity.setSenha(this.passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }

    private boolean isExpired(PasswordTokenPublicData publicData) {
        Instant createdAt = new Date(publicData.getCreateAtTimestamp()).toInstant();
        Instant now = new Date().toInstant();
        return createdAt.plus(Duration.ofMinutes(30)).isBefore(now);
    }

    private KeyBasedPersistenceTokenService getInstanceFor(Usuario user) {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
        try {
            tokenService.setServerSecret(user.getSenha());
            tokenService.setServerInteger(16);
            tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenService;
    }

    private PasswordTokenPublicData readPublicData(String rawToken) {
        String rawTokenDecoded = new String(Base64.getDecoder().decode(rawToken));
        String[] tokenParts = rawTokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];
        return new PasswordTokenPublicData(email, timestamp);
    }

}
