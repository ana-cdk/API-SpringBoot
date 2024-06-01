package br.edu.utfpr.apidemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.apidemo.dto.SensorDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Atuador;
import br.edu.utfpr.apidemo.model.Sensor;
import br.edu.utfpr.apidemo.repository.DispositivoRepository;
import br.edu.utfpr.apidemo.repository.SensorRepository;

@Service
public class SensorService{

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;
    /**
     * 
     * Inserir uma pessoa no DB
     * @return
     * @throws NotFoundException 
     */
    public Sensor create(SensorDTO dto) throws NotFoundException {
        var sensor = new Sensor();
        BeanUtils.copyProperties(dto, sensor);

        var dispositivo = dispositivoRepository.findById(dto.idDispositivo());
        if(dispositivo.isPresent())
            sensor.setDispositivo(dispositivo.get());
        else
            throw new NotFoundException("Dispositivo não existe");

        // Persistir no DB
        return sensorRepository.save(sensor);
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getById(long id) {
        return sensorRepository.findById(id);
    }

    public Sensor update(long id, SensorDTO dto) throws NotFoundException{
        var res = sensorRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Pessoa " + id + " não existe");
        }

        var sensor = res.get();
        sensor.setNome(dto.nome());
        sensor.setTipo(dto.tipo());

        return sensorRepository.save(sensor);
    }

    @Transactional
    public void delete(long id) throws NotFoundException {
        var res = sensorRepository.findById(id);

        if(res.isEmpty()){
            throw new NotFoundException("Sensor " + id + " não existe");
        }
        Sensor sensor = res.get();
        
        if (sensor.getDispositivo() != null) {
            sensor.getDispositivo().getSensores().remove(sensor);
        }

        sensorRepository.delete(sensor);
    }

    public List<Sensor> findSensorByDispositivoId(long idDispositivo) {
        return sensorRepository.findByDispositivoIdDispositivo(idDispositivo);
    }

    //public Sensor findById(Long id) {
      //  Sensor sensor = sensorRepository.findById(id).orElse(null);
      //  if (sensor != null) {
     //       sensor.getLeituras().size(); // Carrega os dispositivos
     //   }
      //  return sensor;
    //}
}
