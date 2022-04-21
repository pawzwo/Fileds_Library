package com.fields.fileds_library.domain.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fields.fileds_library.model.FieldDto;
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
class FieldControllerTest {

    private final String userName = "admin";
    private final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FieldRepository fieldRepository;

    private FieldDto fieldDto;
    private List<Field> fields;

    @BeforeAll
    void setUp() {
        fields = createFields();
        fieldDto = createFieldDto();
    }

    @AfterAll
    void tearDown() {
        fieldRepository.deleteAll();
    }


    @Test
    @DisplayName("POST - /fields - OK")
    void createField_ShouldReturnOk() throws Exception {
        this.mockMvc.perform(post("/fields")
                        .content(objectMapper.writeValueAsString(fieldDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fieldName", is(fieldDto.getFieldName())))
                .andExpect(jsonPath("$.reservesOil", is(fieldDto.getReservesOil())))
                .andExpect(jsonPath("$.reservesGas", is(fieldDto.getReservesGas())))
                .andExpect(jsonPath("$.reservesCondensate", is(fieldDto.getReservesCondensate())))
                .andExpect(jsonPath("$.hydrocarbons", is(fieldDto.getHydrocarbons().stream()
                        .map(HCtype::getValue).collect(Collectors.toList()))))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE - /fields/{id} - OK")
    void deleteField_ShouldReturnOk() throws Exception{
        mockMvc.perform(delete("/fields/{id}", fields.get(fields.size()-1).getId())
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("GET - /fields - OK")
    void findAllFields_ShouldReturnOK() throws Exception{
        int noOfDbRecords = fieldRepository.findAll().size();
        mockMvc.perform(get("/fields")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(noOfDbRecords)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /field/{id} - OK")
    void findFieldById_ShouldReturnOk() throws Exception{
        mockMvc.perform(get("/fields/{id}", fields.get(0).getId())
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fieldName",is(fields.get(0).getFieldName())))
                .andExpect(jsonPath("$.reservesOil", is(fields.get(0).getReservesOil())))
                .andExpect(jsonPath("$.reservesGas", is(fields.get(0).getReservesGas())))
                .andExpect(jsonPath("$.reservesCondensate", is(fields.get(0).getReservesCondensate())))
                .andExpect(jsonPath("$.hydrocarbons", is(fields.get(0).getHydrocarbons().stream()
                        .map(HCtype::getValue).collect(Collectors.toList()))))
                .andDo(print());
    }

    @Test
    @DisplayName("PUT - /fields/{id} - OK")
    void updateField() throws Exception{
        mockMvc.perform(put("/fields/{id}", fields.get(3).getId())
                        .content(objectMapper.writeValueAsString(fieldDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.
                                user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fieldName", is(fieldDto.getFieldName())))
                .andExpect(jsonPath("$.reservesOil", is(fieldDto.getReservesOil())))
                .andExpect(jsonPath("$.reservesGas", is(fieldDto.getReservesGas())))
                .andExpect(jsonPath("$.reservesCondensate", is(fieldDto.getReservesCondensate())))
                .andExpect(jsonPath("$.hydrocarbons", is(fieldDto.getHydrocarbons().stream()
                        .map(HCtype::getValue).collect(Collectors.toList()))))
                .andDo(print());
    }

    private FieldDto createFieldDto() {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setFieldName(fields.get(0).getFieldName());
        fieldDto.setReservesOil(fields.get(0).getReservesOil());
        fieldDto.setReservesGas(fields.get(0).getReservesGas());
        fieldDto.setReservesCondensate(fields.get(0).getReservesCondensate());
        fieldDto.setHydrocarbons(fields.get(0).getHydrocarbons());
        return fieldDto;
    }

    private List<Field> createFields() {
        List<Field> fields = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            if (i < 4) {
                Field field = new Field("Field no " + i, 0.0, (10.0 + i), (0.0), List.of(HCtype.GAS));
                fieldRepository.save(field);
                fields.add(field);
            } else {
                Field field = new Field("Field no " + i, (10.0 + i), 0.0, (5.0 + 1), List.of(HCtype.OIL));
                fieldRepository.save(field);
                fields.add(field);
            }

        }
        return fields;
    }
}