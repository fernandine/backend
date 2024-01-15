package br.com.proccedure.unicasu.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDto dto) {
        authService.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDto dto) {
        authService.saveNewPassword(dto);
        return ResponseEntity.noContent().build();
    }
}
