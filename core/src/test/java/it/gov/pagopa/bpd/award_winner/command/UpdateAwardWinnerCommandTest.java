package it.gov.pagopa.bpd.award_winner.command;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.common.BaseTest;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Test class for the UpdateAwardWinnerCommand method
 */

public class UpdateAwardWinnerCommandTest extends BaseTest {

    @Mock
    AwardWinnerService awardWinnerService;

    @Spy
    AwardWinnerMapper awardWinnerMapperSpy;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerService, awardWinnerMapperSpy);
    }

    @Test
    public void TestExecute_OK() {

        PaymentInfoAwardWinner paymentInfoAwardWinner = getRequestModel();
        AwardWinner savedModel = getSavedModel();

        UpdateAwardWinnerCommandImpl updateAwardWinnerCommand = new UpdateAwardWinnerCommandImpl(
                AwardWinnerCommandModel.builder().payload(paymentInfoAwardWinner).build(),
                awardWinnerService,
                awardWinnerMapperSpy);
        Boolean executed = updateAwardWinnerCommand.doExecute();
        Mockito.verify(awardWinnerMapperSpy).map(Mockito.eq(paymentInfoAwardWinner));
        Mockito.verify(awardWinnerService).updateAwardWinner(Mockito.eq(savedModel));
        Assert.assertTrue(executed);

    }

    @Test
    public void TestExecute_KO() {

        PaymentInfoAwardWinner paymentInfoAwardWinner = getRequestModel();
        paymentInfoAwardWinner.setResult(null);

        UpdateAwardWinnerCommandImpl updateAwardWinnerCommand = new UpdateAwardWinnerCommandImpl(
                AwardWinnerCommandModel.builder().payload(getRequestModel()).build(),
                awardWinnerService,
                awardWinnerMapperSpy);
        exceptionRule.expect(AssertionError.class);
        updateAwardWinnerCommand.doExecute();
        Mockito.verifyZeroInteractions(awardWinnerService);

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

    protected AwardWinner getSavedModel() {
        return AwardWinner.builder()
                .id(1L)
                .result("KO")
                .resultReason("resultReason")
                .cro("17270006101")
                .executionDate(LocalDate.parse("27/07/2020", dtf))
                .build();
    }

}