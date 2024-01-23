package com.example.paymentservice.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class User {
    @Id
    @Column(name = "passenger_id")
    Long passengerId;
    @Column(name = "customer_id")
    String customerId;
}
