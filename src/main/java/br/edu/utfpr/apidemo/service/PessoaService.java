package br.edu.utfpr.apidemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.utfpr.apidemo.dto.PessoaDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Pessoa;
import br.edu.utfpr.apidemo.repository.PessoaRepository;;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

     @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 
     * Inserir uma pessoa no DB
     * @return
     */
    public Pessoa create(PessoaDTO dto) {
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(dto, pessoa);

        pessoa.setSenha(passwordEncoder.encode(dto.getSenha()));

        // Persistir no DB
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getById(long id) {
        return pessoaRepository.findById(id);
    }

    public Optional<Pessoa> findByEmail(String email) {
        return pessoaRepository.findByEmail(email);
    }

    public Pessoa update(long id, PessoaDTO dto) throws NotFoundException{
        var res = pessoaRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Pessoa " + id + " não existe");
        }

        var pessoa = res.get();
        pessoa.setNome(dto.nome());
        pessoa.setEmail(dto.email());

        return pessoaRepository.save(pessoa);
    }

    public void delete(long id) throws NotFoundException {
        var res = pessoaRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Pessoa " + id + " não existe");
        }
        pessoaRepository.delete(res.get());
    }
    

}
