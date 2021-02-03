package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.BaseTest;
import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import it.gov.pagopa.bpd.award_winner.integration.event.AwardWinnerPublisherConnector;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import it.gov.pagopa.bpd.award_winner.transformer.HeaderAwareRequestTransformer;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;

/**
 * Class for unit-testing {@link AwardWinnerPublisherService}
 */
public class AwardWinnerPublisherServiceImplTest extends BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private AwardWinnerPublisherConnector awardWinnerPublisherConnector;
    private AwardWinnerPublisherService awardWinnerPublisherService;

    @SpyBean
    private HeaderAwareRequestTransformer<PaymentInfo> simpleEventRequestTransformerSpy;

    @SpyBean
    private SimpleEventResponseTransformer simpleEventResponseTransformerSpy;

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerPublisherConnector);
        awardWinnerPublisherService =
                new AwardWinnerPublisherServiceImpl(
                        awardWinnerPublisherConnector,
                        simpleEventRequestTransformerSpy,
                        simpleEventResponseTransformerSpy);
    }

    @Test
    public void testSave_Ok() {

        try {

            BDDMockito.doReturn(true)
                    .when(awardWinnerPublisherConnector)
                    .doCall(Mockito.eq(getSaveModel()), Mockito.any(), Mockito.any());

            awardWinnerPublisherService.publishAwardWinnerEvent(getSaveModel(), getRecordHeaders());

            BDDMockito.verify(awardWinnerPublisherConnector, Mockito.atLeastOnce())
                    .doCall(Mockito.eq(getSaveModel()), Mockito.any(), Mockito.any(), Mockito.any());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void testSave_KO_Connector() {

        BDDMockito.doAnswer(invocationOnMock -> {
            throw new Exception();
        }).when(awardWinnerPublisherConnector)
                .doCall(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        expectedException.expect(Exception.class);
        awardWinnerPublisherService.publishAwardWinnerEvent(null, null);

        BDDMockito.verify(awardWinnerPublisherConnector, Mockito.atLeastOnce())
                .doCall(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    protected PaymentInfo getSaveModel() {
        return PaymentInfo.builder()
                .result("OK")
                .resultReason("result reason")
                .cro("cro")
                .executionDate("10/10/2020")
                .build();
    }

    private RecordHeaders getRecordHeaders() {
        return new RecordHeaders();
    }


}