package com.project.springapistudy.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.product.domain.ProductType;
import com.project.springapistudy.product.dto.ProductResponse;
import com.project.springapistudy.product.dto.ProductSaveRequest;
import com.project.springapistudy.product.dto.ProductUpdateRequest;
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
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    final String baseUrl = "/product";

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("상품 저장 요청")
    class registerProduct {
        @Test
        @DisplayName("상품 저장에 성공")
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
        @DisplayName("상품 저장에 실패")
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
    @DisplayName("상품 단건 조회 요청")
    class findProduct {
        @Test
        @DisplayName("상품 단건 조회 성공")
        void success() throws Exception {
            //given
            final ProductSaveRequest request = ProductSaveRequest.create(ProductType.BEVERAGE, "바나나", Flag.Y);
            final String selectUrl = 상품_저장_성공(request);

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(selectUrl)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            ProductResponse response = 응답값을_객체에_매핑함(mvcResult, ProductResponse.class);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getProductId()).isNotNull().isNotZero();
                softAssertions.assertThat(response.getName()).isEqualTo(request.getName());
                softAssertions.assertThat(response.getType()).isEqualTo(request.getType());
            });
        }

        @Test
        @DisplayName("유효성 검증에 실패하는 productId 입력시 오류")
        void invalidProductId() throws Exception {
            //given
            final String productId = "0";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{productId}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("존재하지 않는 productId 입력시 오류")
        void nofFoundProductId() throws Exception {
            //given
            final Long productId = 9999999L;

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{productId}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    @Nested
    @DisplayName("상품 수정 요청")
    class modifyProduct {
        @Test
        @DisplayName("상품 수정 성공")
        void success() throws Exception {
            //given
            final ProductSaveRequest saveProduct = ProductSaveRequest.create(ProductType.BEVERAGE, "바나나", Flag.Y);
            final String selectUrl = 상품_저장_성공(saveProduct);

            final ProductResponse product = 응답값을_객체에_매핑함(상품을_조회함(selectUrl), ProductResponse.class);

            final ProductUpdateRequest request = ProductUpdateRequest.create(ProductType.DESSERT, "오렌지");

            final String reqeustString = "{\n" +
                    "    \"type\": \"" + request.getType() + "\",\n" +
                    "    \"name\": \"" + request.getName() + "\"\n" +
                    "}";

            //when
            mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/{productId}", product.getProductId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqeustString))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            final ProductResponse response = 응답값을_객체에_매핑함(상품을_조회함(selectUrl), ProductResponse.class);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getType()).isNotEqualTo(product.getType());
                softAssertions.assertThat(response.getName()).isNotEqualTo(product.getName());
            });
        }

        @Test
        @DisplayName("존재하지 않는 productId 수정 요청시 오류")
        void nofFoundProductId() throws Exception {
            //given
            final String productId = "99999999";
            final String reqeustString = "{\n" +
                    "    \"type\": \"" + ProductType.DESSERT + "\",\n" +
                    "    \"name\": \"" + "아이스크림" + "\"\n" +
                    "}";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/{productId}", productId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqeustString))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("존재하지 않는 상품 종류(productType) 입력하여 수정 요청시 오류")
        void invalidProductType() throws Exception {
            //given
            final String invalidProductType = "AAA";
            final ProductSaveRequest saveProduct = ProductSaveRequest.create(ProductType.BEVERAGE, "바나나", Flag.Y);

            final String selectUrl = 상품_저장_성공(saveProduct);

            final ProductResponse product = 응답값을_객체에_매핑함(상품을_조회함(selectUrl), ProductResponse.class);

            final String reqeustString = "{\n" +
                    "    \"type\": \"" + invalidProductType + "\",\n" +
                    "    \"name\": \"" + "아이스크림" + "\"\n" +
                    "}";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/{productId}", product.getProductId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqeustString))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("1미만의 유효하지 않은 상품ID(productId) 입력시 유효성 검증 실패로 오류")
        void invalidProductId() throws Exception {
            //given
            final String invalidProductId = "0";

            final String reqeustString = "{\n" +
                    "    \"type\": \"" + ProductType.DESSERT + "\",\n" +
                    "    \"name\": \"" + "아이스크림" + "\"\n" +
                    "}";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/{productId}", invalidProductId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqeustString))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("상품 삭제 요청")
    class removeProduct {
        @Test
        @DisplayName("상품 삭제에 성공")
        void success() throws Exception {
            //given
            final ProductSaveRequest saveProduct = ProductSaveRequest.create(ProductType.BEVERAGE, "바나나", Flag.Y);
            final String selectUrl = 상품_저장_성공(saveProduct);

            final ProductResponse product = 응답값을_객체에_매핑함(상품을_조회함(selectUrl), ProductResponse.class);

            final Long productId = product.getProductId();

            //when
            mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{productId}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            final ProductResponse result = 응답값을_객체에_매핑함(상품을_조회함(selectUrl), ProductResponse.class);

            //then
            assertThat(result.getUseYn().isY()).isFalse();
        }

        @Test
        @DisplayName("없는 상품ID(productId) 요청시 실패")
        void notFoundProductId() throws Exception {
            //given
            final String invalidProductId = "999999999";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{productId}", invalidProductId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("1미만의 유효하지 않은 상품ID(productId) 입력시 유효성 검증 실패로 오류")
        void invalidProductId() throws Exception {
            //given
            final String invalidProductId = "0";

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{productId}", invalidProductId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    private String 상품_저장_성공(ProductSaveRequest input) throws Exception {
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

    private MvcResult 상품을_조회함(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    private <T> T 응답값을_객체에_매핑함(MvcResult mvcResult, Class<T> type) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
    }
}
