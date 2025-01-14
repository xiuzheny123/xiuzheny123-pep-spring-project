package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import com.example.entity.Message;

import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        
    }

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new IllegalArgumentException("Message text cannot be blank.");
        }

        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
        }
        
        if (message.getPostedBy() == null ) {
            throw new IllegalArgumentException("PostedBy must refer to an existing user.");
        }
        return messageRepository.save(message);
    }


    public Message updateMessage(Integer messageId, Message newMessage) {
        if (newMessage == null || newMessage.getMessageText() == null || newMessage.getMessageText().isBlank()) {
            throw new IllegalArgumentException("Message text cannot be blank.");
        }
    
        if (newMessage.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
        }    
        Message existingMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message with ID does not exist"));                
                  
        existingMessage.setMessageText(newMessage.getMessageText());
        messageRepository.save(existingMessage);
    
        return existingMessage;
    }
    

    public int deleteMessage(Integer messageId) {
        
        messageRepository.deleteById(messageId);
        return 1;

    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    public Message getMessageById(Integer messageId){
        return messageRepository.findById(messageId)
        .orElseThrow(()-> new IllegalArgumentException("ID Invalid"));
    }
    public List<Message> getAllMessagesByUser(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }
    
}
