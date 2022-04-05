package com.fields.fileds_library.objects.field;

import com.fields.fileds_library.api.FieldsApi;
import com.fields.fileds_library.exceptions.FieldNotFoundException;
import com.fields.fileds_library.model.FieldDto;
import com.fields.fileds_library.model.HCtype;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FieldController implements FieldsApi {

    FieldService fieldService;

    @Override
    public ResponseEntity<FieldDto> createField(FieldDto fieldDto) {
        return ResponseEntity.ok(fieldService.createField(fieldDto));
    }

    @Override
    public ResponseEntity<Void> deleteField(UUID id) {
        fieldService.deleteField(id);
        return ResponseEntity.ok().build();
    }

    @Override
    //ToDo
    public ResponseEntity<List<FieldDto>> findAllFields(HCtype hydrocarbons) {
        return FieldsApi.super.findAllFields(hydrocarbons);
    }

    @Override
    public ResponseEntity<FieldDto> findFieldById(UUID id) {
        return ResponseEntity.ok(fieldService.findFieldById(id).orElseThrow(FieldNotFoundException::new));
    }

    @Override
    public ResponseEntity<FieldDto> updateField(UUID id, FieldDto fieldDto) {
        return ResponseEntity.ok(fieldService.updateField(id, fieldDto));
    }
}
