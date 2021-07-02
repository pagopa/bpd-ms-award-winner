package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.mapper.IntegratedPaymentMapper;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPayment;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPaymentCommandModel;
import it.gov.pagopa.bpd.award_winner.service.IntegratedPaymentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InsertIntegratedPaymentCommandImplTest extends BaseTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Mock
    IntegratedPaymentService integratedPaymentService;
    @Spy
    IntegratedPaymentMapper integratedPaymentMapperSpy;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Before
    public void initTest() {
        Mockito.reset(integratedPaymentService, integratedPaymentMapperSpy);
    }

    @Test
    public void TestExecute_OK() throws Exception {

        IntegratedPayment integratedPayment = getRequestModel();
        AwardWinner savedModel = getSavedModel();

        InsertIntegratedPaymentCommandImpl insertIntegratedPaymentCommand = new InsertIntegratedPaymentCommandImpl(
                IntegratedPaymentCommandModel.builder().payload(integratedPayment).build(),
                integratedPaymentService);
        Boolean executed = insertIntegratedPaymentCommand.doExecute();
        Mockito.verify(integratedPaymentService).createRecord(Mockito.eq(integratedPayment));
        Assert.assertTrue(executed);

    }

    @Test
    public void TestExecute_KO() {

        IntegratedPayment integratedPayment = getRequestModel();
        integratedPayment.setAwardPeriodId(null);

        InsertIntegratedPaymentCommandImpl insertIntegratedPaymentCommand = new InsertIntegratedPaymentCommandImpl(
                IntegratedPaymentCommandModel.builder().payload(integratedPayment).build(),
                integratedPaymentService);
        exceptionRule.expect(AssertionError.class);
        insertIntegratedPaymentCommand.doExecute();
        Mockito.verifyZeroInteractions(integratedPaymentService);

    }

    protected IntegratedPayment getRequestModel() {
        return IntegratedPayment.builder()
                .fiscalCode("fiscalCode")
                .awardPeriodId(1L)
                .ticketId(2L)
                .relatedPaymentId(123L)
                .amount(new BigDecimal(11))
                .cashbackAmount(new BigDecimal(51))
                .jackpotAmount(new BigDecimal(101))
                .build();
    }

    protected AwardWinner getSavedModel() {
        return AwardWinner.builder()
                .id(1L)
                .cashback(new BigDecimal(12))
                .fiscalCode("fiscalCode")
                .accountHolderFiscalCode("accHolderFiscalCode")
                .payoffInstr("payOffInstr")
                .accountHolderName("accHolderName")
                .accountHolderSurname("accHolderSurname")
                .checkInstrStatus("checkInstrStatus")
                .awardPeriodId(1L)
                .awardPeriodStart(LocalDate.parse("2021-01-01"))
                .awardPeriodEnd(LocalDate.parse("2021-06-30"))
                .amount(new BigDecimal(100))
                .typology("typology")
                .jackpot(new BigDecimal(101))
                .technicalAccountHolder("technicalAccountHolder")
                .chunkFilename("chunkFilename")
                .status(AwardWinner.Status.NEW)
                .result("result").consapId(12L)
                .resultReason("resultReason")
                .cro("cro")
                .executionDate(null)
                .ticketId(2L)
                .relatedPaymentId(123L)
                .issuerCardId("issuerCardId")
                .build();
    }
}
