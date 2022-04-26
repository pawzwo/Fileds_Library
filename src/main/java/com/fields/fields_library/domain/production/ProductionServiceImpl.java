package com.fields.fields_library.domain.production;


import com.fields.fields_library.domain.company.Company;
import com.fields.fields_library.domain.concession.Concession;
import com.fields.fields_library.domain.concession.ConcessionRepository;
import com.fields.fields_library.domain.field.Field;
import com.fields.fields_library.domain.field.FieldRepository;
import com.fields.fields_library.exceptions.ConcessionNotFoundException;
import com.fields.fields_library.exceptions.FieldNotFoundException;
import com.fields.fields_library.model.DailyReportDto;
import com.fields.fields_library.model.ProductionEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionServiceImpl implements ProductionService {


    private final FieldRepository fieldRepository;
    private final ConcessionRepository concessionRepository;
    private final KafkaTemplate<String, DailyReportDto> kafkaTemplate;

    @Override
    public DailyReportDto addProduction(ProductionEntryDto productionEntryDto) {
        System.out.println(productionEntryDto.toString());
        Field field = fieldRepository.findFieldByFieldName(productionEntryDto.getFieldName())
                .orElseThrow(FieldNotFoundException::new);
        Concession concession = concessionRepository.findByFieldName(productionEntryDto.getFieldName())
                .orElseThrow(ConcessionNotFoundException::new);
        DailyReportDto dailyReportDto = createReport(concession, field, productionEntryDto);
        sendToTopic(dailyReportDto);
        updateReserves(field, productionEntryDto);
        return dailyReportDto;
    }

    private DailyReportDto createReport(Concession concession, Field field, ProductionEntryDto productionEntryDto) {
        DailyReportDto dailyReportDto = new DailyReportDto();
        dailyReportDto.setConcessionName(concession.getConcessionName());
        dailyReportDto.setOwners(concession.getOwners().stream().map(Company::getCompanyName).collect(Collectors.toList()));
        dailyReportDto.setFieldName(field.getFieldName());
        dailyReportDto.setHydrocarbons(field.getHydrocarbons());
        dailyReportDto.setProducedOilBbl(productionEntryDto.getProducedOilBbl());
        dailyReportDto.setProducedGasMMcf(productionEntryDto.getProducedGasMMcf());
        dailyReportDto.setProducedCondensateBbl(productionEntryDto.getProducedCondensateBbl());
        return dailyReportDto;
    }

    private void updateReserves(Field field, ProductionEntryDto productionEntryDto) {
        Double remainingOil = field.getReservesOil()- productionEntryDto.getProducedOilBbl()/1000000;
        Double remainingGas = field.getReservesGas()- productionEntryDto.getProducedGasMMcf()/1000;
        Double remainingCondensate = field.getReservesCondensate()- productionEntryDto.getProducedCondensateBbl()/1000000;
        fieldRepository.updateReserves(remainingOil, remainingGas, remainingCondensate, field.getFieldName() );
    }

    private void sendToTopic(DailyReportDto dailyReportDto) {
        //TODO topic config
        kafkaTemplate.send("Daily_Report", dailyReportDto);
    }
}
