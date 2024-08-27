package org.example.hiringdriverservice.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.hiringdriverservice.dto.DriverApplyRequest;
import org.example.hiringdriverservice.dto.DriverApplyResponse;
import org.example.hiringdriverservice.service.DriverApplyService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccessRequest implements JavaDelegate {

    private final DriverApplyService driverApplyService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String firstname = (String) delegateExecution.getVariable(ProcessVariablesConstants.FIRST_NAME);
        String lastname = (String) delegateExecution.getVariable(ProcessVariablesConstants.LAST_NAME);
        String email = (String) delegateExecution.getVariable(ProcessVariablesConstants.EMAIL);
        String phone = (String) delegateExecution.getVariable(ProcessVariablesConstants.PHONE);
        Long requesterId = (Long) delegateExecution.getVariable(ProcessVariablesConstants.REQUESTER_ID);
        String driverLicenseNumber = (String) delegateExecution.getVariable(ProcessVariablesConstants.DRIVER_LICENSE_NUMBER);

        DriverApplyRequest request = DriverApplyRequest.builder()
                        .phone(phone)
                                .driverLicenseNumber(driverLicenseNumber)
                                        .email(email)
                                                .lastName(lastname)
                                                        .firstName(firstname)
                                                                .requesterId(requesterId)
                                                                        .build();
        DriverApplyResponse driverApplyResponse = driverApplyService.createRecordAboutApply(request);
        delegateExecution.setVariable(ProcessVariablesConstants.ID,driverApplyResponse.getId());
    }
}
