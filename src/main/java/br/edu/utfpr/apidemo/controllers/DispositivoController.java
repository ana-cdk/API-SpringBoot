package br.edu.utfpr.apidemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.apidemo.dto.DispositivoDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Dispositivo;
import br.edu.utfpr.apidemo.service.DispositivoService;

@RestController
@RequestMapping("/dispositivo")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;
    /**
     * Obter 1 pessoa pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> GetById(@PathVariable("id") long id) {
       var dispositivo = dispositivoService.getById(id);

       return dispositivo.isPresent()
            ? ResponseEntity.ok().body(dispositivo.get())
            : ResponseEntity.notFound().build();
    }

    /**
     *  Obter todos as pessoas do DB
     */
    @GetMapping
    public List<Dispositivo> getAll() {
        return dispositivoService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DispositivoDTO dto) {
        try {
            var res = dispositivoService.create(dto);

            // Seta o status para 201 (created) e devolve o objeto pessoa em json
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception ex) {
            // Seta o status para 400 (bad request) e devolve a mensagem de exceção lançada
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id,
        @RequestBody DispositivoDTO dto) {
            try {
            return ResponseEntity.ok().body(dispositivoService.update(id, dto));
            }catch(NotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }catch(Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") long id){
        try {
            dispositivoService.delete(id);
            return ResponseEntity.ok().build();
            } catch(NotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }catch(Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
}
