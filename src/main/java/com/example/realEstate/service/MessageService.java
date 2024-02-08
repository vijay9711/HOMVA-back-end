package com.example.realEstate.service;

import com.example.realEstate.entity.Customer;
import com.example.realEstate.entity.Owner;
import com.example.realEstate.entity.Property;
import com.example.realEstate.repository.CustomerRepository;
import com.example.realEstate.repository.MessageRepository;
import com.example.realEstate.repository.OwnerRepository;
import com.example.realEstate.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import com.example.realEstate.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private MessageRepository messageRepository;




    public void sendMessageFromOwnerToCustomer(Long customerId, Long ownerId, Long propertyId, String messageContent) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner with ID " + ownerId + " not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerId + " not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property with ID " + propertyId + " not found"));

        Message message = new Message();
        message.setOwner(owner);
        message.setCustomer(customer);
        message.setProperty(property);
        message.setContent(messageContent);

        messageRepository.save(message);
    }

    public void sendMessageFromCustomerToOwner(Long customerId, Long ownerId, Long propertyId, String messageContent) {
        // Similar logic as above, adjusting sender and recipient
    }


    public List<Message> getAllMessagesForCustomerAndOwner(Long customerId, Long ownerId, Long propertyId) {
        if (ownerId != null && customerId != null && propertyId != null) {
            // Implement logic to retrieve messages for a specific owner, customer, and property
            // For example:
            return messageRepository.findByOwnerIdAndCustomerIdAndPropertyId(ownerId, customerId, propertyId);
        } else {
            // Return empty list if any of the parameters are null
            return Collections.emptyList();
        }

    }

    public List<Message> getAllMessages() {
      return  messageRepository.findAll();
    }

    public List<Message> getAllMessagesForOwnerAndCustomer(Long ownerId, Long customerId, Long propertyId) {

        if (ownerId != null && customerId != null && propertyId != null) {
            // Implement logic to retrieve messages for a specific owner, customer, and property
            // For example:
            return messageRepository.findByOwnerIdAndCustomerIdAndPropertyId(ownerId, customerId, propertyId);
        } else {
            // Return empty list if any of the parameters are null
            return Collections.emptyList();
        }
    }
}
