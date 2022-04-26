package com.fields.fields_library.domain.production;

import com.fields.fields_library.api.ProductionentriesApi;
import com.fields.fields_library.model.DailyReportDto;
import com.fields.fields_library.model.ProductionEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductionController implements ProductionentriesApi {

    private final ProductionService productionService;


    @Override
    public ResponseEntity<DailyReportDto> addProduction(ProductionEntryDto productionEntryDto) {
        return ResponseEntity.ok(productionService.addProduction(productionEntryDto));
    }
}
