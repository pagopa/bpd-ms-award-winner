package it.gov.pagopa.bpd.award_winner.controller;

import eu.sia.meda.DummyConfiguration;
import eu.sia.meda.error.config.LocalErrorConfig;
import eu.sia.meda.error.handler.MedaExceptionHandler;
import eu.sia.meda.error.service.impl.LocalErrorManagerServiceImpl;
import it.gov.pagopa.bpd.award_winner.command.SubmitFlaggedRecordsCommand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BpdAwardWinnerControllerImpl.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        BpdAwardWinnerControllerImpl.class,
        DummyConfiguration.class,
        MedaExceptionHandler.class,
        LocalErrorManagerServiceImpl.class
})
@Import(LocalErrorConfig.class)
@TestPropertySource(properties = {
        "error-manager.enabled=true",
        "spring.application.name=bpd-ms-award-winner-api-rest"
})
public class BpdAwardWinnerControllerImplTest {

    private final String BASE_URL = "/bpd/award-winners";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private SubmitFlaggedRecordsCommand submitFlaggedRecordsCommand;

    @Test
    public void resubmitTransactions_OK() {
        try {
            Mockito.doReturn(true).when(submitFlaggedRecordsCommand).execute();
            mockMvc.perform(
                    MockMvcRequestBuilders.post(BASE_URL.concat("/resubmitInfoPayments"))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andReturn();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }
}