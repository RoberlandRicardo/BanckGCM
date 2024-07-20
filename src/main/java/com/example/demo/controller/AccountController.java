package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.TransferDTO;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;



@RestController
@RequestMapping("/banco/conta")
public class AccountController {
    @Autowired
    private AccountService service;

    @PostMapping
    public Account adicionar(@RequestBody AccountDTO account){
        return service.adicionar(account);
    }

    @GetMapping("/{id}")
    public Account consultarConta(@PathVariable Integer id) {
        return service.consultarConta(id); 
    }

    @GetMapping("/{id}/saldo")
    public float consultarSaldo(@PathVariable Integer id) {
        return service.consultarSaldo(id); 
    }

    @PutMapping("/{id}/{value}/credito")
    public void creditar(@PathVariable Integer id, @PathVariable Float value) {
        service.creditar(id, value); 
    }

    @PutMapping("/{id}/{value}/debito")
    public void debitar(@PathVariable Integer id, @PathVariable Float value) {
        service.debitar(id, value); 
    }

    @PutMapping("/transferencia")
    public void debitar(@RequestBody TransferDTO data) {
        service.debitar(data); 
    }

    @PutMapping("/rendimento/{id}/{taxa}")
    public void renderJuros(@PathVariable int id, @PathVariable float taxa) {
        service.renderJuros(id, taxa); 
    }
    
}
