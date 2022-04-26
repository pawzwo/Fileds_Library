package com.fields.fields_library.domain.field;

import com.fields.fields_library.model.FieldDto;
import com.fields.fields_library.model.HCtype;

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
