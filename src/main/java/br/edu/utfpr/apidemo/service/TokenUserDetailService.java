package br.edu.utfpr.apidemo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.edu.utfpr.apidemo.model.CustomUserDetails;
import br.edu.utfpr.apidemo.model.Pessoa;
import br.edu.utfpr.apidemo.repository.PessoaRepository;

@Component
public class TokenUserDetailService implements UserDetailsService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(pessoa.getEmail(), pessoa.getSenha(), pessoa.getIdPessoa());
    }
}