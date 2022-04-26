package com.fields.fileds_library.entities.field;

import com.fields.fileds_library.model.FieldDto;
import com.fields.fileds_library.model.HCtype;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldService {

    FieldDto createField(FieldDto fieldDto);

    void deleteField(UUID id);

    List<FieldDto> findAllFields(HCtype hydrocarbons);

    Optional<FieldDto> findFieldById(UUID id);

    FieldDto updateField(UUID id, FieldDto fieldDto);

}
