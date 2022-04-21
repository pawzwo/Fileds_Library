package com.fields.fileds_library.domain.production;

import com.fields.fileds_library.model.DailyReportDto;
import com.fields.fileds_library.model.ProductionEntryDto;

public interface ProductionService {

    DailyReportDto addProduction(ProductionEntryDto productionEntryDto);


}
