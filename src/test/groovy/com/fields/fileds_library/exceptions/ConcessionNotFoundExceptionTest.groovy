package com.fields.fileds_library.exceptions

import com.fields.fileds_library.domain.company.Company
import com.fields.fileds_library.domain.company.CompanyRepository
import com.fields.fileds_library.domain.concession.Concession
import com.fields.fileds_library.domain.concession.ConcessionController
import com.fields.fileds_library.domain.concession.ConcessionRepository
import com.fields.fileds_library.domain.field.Field
import com.fields.fileds_library.domain.field.FieldRepository
import com.fields.fileds_library.model.ConcessionDto
import com.fields.fileds_library.model.HCtype
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ConcessionNotFoundExceptionTest extends Specification {

    @Autowired
    private ConcessionRepository concessionRepository
    @Autowired
    private ConcessionController concessionController
    @Autowired
    private CompanyRepository companyRepository
    @Autowired
    private FieldRepository fieldRepository

    private ConcessionDto concessionDto
    private final UUID INVALID_ID = UUID.randomUUID()

    void setup() {
        Field field = new Field("Field 1", 0.0, (10.0), (0.0), List.of(HCtype.GAS))
        fieldRepository.save(field)
        Company company = new Company("Company")
        companyRepository.save(company)
        Concession concession = new Concession("Concession 1", "Concession descr", List.of(company), List.of(field))
        concessionDto = concessionRepository.save(concession).toDto()
    }

    void cleanup() {
        concessionRepository.deleteAll()
    }

    def "Try to find concession with wrong id and get thrown ConcessionNotFoundException"() {
        when:
        concessionController.findConcessionById(INVALID_ID)
        then:
        thrown(ConcessionNotFoundException)
    }

    def "Try to delete non existing concession and get thrown ConcessionNotFoundException"() {
        when:
        concessionController.deleteConcession(INVALID_ID)
        then:
        thrown(ConcessionNotFoundException)
    }

    def "Try to update non existing concession and get thrown ConcessionNotFoundException"() {
        when:
        concessionController.updateConcession(INVALID_ID, concessionDto)
        then:
        thrown(ConcessionNotFoundException)
    }

}
