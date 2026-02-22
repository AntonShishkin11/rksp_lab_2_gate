package ru.shishkin.gate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentGateController implements ru.shishkin.gate.api.StudentsApi {

    private final ru.shishkin.gate.client.api.StudentsApi studentsFeignClient;

    @Override
    public ResponseEntity<ru.shishkin.gate.model.StudentGateResponse> createStudent(
            ru.shishkin.gate.model.StudentGateCreateRequest request
    ) {
        ru.shishkin.gate.client.model.StudentDataCreateRequest dataReq =
                new ru.shishkin.gate.client.model.StudentDataCreateRequest();
        dataReq.setFullName(request.getFullName());
        dataReq.setPassport(request.getPassport());

        // ✅ Тут возвращается МОДЕЛЬ, не ResponseEntity
        ru.shishkin.gate.client.model.StudentDataResponse dataResp =
                studentsFeignClient.createStudentDataInData(dataReq);

        ru.shishkin.gate.model.StudentGateResponse gateResp =
                new ru.shishkin.gate.model.StudentGateResponse();
        gateResp.setId(dataResp.getId());
        gateResp.setFullName(dataResp.getFullName());
        gateResp.setPassport(dataResp.getPassport());

        return ResponseEntity.status(201).body(gateResp);
    }

    @Override
    public ResponseEntity<ru.shishkin.gate.model.StudentGateResponse> getStudentById(Long id) {
        // ✅ Тут тоже возвращается МОДЕЛЬ
        ru.shishkin.gate.client.model.StudentDataResponse dataResp =
                studentsFeignClient.getStudentByIdFromData(id);

        if (dataResp == null) {
            return ResponseEntity.notFound().build();
        }

        ru.shishkin.gate.model.StudentGateResponse gateResp =
                new ru.shishkin.gate.model.StudentGateResponse();
        gateResp.setId(dataResp.getId());
        gateResp.setFullName(dataResp.getFullName());
        gateResp.setPassport(dataResp.getPassport());

        return ResponseEntity.ok(gateResp);
    }
}