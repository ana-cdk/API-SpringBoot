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

import br.edu.utfpr.apidemo.dto.GatewayDTO;
import br.edu.utfpr.apidemo.exceptions.NotFoundException;
import br.edu.utfpr.apidemo.model.Gateway;
import br.edu.utfpr.apidemo.service.GatewayService;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> GetById(@PathVariable("id") long id) {
       var gateway = gatewayService.getById(id);

       return gateway.isPresent()
            ? ResponseEntity.ok().body(gateway.get())
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"Gateway não encontrado\"}");
    }

    @GetMapping
    public ResponseEntity<List<Gateway>> getAll() {
        List<Gateway> gateways = gatewayService.getAll();
        return ResponseEntity.ok(gateways);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody GatewayDTO dto) {
        try {
            var res = gatewayService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + ex.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody GatewayDTO dto) {
        try {
            return ResponseEntity.ok().body(gatewayService.update(id, dto));
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"" + ex.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") long id){
        try {
            gatewayService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}