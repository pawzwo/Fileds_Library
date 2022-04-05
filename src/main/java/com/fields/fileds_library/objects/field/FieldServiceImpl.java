package com.fields.fileds_library.objects.field;


import com.fields.fileds_library.exceptions.FieldNotFoundException;
import com.fields.fileds_library.model.FieldDto;
import com.fields.fileds_library.model.HCtype;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService{

    FieldsRepository fieldsRepository;

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
        return fieldsRepository.save(field).toDto();
    }

    @Override
    public void deleteField(UUID id) {
        fieldsRepository.delete(fieldsRepository.findById(id).orElseThrow(FieldNotFoundException::new));
    }

    @Override
    public List<FieldDto> findAllFields(HCtype hydrocarbons) {
        return null;
    }

    @Override
    public Optional<FieldDto> findFieldById(UUID id) {
        return fieldsRepository.findById(id).map(Field::toDto);
    }

    @Override
    public FieldDto updateField(UUID id, FieldDto fieldDto) {
        Field field = fieldsRepository.findById(id).orElseThrow(FieldNotFoundException::new);
        field.updateField(fieldDto);
        return fieldsRepository.save(field).toDto();
    }
}
