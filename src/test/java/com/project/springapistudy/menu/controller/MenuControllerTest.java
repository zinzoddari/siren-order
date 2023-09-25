package com.project.springapistudy.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.dto.MenuResponse;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
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

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("메뉴 단건 조회 요청")
    class findMenu {
        @Test
        @DisplayName("메뉴 단건 조회 성공")
        void success() throws Exception {
            //given
            final MenuSaveRequest request = MenuSaveRequest.builder()
                    .name("바나나")
                    .type(MenuType.BEVERAGE)
                    .build();
            final String selectUrl = 메뉴_저장_성공(request);

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(selectUrl)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            MenuResponse response = 응답값을_객체에_매핑함(mvcResult, MenuResponse.class);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getMenuId()).isNotNull().isNotZero();
                softAssertions.assertThat(response.getName()).isEqualTo(request.getName());
                softAssertions.assertThat(response.getType()).isEqualTo(request.getType());
            });
        }

        @Test
        @DisplayName("유효성 검증에 실패하는 menuId 입력시 오류")
        void invalidMenuId() throws Exception {
            //given
            final String menuId = "0";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{menuId}", menuId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("존재하지 않는 menuId 입력시 오류")
        void nofFoundMenuId() throws Exception {
            //given
            final Long menuId = 9999999L;

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{menuId}", menuId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    private String 메뉴_저장_성공(MenuSaveRequest input) throws Exception {
        String request = "{\n" +
                "    \"type\": \"" + input.getType() + "\",\n" +
                "    \"name\": \"" + input.getName() + "\",\n" +
                "    \"useYn\": \"Y\"\n" +
                "}";

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        return mvcResult.getResponse().getRedirectedUrl();
    }

    private MvcResult 메뉴를_조회함(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    private MvcResult 메뉴를_조회함(Long menuId) throws Exception {
        return 메뉴를_조회함(String.format(baseUrl + "/%s", menuId));
    }

    private <T> T 응답값을_객체에_매핑함(MvcResult mvcResult, Class<T> type) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
    }
}