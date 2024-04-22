package br.edu.utfpr.apidemo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/")
public class IndexController {
    
    @GetMapping
    public String hi() {
        return "Ta funcionando essa bagaça";
    }
}
