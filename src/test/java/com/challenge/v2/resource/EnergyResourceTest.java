package com.challenge.v2.resource;

import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import com.challenge.service.AssetService;
import com.challenge.v2.domain.model.MeasurementByRangeMock;
import com.challenge.v2.domain.model.MeasurementMock;
import com.challenge.v2.utils.ApiDocs;
import com.challenge.v2.utils.DocSnippets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EnergyResourceTest extends PublicResoureceTest {

    @MockBean
    private AssetService assetService;

    @Test
    public void whenGetAll_thenReturnAll() throws Exception {
        // Create measurements
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        Measurement second = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS)));
        Measurement third = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS)));

        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(first);
        measurementList.add(second);
        measurementList.add(third);

        int page = 0;
        int size = 10;
        Page pageResult = new PageImpl(measurementList, PageRequest.of(page,size), 3);

        // Mock
        when(assetService.getAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(pageResult);
        // Call the controller
        mockMvc.perform(RestDocumentationRequestBuilders.get("/public/measurement?page={page}&size={size}", page, size))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                        .andDo(document("{class-name}/{method-name}",
                                DocSnippets.getPageAndSizeRequestParameters(),
                                responseFields(
                                        fieldWithPath("data[].id").description(ApiDocs.desc(Measurement.class, "id")),
                                        fieldWithPath("data[].timePoint").description(ApiDocs.desc(Measurement.class, "timePoint")),
                                        fieldWithPath("data[].assetId").description(ApiDocs.desc(Measurement.class, "assetId")),
                                        fieldWithPath("data[].activePower").description(ApiDocs.desc(Measurement.class, "activePower")),
                                        fieldWithPath("data[].voltage").description(ApiDocs.desc(Measurement.class, "voltage")),

                                        fieldWithPath("pageNumber").description("Current page number."),
                                        fieldWithPath("pageSize").description("Current number of elements in a page."),
                                        fieldWithPath("totalElements").description("Total elements."),
                                        fieldWithPath("totalPages").description("Total pages."),

                                        fieldWithPath("status").description("Http status code")
                                )));
        // Verify the call to the service
        verify(assetService,times(1)).getAll(page, size);
    }

    @Test
    public void whenGetById_thenReturnOne() throws Exception {
        // Customer object
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));

        // Mock the get customer
        when(assetService.get(Mockito.anyLong())).thenReturn(first);
        // Call the service
        mockMvc.perform(RestDocumentationRequestBuilders.get("/public/measurement/{id}", first.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("id").description(ApiDocs.desc(Measurement.class, "id")),
                                fieldWithPath("timePoint").description(ApiDocs.desc(Measurement.class, "timePoint")),
                                fieldWithPath("assetId").description(ApiDocs.desc(Measurement.class, "assetId")),
                                fieldWithPath("activePower").description(ApiDocs.desc(Measurement.class, "activePower")),
                                fieldWithPath("voltage").description(ApiDocs.desc(Measurement.class, "voltage"))
                        )));
        // Verify the call to the service
        verify(assetService,times(1)).get(Long.valueOf(first.getId()));
    }

    @Test
    public void whenInsertOne_thenReturnOne() throws Exception {
        // Customer object
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        Measurement firstInput = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        firstInput.setId(0);

        // Mock the get customer
        when(assetService.insert(firstInput)).thenReturn(first);
        // Call the service
        mockMvc.perform(RestDocumentationRequestBuilders.post("/public/measurement")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstInput)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("id").description(ApiDocs.desc(Measurement.class, "id")),
                                fieldWithPath("timePoint").description(ApiDocs.desc(Measurement.class, "timePoint")),
                                fieldWithPath("assetId").description(ApiDocs.desc(Measurement.class, "assetId")),
                                fieldWithPath("activePower").description(ApiDocs.desc(Measurement.class, "activePower")),
                                fieldWithPath("voltage").description(ApiDocs.desc(Measurement.class, "voltage"))
                        )));
        // Verify the call to the service
        verify(assetService,times(1)).insert(Mockito.any());
    }

    @Test
    public void whenGetLatest_thenReturnLatestOne() throws Exception {
        // Customer object
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        Measurement firstInput = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        firstInput.setId(0);

        // Mock the get customer
        when(assetService.getLatest()).thenReturn(first);
        // Call the service
        mockMvc.perform(RestDocumentationRequestBuilders.get("/public/measurement/latest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("id").description(ApiDocs.desc(Measurement.class, "id")),
                                fieldWithPath("timePoint").description(ApiDocs.desc(Measurement.class, "timePoint")),
                                fieldWithPath("assetId").description(ApiDocs.desc(Measurement.class, "assetId")),
                                fieldWithPath("activePower").description(ApiDocs.desc(Measurement.class, "activePower")),
                                fieldWithPath("voltage").description(ApiDocs.desc(Measurement.class, "voltage"))
                        )));
        // Verify the call to the service
        verify(assetService,times(1)).getLatest();
    }

    @Test
    public void whenGetMeasurementsByTimeRange_thenReturnList() throws Exception {
        // Customer object
        Measurement second = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS)));
        Measurement third = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS)));

        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(second);
        measurementList.add(third);

        MeasurementByRange input = MeasurementByRangeMock.standardMeasurementByRangeMock(Instant.now().plus(3, ChronoUnit.SECONDS), Instant.now().plus(12, ChronoUnit.SECONDS));

        // Mock the get customer
        when(assetService.getMeasurementsByTimeRange(Mockito.any())).thenReturn(measurementList);
        // Call the service
        mockMvc.perform(RestDocumentationRequestBuilders.post("/public/measurement/byRange")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("data[].id").description(ApiDocs.desc(Measurement.class, "id")),
                                fieldWithPath("data[].timePoint").description(ApiDocs.desc(Measurement.class, "timePoint")),
                                fieldWithPath("data[].assetId").description(ApiDocs.desc(Measurement.class, "assetId")),
                                fieldWithPath("data[].activePower").description(ApiDocs.desc(Measurement.class, "activePower")),
                                fieldWithPath("data[].voltage").description(ApiDocs.desc(Measurement.class, "voltage")),

                                fieldWithPath("status").description("Http status code")
                        )));
        // Verify the call to the service
        verify(assetService,times(1)).getMeasurementsByTimeRange(Mockito.any());
    }
}
