package com.fields.fileds_library.entities.company;


import com.fields.fileds_library.model.CompanyDto;
import com.fields.fileds_library.entities.ProtoEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
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
