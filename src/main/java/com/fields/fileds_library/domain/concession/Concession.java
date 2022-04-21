package com.fields.fileds_library.domain.concession;

import com.fields.fileds_library.model.ConcessionDto;
import com.fields.fileds_library.domain.ProtoEntity;
import com.fields.fileds_library.domain.company.Company;
import com.fields.fileds_library.domain.field.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Audited
@Entity
@Table(name = "concessions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Concession extends ProtoEntity {

    private String concessionName;
    private String concessionDescription;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "concessions_owners", joinColumns = @JoinColumn(name = "concession_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id"))
    private List<Company> owners;
    @OneToMany
    private List<Field> fields;

    public ConcessionDto toDto() {
        ConcessionDto concessionDto = new ConcessionDto();
        concessionDto.setId(this.getId());
        concessionDto.setConcessionName(this.getConcessionName());
        concessionDto.setConcessionDescription(this.getConcessionDescription());
        concessionDto.setOwners(this.getOwners().stream().map(Company::getCompanyName).collect(Collectors.toList()));
        concessionDto.setFields(this.getFields().stream().map(Field::getFieldName).collect(Collectors.toList()));
        return concessionDto;
    }

    public Concession updateConcession(List<Company> owners, List<Field> fields, ConcessionDto concessionDto) {
        this.concessionName = concessionDto.getConcessionName();
        this.concessionDescription = concessionDto.getConcessionDescription();
        this.owners = owners;
        this.fields = fields;
        return this;
    }

    public static class ConcessionBuilder {
        private String concessionName;
        private String concessionDescription;
        private List<Company> owners;
        private List<Field> fields;

        public ConcessionBuilder concessionName(String concessionName) {
            this.concessionName = concessionName;
            return this;
        }

        public ConcessionBuilder concessionDescription(String concessionDescription) {
            this.concessionDescription = concessionDescription;
            return this;
        }

        public ConcessionBuilder owners(List<Company> owners) {
            this.owners = owners;
            return this;
        }

        public ConcessionBuilder fields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public Concession build() {
            return new Concession(concessionName, concessionDescription, owners, fields);
        }
    }
}


