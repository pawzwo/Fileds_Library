package com.fields.fileds_library.domain.concession;

import com.fields.fileds_library.exceptions.CompanyNotFoundException;
import com.fields.fileds_library.exceptions.ConcessionNotFoundException;
import com.fields.fileds_library.exceptions.FieldNotFoundException;
import com.fields.fileds_library.model.ConcessionDto;
import com.fields.fileds_library.domain.company.CompanyRepository;
import com.fields.fileds_library.domain.field.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcessionServiceImpl implements ConcessionService {

    private final ConcessionRepository concessionRepository;
    private final CompanyRepository companyRepository;
    private final FieldRepository fieldRepository;

    @Override
    public ConcessionDto createConcession(ConcessionDto concessionDto) {
        Concession.ConcessionBuilder concessionBuilder = new Concession.ConcessionBuilder();
        Concession concession = concessionBuilder
                .concessionName(concessionDto.getConcessionName())
                .concessionDescription(concessionDto.getConcessionDescription())
                .owners(concessionDto.getOwners().stream()
                        .map(o -> companyRepository.findCompanyByCompanyName(o).orElseThrow(CompanyNotFoundException::new))
                        .collect(Collectors.toList()))
                .fields(concessionDto.getFields().stream()
                        .map(f -> fieldRepository.findFieldByFieldName(f).orElseThrow(FieldNotFoundException::new))
                        .collect(Collectors.toList()))
                .build();
        return concessionRepository.save(concession).toDto();
    }

    @Override
    public void deleteConcession(UUID id) {
        concessionRepository.delete(concessionRepository.findById(id).orElseThrow(ConcessionNotFoundException::new));

    }

    @Override
    public List<ConcessionDto> findAllConcession(String companyName) {
        return concessionRepository.findAllConcessions(companyName).stream()
                .map(Concession::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConcessionDto> findConcessionById(UUID id) {
        return concessionRepository.findById(id).map(Concession::toDto);
    }

    @Override
    public ConcessionDto updateConcession(UUID id, ConcessionDto concessionDto) {
        Concession concession = concessionRepository.findById(id).orElseThrow(ConcessionNotFoundException::new)
                .updateConcession(concessionDto.getOwners().stream()
                                .map(o -> companyRepository.findCompanyByCompanyName(o).orElseThrow(CompanyNotFoundException::new))
                                .collect(Collectors.toList()), concessionDto.getFields().stream()
                                .map(f -> fieldRepository.findFieldByFieldName(f).orElseThrow(FieldNotFoundException::new))
                                .collect(Collectors.toList()), concessionDto);
        return concessionRepository.save(concession).toDto();
    }


}
