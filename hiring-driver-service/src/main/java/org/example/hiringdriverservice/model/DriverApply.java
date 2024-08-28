package org.example.hiringdriverservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "driver_apply")
public class DriverApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long requesterId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverLicenseNumber;
    private String approver;
    private Boolean isApproved;


}
