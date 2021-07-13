package it.gov.pagopa.bpd.award_winner.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.event.service.ErrorPublisherService;
import eu.sia.meda.eventlistener.BaseEventListenerTest;
import it.gov.pagopa.bpd.award_winner.command.*;
import it.gov.pagopa.bpd.award_winner.constants.ListenerHeaders;
import it.gov.pagopa.bpd.award_winner.listener.factory.*;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
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

@Import({OnInfoPaymentRequestListener.class})
@TestPropertySource(
        locations = {"classpath:config/testInfoPaymentRequestListener.properties",
                "classpath:config/testIntegratedPaymentRequestListener.properties"},
        properties = {
                "listeners.eventConfigurations.items.OnInfoPaymentRequestListener.bootstrapServers=${spring.embedded.kafka.brokers}"
        })
public class OnInfoPaymentRequestListenerTest extends BaseEventListenerTest {


    @SpyBean
    ObjectMapper objectMapperSpy;
    @SpyBean
    OnInfoPaymentRequestListener onInfoPaymentRequestListenerSpy;
    @SpyBean
    SaveInfoPaymentCommandModelFactory saveInfoPaymentCommandModelFactorySpy;
    @SpyBean
    SaveIntegratedPaymentCommandModelFactory saveIntegratedPaymentCommandModelFactorySpy;
    @SpyBean
    SaveOnErrorCommandModelFactory saveOnErrorCommandModelFactory;
    @MockBean
    InsertIntegratedPaymentCommand insertIntegratedPaymentCommandMock;
    @MockBean
    UpdateAwardWinnerCommand updateAwardWinnerCommandMock;
    @MockBean
    SavePaymentInfoOnErrorCommand savePaymentInfoOnErrorCommandMock;
    @SpyBean
    SaveIntegrationPaymentCommandModelFactory saveIntegrationPaymentCommandModelFactorySpy;
    @SpyBean
    SaveOnIntegrationErrorCommandModelFactory saveOnIntegrationErrorCommandModelFactory;
    @MockBean
    InsertAwardWinnerCommand insertAwardWinnerCommand;
    @MockBean
    SavePaymentIntegrationOnErrorCommand savePaymentIntegrationOnErrorCommand;

    @Value("${listeners.eventConfigurations.items.OnInfoPaymentRequestListener.topic}")
    private String topic;

    @Before
    public void setUp() throws Exception {

        Mockito.reset(
                insertAwardWinnerCommand,
                savePaymentIntegrationOnErrorCommand,
                saveOnIntegrationErrorCommandModelFactory,
                saveIntegrationPaymentCommandModelFactorySpy,
                insertIntegratedPaymentCommandMock,
                saveIntegrationPaymentCommandModelFactorySpy,
                onInfoPaymentRequestListenerSpy,
                saveInfoPaymentCommandModelFactorySpy,
                saveOnErrorCommandModelFactory,
                updateAwardWinnerCommandMock,
                saveIntegratedPaymentCommandModelFactorySpy,
                savePaymentInfoOnErrorCommandMock);
        Mockito.doReturn(true).when(updateAwardWinnerCommandMock).execute();
        Mockito.doReturn(true).when(savePaymentInfoOnErrorCommandMock).execute();

    }

    @Override
    protected Object getRequestObject() {
        return PaymentInfoAwardWinner.builder()
                .uniqueID("000000001")
                .result("OK")
                .resultReason("resultReason")
                .cro("17270006101")
                .executionDate("27/07/2021")
                .build();
    }

    protected Headers getRequestHeaders() {
        RecordHeaders recordHeaders = new RecordHeaders();
        recordHeaders.add(ListenerHeaders.PAYMENT_INFO_HEADER, "true".getBytes());
        return recordHeaders;
    }


    @Override
    protected String getTopic() {
        return topic;
    }

    @Override
    protected void verifyInvocation(String json) {

        try {
            BDDMockito.verify(saveInfoPaymentCommandModelFactorySpy, Mockito.atLeastOnce())
                    .createModel(Mockito.any());
            BDDMockito.verify(objectMapperSpy, Mockito.atLeastOnce())
                    .readValue(Mockito.anyString(), Mockito.eq(PaymentInfoAwardWinner.class));
            BDDMockito.verify(updateAwardWinnerCommandMock, Mockito.atLeastOnce()).execute();
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
