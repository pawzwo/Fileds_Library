package com.fields.fields_library.domain.concession;

import com.fields.fields_library.model.ConcessionDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConcessionService {

    ConcessionDto createConcession(ConcessionDto concessionDto);

    void deleteConcession(UUID id);

    List<ConcessionDto> findAllConcession(String companyName);

    Optional<ConcessionDto> findConcessionById(UUID id);

    ConcessionDto updateConcession(UUID id, ConcessionDto concessionDto);

}
