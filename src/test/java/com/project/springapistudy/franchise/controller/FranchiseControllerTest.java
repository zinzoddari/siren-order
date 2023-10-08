package com.project.springapistudy.franchise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.common.InvalidResponse;
import com.project.springapistudy.franchise.dto.FranchiseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FranchiseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Nested
    @DisplayName("프랜차이즈 등록 요청")
    class registerFranchise {
        @Test
        @DisplayName("등록 성공")
        void success() throws Exception {
            //given
            final String name = "카페";
            final String englishName = "name";
            final String useYn = "Y";

            final String request = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            //when
            final FranchiseResponse response = 응답값을_객체에_매핑함(프랜차이즈를_조회함(프랜차이즈를_저장함(request)), FranchiseResponse.class);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getFranchiseId()).isNotZero();
                softAssertions.assertThat(response.getName()).isEqualTo(name);
                softAssertions.assertThat(response.getEnglishName()).isEqualTo(englishName);
            });
        }

        @Test
        @DisplayName("중복 이름 등록시 400 오류 출력")
        void duplicateName() throws Exception {
            //given
            final String name = "카페1";
            final String englishName = "name";
            final String useYn = "Y";

            final String request = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            프랜차이즈를_저장함(request);

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("이름 32자 넘겼을 경우 400 오류")
        void invalidNameSize() throws Exception {
            //given
            final String name = "가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마";
            final String englishName = "name";
            final String useYn = "Y";

            final String request = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            //when
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

            InvalidResponse response = 유효성검사_실패_객체_매핑함(result).get(0);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getField()).isEqualTo("name");
                softAssertions.assertThat(response.getRejectValue()).isEqualTo(name);
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        @DisplayName("이름이 빈값일 경우 400 오류")
        void invalidName(String input) throws Exception {
            //given
            final String name = input;
            final String englishName = "name";
            final String useYn = "Y";

            final String request = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            //when
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

            InvalidResponse response = 유효성검사_실패_객체_매핑함(result).get(0);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getField()).isEqualTo("name");
                softAssertions.assertThat(response.getRejectValue()).isEqualTo(name);
            });
        }

        @Test
        @DisplayName("이름이 null일 경우 400 오류")
        void nameIsNull() throws Exception {
            //given
            final String englishName = "name";
            final String useYn = "Y";

            final String request = "{\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            //when
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

            InvalidResponse response = 유효성검사_실패_객체_매핑함(result).get(0);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getField()).isEqualTo("name");
                softAssertions.assertThat(response.getRejectValue()).isEqualTo(null);
            });
        }

        @Test
        @DisplayName("영어 이름이 64자 이상일 경우 400 오류")
        void invalidEnglishNameSize() throws Exception {
            //given
            final String name = "카페";
            final String englishName = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";
            final String useYn = "Y";

            final String request = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            //when
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

            InvalidResponse response = 유효성검사_실패_객체_매핑함(result).get(0);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getField()).isEqualTo("englishName");
                softAssertions.assertThat(response.getRejectValue()).isEqualTo(englishName);
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        @DisplayName("사용여부가 빈값일 때 400 오류")
        void useYnIsNull(String input) throws Exception {
            //given
            final String name = "카페";
            final String englishName = "name";
            final String useYn = input;

            final String request = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"englishName\": \"" + englishName + "\",\n" +
                    "    \"useYn\": \"" + useYn + "\"\n" +
                    "}";

            //when
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();

            InvalidResponse response = 유효성검사_실패_객체_매핑함(result).get(0);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getField()).isEqualTo("useYn");
                softAssertions.assertThat(response.getRejectValue()).isEqualTo(useYn);
            });
        }
    }

    private String 프랜차이즈를_저장함(String param) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getRedirectedUrl();
    }

    private MvcResult 프랜차이즈를_조회함(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    private <T> T 응답값을_객체에_매핑함(MvcResult mvcResult, Class<T> type) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
    }

    private List<InvalidResponse> 유효성검사_실패_객체_매핑함(MvcResult mvcResult) throws Exception {
        return Arrays.stream(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), InvalidResponse[].class))
                .collect(Collectors.toList());
    }
}