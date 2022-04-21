package com.fields.fileds_library.domain.company;


import com.fields.fileds_library.model.CompanyDto;
import com.fields.fileds_library.domain.ProtoEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Audited
@Entity
@Table(name = "owners")
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
