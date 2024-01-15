package br.com.proccedure.unicasu.auth;

import br.com.proccedure.unicasu.auth.security.jwt.JwtBlacklistService;
import br.com.proccedure.unicasu.auth.security.jwt.JwtUtils;
import br.com.proccedure.unicasu.auth.security.services.UserDetailsImpl;
import br.com.proccedure.unicasu.auth.security.services.UserPasswordService;
import br.com.proccedure.unicasu.mail.EmailService;
import br.com.proccedure.unicasu.usuario.Usuario;
import br.com.proccedure.unicasu.usuario.UsuarioAutenticado;
import br.com.proccedure.unicasu.usuario.UsuarioRepository;
import br.com.proccedure.unicasu.usuario.UsuarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtBlacklistService jwtBlacklistService;

    @Autowired
    UserPasswordService userPasswordService;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestParams authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwt(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> tiposUsuario = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        if (tiposUsuario.get(0) == "ROLE_AGENTE_ENDEMIAS") {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new AuthResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getCpf(),
                userDetails.getCnpj(),
                userDetails.getEmail(),
                userDetails.getCidade(),
                userDetails.getEstado(),
                tiposUsuario.get(0),
                userDetails.getUbs()
        ));
    }

    @PostMapping("/mobile/login")
    public ResponseEntity<?> authenticateUserMobile(@Valid @RequestBody AuthRequestParams authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwt(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> tiposUsuario = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        if (tiposUsuario.get(0) != "ROLE_AGENTE_ENDEMIAS") {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new AuthResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getCpf(),
                userDetails.getCnpj(),
                userDetails.getEmail(),
                userDetails.getCidade(),
                userDetails.getEstado(),
                tiposUsuario.get(0),
                null));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestParams logoutParams) {
        jwtBlacklistService.addTokenToBlacklist(logoutParams.getToken());
    }

    @GetMapping("/user-by-token")
    public ResponseEntity<UsuarioAutenticado> getAuthenticatedUserByToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        if (jwtUtils.validateJwt(token)) {
            UserDetailsImpl userDetails = jwtUtils.getUserDetailsFromJwt(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String newToken = jwtUtils.generateJwt(authentication);
            Usuario usuario = usuarioService.findByIdOrThrow(userDetails.getId());
            UsuarioAutenticado authenticatedUser = new UsuarioAutenticado(usuario, newToken);
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

