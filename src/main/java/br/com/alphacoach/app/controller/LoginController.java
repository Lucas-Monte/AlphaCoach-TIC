package br.com.alphacoach.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class LoginController {

    @GetMapping
    public String teste() {
        return "Acesso permitido! Se está vendo isso é porque, muito provavelmente, deu certo!";
    }
}
