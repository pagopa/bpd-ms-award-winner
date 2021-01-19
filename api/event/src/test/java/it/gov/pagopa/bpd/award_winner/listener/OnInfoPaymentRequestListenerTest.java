package it.gov.pagopa.bpd.award_winner.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.event.service.ErrorPublisherService;
import eu.sia.meda.eventlistener.BaseEventListenerTest;
import it.gov.pagopa.bpd.award_winner.command.UpdateAwardWinnerCommand;
import it.gov.pagopa.bpd.award_winner.listener.factory.SaveInfoPaymentCommandModelFactory;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
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
        locations = "classpath:config/testInfoPaymentRequestListener.properties",
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
    @MockBean
    UpdateAwardWinnerCommand updateAwardWinnerCommandMock;

    @Value("${listeners.eventConfigurations.items.OnInfoPaymentRequestListener.topic}")
    private String topic;

    @Before
    public void setUp() throws Exception {

        Mockito.reset(
                onInfoPaymentRequestListenerSpy,
                saveInfoPaymentCommandModelFactorySpy,
                updateAwardWinnerCommandMock);
        Mockito.doReturn(true).when(updateAwardWinnerCommandMock).execute();

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
