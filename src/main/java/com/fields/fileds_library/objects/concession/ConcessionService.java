package com.fields.fileds_library.objects.concession;

import com.fields.fileds_library.model.ConcessionDto;
import com.fields.fileds_library.model.CompanyDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConcessionService {

    ConcessionDto createConcession(ConcessionDto concessionDto);

    void deleteConcession(UUID id);

//    List<ConcessionDto> findAllConcession(String companyName);

    Optional<ConcessionDto> findConcessionById(UUID id);

    ConcessionDto updateConcession(UUID id, ConcessionDto concessionDto);

}
