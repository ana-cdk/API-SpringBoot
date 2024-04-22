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

import br.edu.utfpr.apidemo.dto.LeituraDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Leitura;
import br.edu.utfpr.apidemo.service.LeituraService;

@RestController
@RequestMapping("/leitura")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    /**
     * Obter 1 pessoa pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> GetById(@PathVariable("id") long id) {
       var leitura = leituraService.getById(id);

       return leitura.isPresent()
            ? ResponseEntity.ok().body(leitura.get())
            : ResponseEntity.notFound().build();
    }

    /**
     *  Obter todos as pessoas do DB
     */
    @GetMapping
    public List<Leitura> getAll() {
        return leituraService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody LeituraDTO dto) {
        try {
            var res = leituraService.create(dto);

            // Seta o status para 201 (created) e devolve o objeto pessoa em json
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception ex) {
            // Seta o status para 400 (bad request) e devolve a mensagem de exceção lançada
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id,
        @RequestBody LeituraDTO dto) {
            try {
            return ResponseEntity.ok().body(leituraService.update(id, dto));
            }catch(NotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }catch(Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") long id){
        try {
            leituraService.delete(id);
            return ResponseEntity.ok().build();
            } catch(NotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }catch(Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }
}
