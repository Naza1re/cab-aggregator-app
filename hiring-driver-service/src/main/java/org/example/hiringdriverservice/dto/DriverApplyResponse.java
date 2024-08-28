package org.example.hiringdriverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverApplyResponse {
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
