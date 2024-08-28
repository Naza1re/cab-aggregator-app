package org.example.hiringdriverservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.hiringdriverservice.dto.DriverApplyRequest;
import org.example.hiringdriverservice.dto.DriverApplyResponse;
import org.example.hiringdriverservice.model.DriverApply;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverApplyMapper {

    private final ModelMapper mapper;

    public DriverApplyResponse fromEntityToResponse(DriverApply apply) {
        return mapper.map(apply, DriverApplyResponse.class);
    }

    public DriverApply fromRequestToEntity(DriverApplyRequest request) {
        return mapper.map(request, DriverApply.class);
    }
}
