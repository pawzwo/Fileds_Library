package com.fields.fileds_library.entities.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    @Query("SELECT c FROM Company c WHERE (:companyName is null or c.companyName= :companyName)")
    List<Company> findAllCompanies(String companyName);

    @Query("SELECT c FROM Company c WHERE c.companyName= :companyName")
    Optional<Company> findCompanyByCompanyName(String companyName);


}
