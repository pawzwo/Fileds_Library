package com.fields.fileds_library.exceptions

import com.fields.fileds_library.domain.company.Company
import com.fields.fileds_library.domain.company.CompanyRepository
import com.fields.fileds_library.domain.concession.Concession
import com.fields.fileds_library.domain.concession.ConcessionController
import com.fields.fileds_library.domain.concession.ConcessionRepository
import com.fields.fileds_library.domain.field.Field
import com.fields.fileds_library.domain.field.FieldController
import com.fields.fileds_library.domain.field.FieldRepository
import com.fields.fileds_library.model.ConcessionDto
import com.fields.fileds_library.model.FieldDto
import com.fields.fileds_library.model.HCtype
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FieldNotFoundExceptionTest extends Specification {

    @Autowired
    private CompanyRepository companyRepository
    @Autowired
    private FieldController fieldController
    @Autowired
    private FieldRepository fieldRepository
    @Autowired
    private ConcessionRepository concessionRepository
    @Autowired
    private ConcessionController concessionController

    private FieldDto fieldDto
    private final UUID INVALID_ID = UUID.randomUUID()

    void setup() {
        Field field = new Field("Field 1", 0.0, (10.0), (0.0), List.of(HCtype.GAS))
        fieldDto = fieldRepository.save(field).toDto()
    }

    void cleanup() {
        fieldRepository.deleteAll()
    }

    def "Try to find field with wrong id and get thrown FieldNotFoundException"() {
        when:
        fieldController.findFieldById(INVALID_ID)
        then:
        thrown(FieldNotFoundException)
    }

    def "Try to delete non existing field and get thrown FieldNotFoundException"() {
        when:
        fieldController.deleteField(INVALID_ID)
        then:
        thrown(FieldNotFoundException)
    }

    def "Try to update non existing field and get thrown FieldNotFoundException"() {
        when:
        fieldController.updateField(INVALID_ID, fieldDto)
        then:
        thrown(FieldNotFoundException)
    }

    def "Try to create concession with non existing field and get thrown FieldNotFoundException"() {
        given:
        Field fieldNonExist = new Field("Non existing field", 0.0, (10.0), (0.0), List.of(HCtype.GAS))
        Company company = new Company("Company1")
        companyRepository.save(company)
        Concession concession = new Concession("Concession 1", "Concession descr", List.of(company), List.of(fieldNonExist))
        ConcessionDto concessionDto = concession.toDto()
        when:
        concessionController.createConcession(concessionDto)
        then:
        thrown(FieldNotFoundException)
    }

}
