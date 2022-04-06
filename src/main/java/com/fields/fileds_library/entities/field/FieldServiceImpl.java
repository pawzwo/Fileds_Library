package com.fields.fileds_library.entities.field;


import com.fields.fileds_library.exceptions.FieldNotFoundException;
import com.fields.fileds_library.model.FieldDto;
import com.fields.fileds_library.model.HCtype;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService{

    private final FieldRepository fieldRepository;

    @Override
    public FieldDto createField(FieldDto fieldDto) {
        Field.FieldBuilder fieldBuilder = new Field.FieldBuilder();
        Field field = fieldBuilder
                .fieldName(fieldDto.getFieldName())
                .hydrocarbons(fieldDto.getHydrocarbons())
                .reservesOil(fieldDto.getReservesOil())
                .reservesGas(fieldDto.getReservesGas())
                .reservesCondensate(fieldDto.getReservesCondensate())
                .build();
        return fieldRepository.save(field).toDto();
    }

    @Override
    public void deleteField(UUID id) {
        fieldRepository.delete(fieldRepository.findById(id).orElseThrow(FieldNotFoundException::new));
    }

    @Override
    public List<FieldDto> findAllFields(HCtype hydrocarbons) {
        return fieldRepository.findAllFields(hydrocarbons).stream()
                .map(Field::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FieldDto> findFieldById(UUID id) {
        return fieldRepository.findById(id).map(Field::toDto);
    }

    @Override
    public FieldDto updateField(UUID id, FieldDto fieldDto) {
        Field field = fieldRepository.findById(id).orElseThrow(FieldNotFoundException::new);
        field.updateField(fieldDto);
        return fieldRepository.save(field).toDto();
    }
}
