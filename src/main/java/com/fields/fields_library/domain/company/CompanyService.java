package com.fields.fields_library.domain.company;

import com.fields.fields_library.model.CompanyDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyService {

    CompanyDto createCompany(CompanyDto companyDto);

    void deleteCompany(UUID id);

    List<CompanyDto> findAllCompanies(String companyName);

    Optional<CompanyDto> findCompanyById(UUID id);

    CompanyDto updateCompany(UUID id, CompanyDto companyDto);
}
