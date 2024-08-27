package org.example.hiringdriverservice.service;

import org.example.hiringdriverservice.dto.DriverApplyRequest;
import org.example.hiringdriverservice.dto.DriverApplyResponse;

public interface DriverApplyService {

    DriverApplyResponse createRecordAboutApply(DriverApplyRequest request);
    DriverApplyResponse changeStatusOfApply(Long requesterId,String approver,Boolean isApproved);
}
