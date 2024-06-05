package br.edu.utfpr.apidemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.apidemo.dto.LeituraDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Dispositivo;
import br.edu.utfpr.apidemo.model.Leitura;
import br.edu.utfpr.apidemo.repository.LeituraRepository;
import br.edu.utfpr.apidemo.repository.SensorRepository;
import jakarta.transaction.Transactional;

@Service
public class LeituraService {
    
    @Autowired
    private LeituraRepository leituraRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Inserir uma leitura no DB
     * @return
     * @throws NotFoundException 
     */
    public Leitura create(LeituraDTO dto) throws NotFoundException {
        var leitura = new Leitura();
        BeanUtils.copyProperties(dto, leitura);

        // Usando a data e hora atual no momento da criação
        leitura.setData(LocalDateTime.now());
        
        var sensor = sensorRepository.findById(dto.idSensor());
        if(sensor.isPresent()) {
            leitura.setSensor(sensor.get());
        } else {
            throw new NotFoundException("Sensor não existe");
        }

        // Persistir no DB
        return leituraRepository.save(leitura);
    }

    public List<Leitura> getAll() {
        return leituraRepository.findAll();
    }

    public Optional<Leitura> getById(long id) {
        return leituraRepository.findById(id);
    }

    public Leitura update(long id, LeituraDTO dto) throws NotFoundException{
        var res = leituraRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Gateway " + id + " não existe");
        }

        var leitura = res.get();
        leitura.setValor(dto.valor());
        //leitura.setData(dto.data());

        return leituraRepository.save(leitura);
    }

    @Transactional
    public void delete(long id) throws NotFoundException {
        var res = leituraRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Leitura " + id + " não existe");
        }

        Leitura leitura = res.get();
        
        // Remove a leitura da lista de leituras do sensor
        if (leitura.getSensor() != null) {
            leitura.getSensor().getLeituras().remove(leitura);
        }

        leituraRepository.delete(leitura);
    }


    public List<Leitura> findLeituraBySensorId(long idSensor) {
        return leituraRepository.findBySensorIdSensor(idSensor);
    }
}
