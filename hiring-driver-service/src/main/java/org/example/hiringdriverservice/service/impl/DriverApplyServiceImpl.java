package org.example.hiringdriverservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hiringdriverservice.dto.DriverApplyRequest;
import org.example.hiringdriverservice.dto.DriverApplyResponse;
import org.example.hiringdriverservice.exception.DriverApplyNotFoundException;
import org.example.hiringdriverservice.mapper.DriverApplyMapper;
import org.example.hiringdriverservice.model.DriverApply;
import org.example.hiringdriverservice.repository.DriverApplyRepository;
import org.example.hiringdriverservice.service.DriverApplyService;
import org.springframework.stereotype.Service;

import static org.example.hiringdriverservice.utill.ExceptionMessages.DRIVER_APPLY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DriverApplyServiceImpl implements DriverApplyService {

    private final DriverApplyRepository driverApplyRepository;
    private final DriverApplyMapper mapper;

    @Override
    public DriverApplyResponse createRecordAboutApply(DriverApplyRequest request) {
        DriverApply driverToSave = mapper.fromRequestToEntity(request);
        DriverApply savedDriverApply = driverApplyRepository.save(driverToSave);
        return mapper.fromEntityToResponse(savedDriverApply);
    }

    @Override
    public DriverApplyResponse changeStatusOfApply(Long requesterId, String approver, Boolean isApproved) {
        DriverApply driverApply = getOrThrow(requesterId);
        driverApply.setApprover(approver);
        driverApply.setIsApproved(isApproved);
        return mapper.fromEntityToResponse(driverApply);
    }

    private DriverApply getOrThrow(Long requesterId) {
        return driverApplyRepository.findByRequesterId(requesterId)
                .orElseThrow(() -> new DriverApplyNotFoundException(String.format(DRIVER_APPLY_NOT_FOUND, requesterId)));
    }
}
