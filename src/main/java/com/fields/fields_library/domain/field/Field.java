package com.fields.fields_library.domain.field;

import com.fields.fields_library.model.FieldDto;
import com.fields.fields_library.model.HCtype;
import com.fields.fields_library.domain.ProtoEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Audited
@Entity
@Table(name = "fields")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Field extends ProtoEntity {

    private String fieldName;
    @Column(name="oil_MMbbl")
    private Double reservesOil;
    @Column(name = "gas_Bcf")
    private Double reservesGas;
    @Column(name = "condensate_MMbbl")
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
