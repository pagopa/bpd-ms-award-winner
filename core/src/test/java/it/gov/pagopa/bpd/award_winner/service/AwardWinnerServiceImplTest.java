package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerErrorDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AwardWinnerServiceImpl.class)
@TestPropertySource(locations = "classpath:config/awardWinner.properties")
public class AwardWinnerServiceImplTest {



    @MockBean
    private AwardWinnerDAO awardWinnerDAOMock;
    @MockBean
    private AwardWinnerErrorDAO awardWinnerErrorDAOMock;
    @Autowired
    private AwardWinnerService awardWinnerService;


    @PostConstruct
    public void configureTest() {

    }


    @Test
    public void update_OK() {
    }


    @Test
    public void update_KO() {

    }


}