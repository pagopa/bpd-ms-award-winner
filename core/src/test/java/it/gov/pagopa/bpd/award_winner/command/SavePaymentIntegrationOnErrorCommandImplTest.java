package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerIntegrationErrorMapper;
import it.gov.pagopa.bpd.award_winner.model.*;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

public class SavePaymentIntegrationOnErrorCommandImplTest extends BaseTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Mock
    AwardWinnerErrorService awardWinnerErrorService;
    @Spy
    AwardWinnerIntegrationErrorMapper awardWinnerIntegrationErrorMapper;

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerErrorService, awardWinnerIntegrationErrorMapper);
    }

    @Test
    public void TestExecute_OK() {

        PaymentIntegrationAwardWinner paymentIntegrationAwardWinner = getRequestModel();

        AwardWinnerIntegrationErrorCommandModel requestModel =
                AwardWinnerIntegrationErrorCommandModel.builder().payload(paymentIntegrationAwardWinner)
                        .headers(new RecordHeaders())
                .exceptionMessage("Exception description").build();

        SavePaymentIntegrationOnErrorCommandImpl updateAwardWinnerCommand =
                new SavePaymentIntegrationOnErrorCommandImpl(
                requestModel,
                awardWinnerErrorService,
                awardWinnerIntegrationErrorMapper);
        Boolean executed = updateAwardWinnerCommand.doExecute();
        Mockito.verify(awardWinnerIntegrationErrorMapper).map(requestModel);
        Mockito.verify(awardWinnerErrorService).saveErrorRecord(Mockito.any(AwardWinnerError.class));
        Assert.assertTrue(executed);
    }


    protected PaymentIntegrationAwardWinner getRequestModel() {
        return PaymentIntegrationAwardWinner.builder()
                .idConsap("000000001")
                .result("KO")
                .resultReason("resultReason")
                .cro("17270006101")
                .executionDate("27/07/2020")
                .build();
    }


}