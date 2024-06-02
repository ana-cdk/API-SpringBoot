package br.edu.utfpr.apidemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.utfpr.apidemo.dto.GatewayDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Gateway;
import br.edu.utfpr.apidemo.repository.GatewayRepository;
import br.edu.utfpr.apidemo.repository.PessoaRepository;

@Service
public class GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

        /**
     * 
     * Inserir uma dispositivo no DB
     * @return
         * @throws NotFoundException 
     */
    public Gateway create(GatewayDTO dto) throws NotFoundException {
        var gateway = new Gateway();
        BeanUtils.copyProperties(dto, gateway);

        var pessoa = pessoaRepository.findById(dto.idPessoa());
        if(pessoa.isPresent())
            gateway.setPessoa(pessoa.get());
        else
            throw new NotFoundException("Pessoa não existe");

        // Persistir no DB
        return gatewayRepository.save(gateway);
    }

    public List<Gateway> getAll() {
        return gatewayRepository.findAll();
    }

    public Optional<Gateway> getById(long id) {
        return gatewayRepository.findById(id);
    }

    public Gateway update(long id, GatewayDTO dto) throws NotFoundException{
        var res = gatewayRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Gateway " + id + " não existe");
        }

        var gateway = res.get();
        gateway.setNome(dto.nome());
        gateway.setDescricao(dto.descricao());
        gateway.setEndereco(dto.endereco());

        return gatewayRepository.save(gateway);
    }

    public void delete(long id) throws NotFoundException {
        var res = gatewayRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Gateway " + id + " não existe");
        }
        gatewayRepository.delete(res.get());
    }

    public List<Gateway> findGatewayByPessoaId(long idPessoa) {
        return gatewayRepository.findByPessoaIdPessoa(idPessoa);
    }

    

}

