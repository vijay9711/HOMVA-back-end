package com.example.realEstate.controller;

import com.example.realEstate.entity.Message;
import com.example.realEstate.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/owners/{ownerId}/customers/{customerId}/properties/{propertyId}/messages")
    public ResponseEntity<String> sendMessageFromOwnerToCustomer(@PathVariable Long ownerId,
                                                             @PathVariable Long customerId,
                                                                 @PathVariable Long propertyId,
                                                                 @RequestBody String messageContent) {
        messageService.sendMessageFromOwnerToCustomer(ownerId, customerId, propertyId, messageContent);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent successfully from owner to customer.");
    }

    @PostMapping("/customers/{customerId}/owners/{ownerId}/properties/{propertyId}/messages")
    public ResponseEntity<String> sendMessageFromCustomerToOwner(@PathVariable Long customerId,
                                                                 @PathVariable Long ownerId,
                                                                 @PathVariable Long propertyId,
                                                                 @RequestBody String messageContent) {
        messageService.sendMessageFromCustomerToOwner(customerId, ownerId, propertyId, messageContent);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent successfully from customer to owner.");
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(@RequestParam(required = false) Long ownerId,
                                                        @RequestParam(required = false) Long customerId,
                                                        @RequestParam(required = false) Long propertyId) {
        List<Message> messages;

        if (ownerId != null && customerId != null && propertyId != null) {
            messages = messageService.getAllMessagesForOwnerAndCustomer(ownerId, customerId, propertyId);
        } else if (customerId != null && ownerId != null && propertyId != null) {
            messages = messageService.getAllMessagesForCustomerAndOwner(customerId, ownerId, propertyId);
        } else {
            // Return all messages if no specific IDs are provided
            messages = messageService.getAllMessages();
        }

        return ResponseEntity.ok().body(messages);
    }

}

