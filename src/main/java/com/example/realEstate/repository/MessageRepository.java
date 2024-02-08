package com.example.realEstate.repository;


import com.example.realEstate.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByOwnerIdAndCustomerIdAndPropertyId(Long ownerId, Long customerId, Long propertyId);
}

