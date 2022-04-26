package com.fields.fields_library.domain.production;

import com.fields.fields_library.model.DailyReportDto;
import com.fields.fields_library.model.ProductionEntryDto;

public interface ProductionService {

    DailyReportDto addProduction(ProductionEntryDto productionEntryDto);


}
