package org.example.hiringdriverservice.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.hiringdriverservice.dto.DriverApplyResponse;
import org.example.hiringdriverservice.service.DriverApplyService;
import org.springframework.stereotype.Component;

import static org.example.hiringdriverservice.delegate.ProcessVariablesConstants.*;

@Component
@RequiredArgsConstructor
public class ApproveApplyRequest implements JavaDelegate {

    private final DriverApplyService driverApplyService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long requesterId = (Long) delegateExecution.getVariable(REQUESTER_ID);
        Boolean isApproved = (Boolean) delegateExecution.getVariable(IS_APPROVED);
        String approver = (String) delegateExecution.getVariable(APPROVER);
        DriverApplyResponse driverApplyResponse = driverApplyService.changeStatusOfApply(requesterId,approver,isApproved);
    }
}
