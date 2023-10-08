package com.project.springapistudy.franchise.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FranchiseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    final String baseUrl = "/franchise";

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("프랜차이즈 단건 조회 요청")
    class findProductId {
        @Test
        @DisplayName("정상 조회 성공")
        void success() {
            //TODO: 저장 기능 후에 조회 할 수 있도록
            //given

            //when

            //then
        }

        @Test
        @DisplayName("1보다 작은 숫자 입력 시 오류")
        void invalidSizeFranchiseId() throws Exception {
            //given
            final String franchiseId = "0";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{franchiseId}", franchiseId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("숫자 외 다른 값 입력 시 오류")
        void invalidFranchiseId() throws Exception {
            //given
            final String franchiseId = "ㄱㄴㄷㄹㅁㅂㅅ";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{franchiseId}", franchiseId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }
    }
}