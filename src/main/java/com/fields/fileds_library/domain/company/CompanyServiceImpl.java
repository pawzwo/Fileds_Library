package com.fields.fileds_library.domain.company;

import com.fields.fileds_library.exceptions.CompanyNotFoundException;
import com.fields.fileds_library.model.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company.CompanyBuilder companyBuilder = new Company.CompanyBuilder();
        Company company = companyBuilder.companyName(companyDto.getCompanyName())
                .build();
        companyRepository.save(company);
        return companyRepository.save(company).toDto();
    }

    @Override
    public void deleteCompany(UUID id) {
        companyRepository.delete(companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new));
    }

    @Override
    public List<CompanyDto> findAllCompanies(String companyName) {
        return companyRepository.findAllCompanies(companyName).stream()
                .map(Company::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CompanyDto> findCompanyById(UUID id) {
        return companyRepository.findById(id).map(Company::toDto);
    }

    @Override
    public CompanyDto updateCompany(UUID id, CompanyDto companyDto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(CompanyNotFoundException::new)
                .updateCompany(companyDto);
        return companyRepository.save(company).toDto();
    }
}
