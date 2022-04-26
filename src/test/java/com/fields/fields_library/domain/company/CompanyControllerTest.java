package com.fields.fields_library.domain.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fields.fields_library.model.CompanyDto;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyControllerTest {

    private final String userName = "admin";
    private final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CompanyRepository companyRepository;

    private CompanyDto companyDto;
    private List<Company> companies;

    @BeforeAll
    void setUp() {
        companies = createCompanies();
        companyDto = createCompanyDto();

    }

    @AfterAll
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    @DisplayName("POST - /companies - OK")
    void createCompany_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/companies")
                .content(objectMapper.writeValueAsString(companyDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors
                        .user(userName)
                        .authorities(authority)))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.companyName", is(companyDto.getCompanyName())))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE - /companies/{id} - OK")
    void deleteCompany_ShouldReturnOk() throws Exception{
        mockMvc.perform(delete("/companies/{id}", companies.get(companies.size()-1).getId())
                .with(SecurityMockMvcRequestPostProcessors
                        .user(userName)
                        .authorities(authority)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /companies - OK")
    void findAllCompanies_ShouldReturnOK() throws Exception{
        int noOfDbRecords = companyRepository.findAll().size();
        mockMvc.perform(get("/companies")
                .with(SecurityMockMvcRequestPostProcessors
                        .user(userName)
                        .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(noOfDbRecords)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /companies/{id} - OK")
    void findCompanyById_ShouldReturnOk() throws Exception{
        mockMvc.perform(get("/companies/{id}", companies.get(0).getId())
                .with(SecurityMockMvcRequestPostProcessors
                        .user(userName)
                        .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.companyName",is(companies.get(0).getCompanyName())))
                .andDo(print());
    }

    @Test
    @DisplayName("PUT - /companies/{id} - OK")
    void updateCompany() throws Exception{
        mockMvc.perform(put("/companies/{id}", companies.get(3).getId())
                .content(objectMapper.writeValueAsString(companyDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.
                        user(userName)
                        .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.companyName", is(companyDto.getCompanyName())))
                .andDo(print());
    }

    private CompanyDto createCompanyDto() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyName(companies.get(0).getCompanyName());
        return companyDto;
    }

    private List<Company> createCompanies() {
        List<Company> companies = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Company company = new Company("company no " + i );
            companyRepository.save(company);
            companies.add(company);
        }
        return companies;
    }
}