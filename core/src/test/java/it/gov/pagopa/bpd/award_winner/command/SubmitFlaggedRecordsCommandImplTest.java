package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import it.gov.pagopa.bpd.award_winner.mapper.ResubmitAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerPublisherService;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the SubmitFlaggedRecordsCommand method
 */
public class SubmitFlaggedRecordsCommandImplTest extends BaseTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Mock
    AwardWinnerErrorService awardWinnerErrorService;
    @Mock
    AwardWinnerPublisherService awardWinnerPublisherService;
    @Spy
    ResubmitAwardWinnerMapper resubmitAwardWinnerMapperSpy;

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerErrorService, awardWinnerPublisherService, resubmitAwardWinnerMapperSpy);
    }

    @Test
    public void TestExecute_OK() {

        List<AwardWinnerError> awardWinnerErrorList = new ArrayList<>();
        AwardWinnerError awardWinnerError = AwardWinnerError.builder().uniqueID("0000000001").result("KO").build();
        PaymentInfo paymentInfo = PaymentInfo.builder().uniqueID("0000000001").result("KO").build();
        awardWinnerErrorList.add(awardWinnerError);
        BDDMockito.doReturn(awardWinnerErrorList)
                .when(awardWinnerErrorService).findRecordsToResubmit();


        SubmitFlaggedRecordsCommandImpl submitFlaggedRecordsCommand = new SubmitFlaggedRecordsCommandImpl(
                awardWinnerErrorService,
                resubmitAwardWinnerMapperSpy,
                awardWinnerPublisherService);
        Boolean executed = submitFlaggedRecordsCommand.doExecute();

        Mockito.verify(awardWinnerPublisherService).publishAwardWinnerEvent(Mockito.eq(paymentInfo), Mockito.any(RecordHeaders.class));
        Mockito.verify(resubmitAwardWinnerMapperSpy).map(Mockito.eq(awardWinnerError));
        Mockito.verify(awardWinnerErrorService).findRecordsToResubmit();
        Mockito.verify(awardWinnerErrorService).saveErrorRecord(Mockito.any(AwardWinnerError.class));
        Assert.assertTrue(executed);

    }


}