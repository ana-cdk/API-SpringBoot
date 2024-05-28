package br.edu.utfpr.apidemo.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.apidemo.dto.AuthDTO;
import br.edu.utfpr.apidemo.security.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt_secret}")
    private String jwtSecret;

    @PostMapping
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthDTO authDTO) {
        try {
            var payload = new HashMap<String, Object>();
            payload.put("useremail", authDTO.username);

            var now = Instant.now();

            var jwt = jwtUtil.generateToken(payload, jwtSecret, 36000);

            var res = new HashMap<String, Object>();
            res.put("token", jwt);
            res.put("issuedIn", now);
            res.put("expiresIn", now.plus(36000, ChronoUnit.SECONDS));
            return ResponseEntity.ok().body(res);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Usuário não encontrado ou senha incorreta.");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Erro de autenticação.");
        }
    }
}
