package br.com.proccedure.unicasu.mail;

import br.com.proccedure.unicasu.infra.exceptions.UnicasuException;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${spring.mail.username}")
    private String defaultSender;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PassRecoverRepository passwordRecoverRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createRecoverToken(EmailDto body) {

        Optional<Usuario> user = userRepository.findByEmail(body.getEmail());
        if (!user.isPresent()) {
            throw new UnicasuException("Não foi possível encontrar um usuário associado a este e-mail.");
        }

        String recoveryToken = UUID.randomUUID().toString();

        PasswordRecover recoveryEntity = new PasswordRecover();
        recoveryEntity.setToken(recoveryToken);
        recoveryEntity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        recoveryEntity.setEmail(body.getEmail());

        passwordRecoverRepository.save(recoveryEntity);

        String recoveryLink = recoverUri + recoveryToken;
        String emailText = String.format(
                "Olá %s,\n\n"
                        + "Recebemos uma solicitação para redefinir a senha associada a este e-mail.\n"
                        + "Clique no link a seguir para definir uma nova senha:\n\n"
                        + "%s\n\n"
                        + "Este link é válido por %d minutos.\n\n"
                        + "Se você não solicitou a recuperação da senha, ignore este e-mail.\n\n"
                        + "Atenciosamente,\n"
                        + "Sua Equipe de Suporte Unica",
                user.get().getNome(), recoveryLink, tokenMinutes);

        emailService.sendEmail(body.getEmail(), "Recuperação de Senha", emailText);
    }

    @Transactional
    public void saveNewPassword(NewPasswordDto body) {
        try {
            List<PasswordRecover> list = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());

            if (list.size() == 0) {
                throw new UnicasuException("Invalid token");
            }

            Optional<Usuario> optionalUser = userRepository.findByEmail(list.get(0).getEmail());

            optionalUser.ifPresent(user -> {
                user.setSenha(passwordEncoder.encode(body.getPassword()));
                userRepository.save(user);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnicasuException("Erro ao processar a alteração de senha");
        }
    }
}
