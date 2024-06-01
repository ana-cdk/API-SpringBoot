package br.edu.utfpr.apidemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.apidemo.dto.DispositivoDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Dispositivo;
import br.edu.utfpr.apidemo.repository.DispositivoRepository;
import br.edu.utfpr.apidemo.repository.GatewayRepository;
import jakarta.transaction.Transactional;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private GatewayRepository gatewayRepository;

        /**
     * 
     * Inserir uma dispositivo no DB
     * @return
         * @throws NotFoundException 
     */
    public Dispositivo create(DispositivoDTO dto) throws NotFoundException {
        var dispositivo = new Dispositivo();
        BeanUtils.copyProperties(dto, dispositivo);

        var gateway = gatewayRepository.findById(dto.idGateway());
        if(gateway.isPresent())
            dispositivo.setGateway(gateway.get());
        else
            throw new NotFoundException("Gateway não existe");

        // Persistir no DB
        return dispositivoRepository.save(dispositivo);
    }

    public List<Dispositivo> getAll() {
        return dispositivoRepository.findAll();
    }

    public Optional<Dispositivo> getById(long id) {
        return dispositivoRepository.findById(id);
    }

    public Dispositivo update(long id, DispositivoDTO dto) throws NotFoundException{
        var res = dispositivoRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Dispositivo " + id + " não existe");
        }

        var dispositivo = res.get();
        dispositivo.setNome(dto.nome());
        dispositivo.setDescricao(dto.descricao());
        dispositivo.setLocalizacao(dto.localizacao());
        dispositivo.setEndereco(dto.endereco());

        return dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public void delete(long id) throws NotFoundException {
        var res = dispositivoRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Dispositivo " + id + " não existe");
        }

        Dispositivo dispositivo = res.get();
        
        // Remove o dispositivo da lista de dispositivos do gateway
        if (dispositivo.getGateway() != null) {
            dispositivo.getGateway().getDispositivos().remove(dispositivo);
        }

        dispositivoRepository.delete(dispositivo);
    }
}


 /**    @Transactional
     public Dispositivo findAtuadorById(Long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        if (dispositivo != null) {
            dispositivo.getAtuadores().size(); // Carrega os atuadores
        }
        return dispositivo;
    }
    @Transactional
    public Dispositivo findSensorById(Long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        if (dispositivo != null) {
            dispositivo.getSensores().size(); // Carrega os sensores
        }
        return dispositivo;
    }
   */ 

