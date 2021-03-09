package it.gov.pagopa.bpd.award_winner.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.event.service.ErrorPublisherService;
import eu.sia.meda.eventlistener.BaseEventListenerTest;
import it.gov.pagopa.bpd.award_winner.command.InsertAwardWinnerCommand;
import it.gov.pagopa.bpd.award_winner.command.SavePaymentInfoOnErrorCommand;
import it.gov.pagopa.bpd.award_winner.command.SavePaymentIntegrationOnErrorCommand;
import it.gov.pagopa.bpd.award_winner.constants.ListenerHeaders;
import it.gov.pagopa.bpd.award_winner.listener.factory.SaveIntegrationPaymentCommandModelFactory;
import it.gov.pagopa.bpd.award_winner.listener.factory.SaveOnErrorCommandModelFactory;
import it.gov.pagopa.bpd.award_winner.listener.factory.SaveOnIntegrationErrorCommandModelFactory;
import it.gov.pagopa.bpd.award_winner.model.PaymentIntegrationAwardWinner;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

/**
 * Test class for the OnInfoPaymentRequestListener method
 */

@Import({OnIntegrationPaymentRequestListener.class})
@TestPropertySource(
        locations = "classpath:config/testIntegrationPaymentRequestListener.properties",
        properties = {
                "listeners.eventConfigurations.items.OnIntegrationPaymentRequestListener.bootstrapServers=${spring.embedded.kafka.brokers}"
        })
public class OnIntegrationPaymentRequestListenerTest extends BaseEventListenerTest {


    @SpyBean
    ObjectMapper objectMapperSpy;
    @SpyBean
    OnIntegrationPaymentRequestListener onIntegrationPaymentRequestListenerSpy;
    @SpyBean
    SaveIntegrationPaymentCommandModelFactory saveIntegrationPaymentCommandModelFactorySpy;
    @SpyBean
    SaveOnIntegrationErrorCommandModelFactory saveOnIntegrationErrorCommandModelFactory;
    @MockBean
    InsertAwardWinnerCommand insertAwardWinnerCommand;
    @MockBean
    SavePaymentIntegrationOnErrorCommand savePaymentIntegrationOnErrorCommand;

    @Value("${listeners.eventConfigurations.items.OnIntegrationPaymentRequestListener.topic}")
    private String topic;

    @Before
    public void setUp() throws Exception {

        Mockito.reset(
                onIntegrationPaymentRequestListenerSpy,
                saveIntegrationPaymentCommandModelFactorySpy,
                saveOnIntegrationErrorCommandModelFactory,
                insertAwardWinnerCommand,
                savePaymentIntegrationOnErrorCommand);
        Mockito.doReturn(true).when(insertAwardWinnerCommand).execute();
        Mockito.doReturn(true).when(savePaymentIntegrationOnErrorCommand).execute();

    }

    @Override
    protected Object getRequestObject() {
        return PaymentIntegrationAwardWinner.builder()
                .idComplaint("000000001")
                .result("OK")
                .resultReason("resultReason")
                .cro("17270006101")
                .executionDate("27/07/2021")
                .build();
    }

    protected Headers getRequestHeaders()
    {
        RecordHeaders recordHeaders = new RecordHeaders();
        recordHeaders.add(ListenerHeaders.INTEGRATION_HEADER, "true".getBytes());
        return recordHeaders;
    }

    @Override
    protected String getTopic() {
        return topic;
    }

    @Override
    protected void verifyInvocation(String json) {
        try {
            BDDMockito.verify(saveIntegrationPaymentCommandModelFactorySpy, Mockito.atLeastOnce())
                    .createModel(Mockito.any());
            BDDMockito.verify(objectMapperSpy, Mockito.atLeastOnce())
                    .readValue(Mockito.anyString(), Mockito.eq(PaymentIntegrationAwardWinner.class));
            BDDMockito.verify(insertAwardWinnerCommand, Mockito.atLeastOnce()).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    protected ErrorPublisherService getErrorPublisherService() {
        return null;
    }
}
