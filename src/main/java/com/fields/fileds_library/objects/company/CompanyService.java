package com.fields.fileds_library.objects.company;

import com.fields.fileds_library.model.CompanyDto;

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
