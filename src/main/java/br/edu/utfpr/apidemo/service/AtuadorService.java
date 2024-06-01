package br.edu.utfpr.apidemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.apidemo.dto.AtuadorDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Atuador;
import br.edu.utfpr.apidemo.model.Dispositivo;
import br.edu.utfpr.apidemo.repository.AtuadorRepository;
import br.edu.utfpr.apidemo.repository.DispositivoRepository;

@Service
public class AtuadorService {

    @Autowired
    private AtuadorRepository atuadorRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Transactional
    public Atuador create(AtuadorDTO dto) throws NotFoundException {
        var atuador = new Atuador();
        BeanUtils.copyProperties(dto, atuador);

        var dispositivo = dispositivoRepository.findById(dto.idDispositivo());
        if(dispositivo.isPresent())
            atuador.setDispositivo(dispositivo.get());
        else
            throw new NotFoundException("Dispositivo não existe");

        return atuadorRepository.save(atuador);
    }

    public List<Atuador> getAll() {
        return atuadorRepository.findAll();
    }

    public Optional<Atuador> getById(long id) {
        return atuadorRepository.findById(id);
    }

    @Transactional
    public Atuador update(long id, AtuadorDTO dto) throws NotFoundException{
        var res = atuadorRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Atuador " + id + " não existe");
        }

        var atuador = res.get();
        atuador.setNome(dto.nome());

        return atuadorRepository.save(atuador);
    }

    @Transactional
    public void delete(long id) throws NotFoundException {
        var res = atuadorRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Atuador " + id + " não existe");
        }
        Atuador atuador = res.get();
        
        if (atuador.getDispositivo() != null) {
            atuador.getDispositivo().getAtuadores().remove(atuador);
        }

        atuadorRepository.delete(atuador);
    }


    public List<Atuador> findAtuadorByDispositivoId(long idDispositivo) {
        return atuadorRepository.findByDispositivoIdDispositivo(idDispositivo);
    }
}
