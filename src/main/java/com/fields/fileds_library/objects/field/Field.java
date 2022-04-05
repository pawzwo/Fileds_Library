package com.fields.fileds_library.objects.field;

import com.fields.fileds_library.model.ConcessionDto;
import com.fields.fileds_library.model.FieldDto;
import com.fields.fileds_library.model.HCtype;
import com.fields.fileds_library.objects.ProtoEntity;
import com.fields.fileds_library.objects.company.Company;
import com.fields.fileds_library.objects.concession.Concession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fields")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field extends ProtoEntity {

    private String fieldName;
    @Column(name="oilMMbbl")
    private Double reservesOil;
    @Column(name = "gasBcf")
    private Double reservesGas;
    @Column(name = "condensateMMbbl")
    private Double reservesCondensate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<HCtype> hydrocarbons;

    public FieldDto toDto() {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setId(this.getId());
        fieldDto.fieldName(this.getFieldName());
        fieldDto.reservesOil(this.getReservesOil());
        fieldDto.reservesGas(this.getReservesGas());
        fieldDto.reservesCondensate(this.getReservesCondensate());
        fieldDto.hydrocarbons(this.getHydrocarbons());
        return fieldDto;
    }

    public Field updateField(FieldDto fieldDto) {
        this.fieldName= fieldDto.getFieldName();
        this.hydrocarbons = fieldDto.getHydrocarbons();
        this.reservesOil = fieldDto.getReservesOil();
        this.reservesGas = fieldDto.getReservesGas();
        this.reservesCondensate = fieldDto.getReservesCondensate();
        return this;
    }

}
