package com.fields.fileds_library.entities.concession;

import com.fields.fileds_library.api.ConcessionsApi;
import com.fields.fileds_library.exceptions.ConcessionNotFoundException;
import com.fields.fileds_library.model.ConcessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ConcessionController implements ConcessionsApi {

    private final ConcessionService concessionService;

    @Override
    public ResponseEntity<ConcessionDto> createConcession(ConcessionDto concessionDto) {
        return ResponseEntity.ok(concessionService.createConcession(concessionDto));
    }

    @Override
    public ResponseEntity<Void> deleteConcession(UUID id) {
        concessionService.deleteConcession(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ConcessionDto>> findAllConcessions(String companyName) {
        return ResponseEntity.ok(concessionService.findAllConcession(companyName));
    }

    @Override
    public ResponseEntity<ConcessionDto> findConcessionById(UUID id) {
        return ResponseEntity.ok(concessionService.findConcessionById(id).orElseThrow(ConcessionNotFoundException::new));
    }

    @Override
    public ResponseEntity<ConcessionDto> updateConcession(UUID id, ConcessionDto concessionDto) {
        return ResponseEntity.ok(concessionService.updateConcession(id, concessionDto));
    }
}
