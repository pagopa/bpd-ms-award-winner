package it.gov.pagopa.bpd.award_winner.command;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerIntegration;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.mapper.IntegrationAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerCommandModel;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerIntegrationCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.model.PaymentIntegrationAwardWinner;
import it.gov.pagopa.bpd.award_winner.service.AwardPeriodConnectorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import it.gov.pagopa.bpd.common.BaseTest;
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
import java.time.format.DateTimeFormatter;

/**
 * Test class for the UpdateAwardWinnerCommand method
 */

public class IntegrationAwardWinnerCommandTest extends BaseTest {

    @Mock
    AwardWinnerService awardWinnerService;

    @Mock
    AwardPeriodConnectorService awardPeriodConnectorService;

    @Spy
    IntegrationAwardWinnerMapper integrationAwardWinnerMapperSpy;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerService, integrationAwardWinnerMapperSpy);
    }

    @Test
    public void TestExecute_OK() throws Exception {

        PaymentIntegrationAwardWinner paymentIntegrationAwardWinner = getRequestModel();
        AwardWinnerIntegration savedModel = getSavedModel();
        BDDMockito.doReturn(1L).when(awardPeriodConnectorService).findAwardPeriodId(
                Mockito.eq(savedModel.getAwardPeriodStart()), Mockito.eq(savedModel.getAwardPeriodEnd()));

        InsertAwardWinnerCommandImpl updateAwardWinnerCommand = new InsertAwardWinnerCommandImpl(
                AwardWinnerIntegrationCommandModel.builder().payload(paymentIntegrationAwardWinner).build(),
                awardWinnerService,
                awardPeriodConnectorService,
                integrationAwardWinnerMapperSpy);
        Boolean executed = updateAwardWinnerCommand.doExecute();
        Mockito.verify(integrationAwardWinnerMapperSpy).map(Mockito.eq(paymentIntegrationAwardWinner));
        Mockito.verify(awardWinnerService).insertIntegrationAwardWinner(Mockito.eq(savedModel));
        Assert.assertTrue(executed);

    }

    @Test
    public void TestExecute_KO() {

        PaymentIntegrationAwardWinner paymentIntegrationAwardWinner = getRequestModel();
        paymentIntegrationAwardWinner.setResult(null);

        InsertAwardWinnerCommandImpl insertAwardWinnerCommand = new InsertAwardWinnerCommandImpl(
                AwardWinnerIntegrationCommandModel.builder().payload(getRequestModel()).build(),
                awardWinnerService,
                awardPeriodConnectorService,
                integrationAwardWinnerMapperSpy);
        exceptionRule.expect(AssertionError.class);
        insertAwardWinnerCommand.doExecute();
        Mockito.verifyZeroInteractions(awardWinnerService);

    }

    protected PaymentIntegrationAwardWinner getRequestModel() {
        return PaymentIntegrationAwardWinner.builder()
                .idConsap("000000001")
                .idComplaint("000000001")
                .idPagoPa("000000001")
                .periodStartDate("01/01/2021")
                .periodEndDate("01/06/2021")
                .iban("iban")
                .fiscalCode("fiscalCode")
                .name("name")
                .surname("surname")
                .cashbackAmount(BigDecimal.valueOf(100L,2))
                .amount(BigDecimal.valueOf(100L,2))
                .jackpotAmount(BigDecimal.valueOf(100L,2))
                .result("ORDINE ESEGUITO")
                .resultReason("result reason")
                .cro("17270006101")
                .executionDate("27/07/2021")
                .build();
    }

    protected AwardWinnerIntegration getSavedModel() {
        return AwardWinnerIntegration.builder()
                .consapId(Long.valueOf("000000001"))
                .relatedId(Long.valueOf("000000001"))
                .ticketId(Long.valueOf("000000001"))
                .awardPeriodId(Long.valueOf("1"))
                .awardPeriodStart(LocalDate.parse("01/01/2021", dtf))
                .awardPeriodEnd(LocalDate.parse("01/06/2021", dtf))
                .payoffInstr("iban")
                .fiscalCode("fiscalCode")
                .accountHolderFiscalCode("fiscalCode")
                .accountHolderName("name")
                .accountHolderSurname("surname")
                .cashback(BigDecimal.valueOf(100L,2))
                .jackpot(BigDecimal.valueOf(100L,2))
                .amount(BigDecimal.valueOf(100L,2))
                .result("ORDINE ESEGUITO")
                .resultReason("result reason")
                .cro("17270006101")
                .executionDate(LocalDate.parse("27/07/2020", dtf))
                .technicalAccountHolder("technicalProperty")
                .status(AwardWinnerIntegration.Status.INTEGRATION)
                .build();
    }

}