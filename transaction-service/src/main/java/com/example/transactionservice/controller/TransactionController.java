package com.example.transactionservice.controller;

import com.example.transactionservice.dto.AmountDTO;
import com.example.transactionservice.dto.TransferInputDTO;
import com.example.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<String> deposit (@PathVariable Long accountId, @RequestBody AmountDTO amount) {
        transactionService.deposit(accountId, amount.getAmount());
        return ResponseEntity.ok("Deposito realizado correctamente");
    }

    @PostMapping("/withdrawal/{accountId}")
    public ResponseEntity<String> withdrawal (@PathVariable Long accountId, @RequestBody AmountDTO amount) {
        transactionService.withdrawal(accountId, amount.getAmount());
        return ResponseEntity.ok("Retirada realizada correctamente");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer (@RequestBody TransferInputDTO dto) {
        transactionService.transfer(dto.getSourceAccountId(), dto.getDestAccountId(), dto.getAmount());
        return ResponseEntity.ok("Transferencia realizada correctamente");
    }

}
