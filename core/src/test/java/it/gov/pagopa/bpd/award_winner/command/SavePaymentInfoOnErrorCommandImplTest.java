package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerErrorMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

public class SavePaymentInfoOnErrorCommandImplTest extends BaseTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Mock
    AwardWinnerErrorService awardWinnerErrorService;
    @Spy
    AwardWinnerErrorMapper awardWinnerErrorMapper;

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerErrorService, awardWinnerErrorMapper);
    }

    @Test
    public void TestExecute_OK() {

        PaymentInfoAwardWinner paymentInfoAwardWinner = getRequestModel();

        AwardWinnerErrorCommandModel requestModel = AwardWinnerErrorCommandModel.builder().payload(paymentInfoAwardWinner)
                .exceptionMessage("Exception description").build();

        SavePaymentInfoOnErrorCommandImpl updateAwardWinnerCommand = new SavePaymentInfoOnErrorCommandImpl(
                requestModel,
                awardWinnerErrorService,
                awardWinnerErrorMapper);
        Boolean executed = updateAwardWinnerCommand.doExecute();
        Mockito.verify(awardWinnerErrorMapper).map(requestModel);
        Mockito.verify(awardWinnerErrorService).saveErrorRecord(Mockito.any(AwardWinnerError.class));
        Assert.assertTrue(executed);
    }


    protected PaymentInfoAwardWinner getRequestModel() {
        return PaymentInfoAwardWinner.builder()
                .uniqueID("000000001")
                .result("KO")
                .resultReason("resultReason")
                .cro("17270006101")
                .executionDate("27/07/2020")
                .build();
    }


}