package com.fields.fileds_library.objects.company;


import com.fields.fileds_library.model.CompanyDto;
import com.fields.fileds_library.objects.ProtoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "concessions")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Company extends ProtoEntity {

    private String companyName;


    public Company updateCompany(CompanyDto companyDto) {
        this.companyName=companyDto.getCompanyName();
        return this;
    }

    public CompanyDto toDto(){
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(this.getId());
        companyDto.setCompanyName(this.getCompanyName());
        return companyDto;
    }





}
