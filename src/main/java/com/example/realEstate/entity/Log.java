package com.example.realEstate.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "method_name")
    private String methodName;

    // Constructors, getters, and setters

    public Log(LocalDateTime dateTime, String userEmail, String methodName) {
        this.dateTime = dateTime;
        this.userEmail = userEmail;
        this.methodName = methodName;
    }


}
