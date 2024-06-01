package br.edu.utfpr.apidemo.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.apidemo.dto.AuthDTO;
import br.edu.utfpr.apidemo.security.JwtUtil;
import br.edu.utfpr.apidemo.model.CustomUserDetails;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt_secret}")
    private String jwtSecret;

    @PostMapping
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthDTO authDTO) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(authDTO.username);
            if (userDetails == null) {
                throw new UsernameNotFoundException("User not found");
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            String userId = customUserDetails.getId().toString(); // Supondo que o ID é um Long, converta para String

            var payload = new HashMap<String, Object>();
            payload.put("username", authDTO.username);
            payload.put("userId", userId);

            var now = Instant.now();

            var jwt = jwtUtil.generateToken(payload, jwtSecret, 36000);

            var res = new HashMap<String, Object>();
            res.put("token", jwt);
            res.put("issuedIn", now);
            res.put("expiresIn", now.plus(36000, ChronoUnit.SECONDS));
            res.put("userId", userId); // Retorne o userId aqui

            return ResponseEntity.ok().body(res);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Usuário não encontrado ou senha incorreta.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro de autenticação.");
        }
    }
}