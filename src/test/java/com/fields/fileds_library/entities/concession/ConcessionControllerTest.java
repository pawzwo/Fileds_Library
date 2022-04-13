package com.fields.fileds_library.entities.concession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fields.fileds_library.entities.company.Company;
import com.fields.fileds_library.entities.company.CompanyRepository;
import com.fields.fileds_library.entities.field.Field;
import com.fields.fileds_library.entities.field.FieldRepository;
import com.fields.fileds_library.model.ConcessionDto;
import com.fields.fileds_library.model.HCtype;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConcessionControllerTest {

    private final String userName = "admin";
    private final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConcessionRepository concessionRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private ConcessionDto concessionDto;
    private List<Concession> concessions;

    @BeforeAll
    void setUp() {
        concessions = createConcessions();
        concessionDto = createConcessionDto();
    }

    @AfterAll
    void tearDown() {
        fieldRepository.deleteAll();
        companyRepository.deleteAll();
        concessionRepository.deleteAll();
    }

    @Test
    @DisplayName("POST - /concessions - OK")
    void createConcession_ShouldReturnOk() throws Exception {
        this.mockMvc.perform(post("/concessions")
                        .content(objectMapper.writeValueAsString(concessionDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.concessionName", is(concessionDto.getConcessionName())))
                .andExpect(jsonPath("$.concessionDescription", is(concessionDto.getConcessionDescription())))
                .andExpect(jsonPath("$.owners", is(concessionDto.getOwners())))
                .andExpect(jsonPath("$.fields", is(concessionDto.getFields())))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE - /concessions/{id} - OK")
    void deleteConcession_ShouldReturnOk() throws Exception{
        mockMvc.perform(delete("/concessions/{id}", concessions.get(concessions.size()-1).getId())
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /concessions - OK")
    void findAllConcessions_ShouldReturnOK() throws Exception{
        int noOfDbRecords = concessionRepository.findAll().size();
        mockMvc.perform(get("/concessions")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(noOfDbRecords)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /concessions/{id} - OK")
    void findConcessionsById_ShouldReturnOk() throws Exception{
        mockMvc.perform(get("/concessions/{id}", concessions.get(0).getId())
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.concessionName",is(concessions.get(0).getConcessionName())))
                .andExpect(jsonPath("$.concessionDescription",is(concessions.get(0).getConcessionDescription())))
                .andExpect(jsonPath("$.owners",is(concessions.get(0).getOwners().stream()
                        .map(Company::getCompanyName).collect(Collectors.toList()))))
                .andExpect(jsonPath("$.fields",is(concessions.get(0).getFields().stream()
                        .map(Field::getFieldName).collect(Collectors.toList()))))
                .andDo(print());
    }

    @Test
    @DisplayName("PUT - /concessions/{id} - OK")
    void updateConcession_ShouldReturnOk() throws Exception{
        mockMvc.perform(put("/concessions/{id}", concessions.get(3).getId())
                        .content(objectMapper.writeValueAsString(concessionDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.
                                user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.concessionName", is(concessionDto.getConcessionName())))
                .andExpect(jsonPath("$.concessionDescription", is(concessionDto.getConcessionDescription())))
                .andExpect(jsonPath("$.owners", is(concessionDto.getOwners())))
                .andExpect(jsonPath("$.fields", is(concessionDto.getFields())))
                .andDo(print());
    }

    private ConcessionDto createConcessionDto() {
        ConcessionDto concessionDto = new ConcessionDto();
        concessionDto.setConcessionName(concessions.get(0).getConcessionName());
        concessionDto.setConcessionDescription(concessions.get(0).getConcessionDescription());
        concessionDto.setOwners(concessions.get(0).getOwners().stream()
                .map(Company::getCompanyName).collect(Collectors.toList()));
        concessionDto.setFields(concessions.get(0).getFields().stream()
                .map(Field::getFieldName).collect(Collectors.toList()));
        return concessionDto;
    }

    private List<Concession> createConcessions() {
        List<Concession> concessions = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Field field = new Field("Field no " + i, 0.0, (10.0 + i), (0.0), List.of(HCtype.GAS));
            fieldRepository.save(field);
            Company company = new Company("company no " + i);
            companyRepository.save(company);
            Concession concession = new Concession("Concession no " + i, "Concession descr" + i, List.of(company), List.of(field));
            concessionRepository.save(concession);
            concessions.add(concession);
        }
        return concessions;
    }
}