package com.fields.fields_library.exceptions

import com.fields.fields_library.domain.company.Company
import com.fields.fields_library.domain.company.CompanyController
import com.fields.fields_library.domain.company.CompanyRepository
import com.fields.fields_library.domain.concession.Concession
import com.fields.fields_library.domain.concession.ConcessionController
import com.fields.fields_library.domain.concession.ConcessionRepository
import com.fields.fields_library.domain.field.Field
import com.fields.fields_library.domain.field.FieldRepository
import com.fields.fields_library.model.CompanyDto
import com.fields.fields_library.model.ConcessionDto
import com.fields.fields_library.model.HCtype
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CompanyNotFoundExceptionTest extends Specification {

    @Autowired
    private CompanyRepository companyRepository
    @Autowired
    private CompanyController companyController
    @Autowired
    private FieldRepository fieldRepository
    @Autowired
    private ConcessionRepository concessionRepository
    @Autowired
    private ConcessionController concessionController

    private CompanyDto companyDto
    private final UUID INVALID_ID = UUID.randomUUID()

    void setup() {
        Company company = new Company("Company1")
        companyDto = companyRepository.save(company).toDto()
    }

    void cleanup() {
        companyRepository.deleteAll()
    }

    def "Try to find company with wrong id and get thrown CompanyNotFoundException"() {
        when:
        companyController.findCompanyById(INVALID_ID)
        then:
        thrown(CompanyNotFoundException)
    }

    def "Try to delete non existing company and get thrown CompanyNotFoundException"() {
        when:
        companyController.deleteCompany(INVALID_ID)
        then:
        thrown(CompanyNotFoundException)
    }

    def "Try to update non existing company and get thrown CompanyNotFoundException"() {
        when:
        companyController.updateCompany(INVALID_ID, companyDto)
        then:
        thrown(CompanyNotFoundException)
    }

    def "Try to create concession owned by non existing company and get thrown CompanyNotFoundException"() {
        given:
        Field field = new Field("Field 1", 0.0, (10.0), (0.0), List.of(HCtype.GAS))
        fieldRepository.save(field)
        Company companyNotExist = new Company("not existing company")
        Concession concession = new Concession("Concession 1", "Concession descr", List.of(companyNotExist), List.of(field))
        ConcessionDto concessionDto = concession.toDto()
        when:
        concessionController.createConcession(concessionDto)
        then:
        thrown(CompanyNotFoundException)
    }


}
