package com.fields.fileds_library.domain.company;

import com.fields.fileds_library.api.CompaniesApi;
import com.fields.fileds_library.exceptions.CompanyNotFoundException;
import com.fields.fileds_library.model.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompaniesApi {

    private final CompanyService companyService;

    @Override
    public ResponseEntity<CompanyDto> createCompany(CompanyDto companyDto) {
        return ResponseEntity.ok(companyService.createCompany(companyDto));
    }

    @Override
    public ResponseEntity<Void> deleteCompany(UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CompanyDto>> findAllCompanies(String companyName) {
        return ResponseEntity.ok(companyService.findAllCompanies(companyName));
    }

    @Override
    public ResponseEntity<CompanyDto> findCompanyById(UUID id) {
        return ResponseEntity.ok(companyService.findCompanyById(id).orElseThrow(CompanyNotFoundException::new));
    }

    @Override
    public ResponseEntity<CompanyDto> updateCompany(UUID id, CompanyDto companyDto) {
        return ResponseEntity.ok(companyService.updateCompany(id, companyDto));
    }
}
