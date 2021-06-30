package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:config/awardWinnerService.properties")
@ContextConfiguration(classes = AwardWinnerServiceImpl.class)
public class AwardWinnerServiceImplTest extends BaseTest {



    @MockBean
    private AwardWinnerDAO awardWinnerDAOMock;

    @Autowired
    private AwardWinnerService awardWinnerService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerDAOMock);
        awardWinnerService = new AwardWinnerServiceImpl(awardWinnerDAOMock);
    }

    @Test
    public void update_OK() throws Exception {
        AwardWinner awardWinner = AwardWinner.builder().id(1L).build();
        Optional<AwardWinner> optionalAwardWinner = Optional.ofNullable(AwardWinner.builder().id(1L).build());
        BDDMockito.doReturn(optionalAwardWinner).when(awardWinnerDAOMock).findById(awardWinner.getId());
        BDDMockito.doReturn(awardWinner).when(awardWinnerDAOMock).update(Mockito.eq(awardWinner));
        awardWinner = awardWinnerService.updateAwardWinner(awardWinner);
        Assert.assertNotNull(awardWinner);
        BDDMockito.verify(awardWinnerDAOMock).update(Mockito.eq(awardWinner));
    }

    @Test
    public void update_KO() throws Exception {
        BDDMockito.when(awardWinnerDAOMock.update(Mockito.any())).thenAnswer(
                invocation -> {
                    throw new Exception();
                });
        expectedException.expect(Exception.class);
        awardWinnerService.updateAwardWinner(AwardWinner.builder().build());
        BDDMockito.verify(awardWinnerDAOMock).update(Mockito.any());
    }


}