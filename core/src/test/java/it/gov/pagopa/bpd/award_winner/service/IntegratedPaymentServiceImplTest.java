package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.mapper.IntegratedPaymentMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = IntegratedPaymentServiceImpl.class)
public class IntegratedPaymentServiceImplTest extends BaseTest {

    @MockBean
    private AwardWinnerDAO awardWinnerDAOMock;

    @MockBean
    private IntegratedPaymentMapper integratedPaymentMapperMock;

    @Autowired
    private IntegratedPaymentService integratedPaymentService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerDAOMock);
        integratedPaymentService = new IntegratedPaymentServiceImpl(awardWinnerDAOMock, integratedPaymentMapperMock);
    }

    @Test
    public void createRecord_OK() throws Exception {
        Optional<AwardWinner> aw = Optional.of(new AwardWinner());
        AwardWinner award = new AwardWinner();
        AwardWinner testAwardWinner = new AwardWinner();
        testAwardWinner.setId(2L);

        BDDMockito.doReturn(award).when(integratedPaymentMapperMock).map(Mockito.any());

        BDDMockito.doReturn(testAwardWinner).when(awardWinnerDAOMock).save(Mockito.any());

        BDDMockito.doReturn(award).when(awardWinnerDAOMock).getAwardWinner("fiscalCode",1L,2L);
        AwardWinner winner = integratedPaymentMapperMock.map(aw);
        winner.setId(2L);
        winner = awardWinnerDAOMock.save(winner);
        Assert.assertNotNull(winner);
        BDDMockito.verify(awardWinnerDAOMock).save(Mockito.eq(winner));
    }

    @Test
    public void createRecord_KO() throws Exception {
        BDDMockito.when(awardWinnerDAOMock.save(Mockito.any())).thenAnswer(
                invocation -> {
                    throw new Exception();
                });
        expectedException.expect(Exception.class);
        awardWinnerDAOMock.save(AwardWinner.builder().build());
        BDDMockito.verify(awardWinnerDAOMock).save(Mockito.any());
    }
}
