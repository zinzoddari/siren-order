package com.project.springapistudy.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MenuControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    final String baseUrl = "/menu";

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("메뉴 저장 요청")
    class registerMenu {
        @Test
        @DisplayName("메뉴 저장에 성공")
        void success() throws Exception {
            //given
            String request = "{\n" +
                    "    \"type\": \"BEVERAGE\",\n" +
                    "    \"name\": \"얼음 뺀 아이스 아메리카노\",\n" +
                    "    \"useYn\": \"Y\"\n" +
                    "}";

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn();

            //then
            //TODO: 조회 기능 만들어지면 추후 내용 검증 필요
            assertThat(mvcResult.getResponse().getRedirectedUrl()).isNotNull();
        }

        @Test
        @DisplayName("메뉴 저장에 실패")
        void invalidInput() throws Exception {
            //given
            String request = "{\n" +
                    "    \"type\": \"12345\",\n" +
                    "    \"name\": \"\",\n" +
                    "    \"useYn\": \"F\"\n" +
                    "}";

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

            //then
            //TODO: 조회 기능 만들어지면 추후 내용 검증 필요
            //assertThat(mvcResult.getResponse().getRedirectedUrl()).isNotNull();
        }
    }
}