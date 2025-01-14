package com.example.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.register(account);
            return ResponseEntity.status(200).body(createdAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.login(account);
            return ResponseEntity.status(200).body(loggedInAccount);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(200).body(createdMessage);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message newMessage) {
        try {
            Message updatedMessage = messageService.updateMessage(messageId, newMessage);
            return ResponseEntity.status(200).body(1);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        try {
            int rowsDeleted = messageService.deleteMessage(messageId);
            return ResponseEntity.status(200).body(rowsDeleted);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return ResponseEntity.status(200).body(messages);
        } catch (Exception e) {
            return ResponseEntity.status(200).body(null);
        }
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId){
        try{
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.status(200).body(message);
        }catch (Exception e) {
            return ResponseEntity.status(200).body(null);
        }
    }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessagesByUser(@PathVariable Integer accountId) {
        try {
            List<Message> messages = messageService.getAllMessagesByUser(accountId);
            return ResponseEntity.status(200).body(messages);
        } catch (Exception e) {
            return ResponseEntity.status(200).body(null);
        }
    }

}
