package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.CitizenReplicaDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.Citizen;
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

import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:config/awardWinnerService.properties")
@ContextConfiguration(classes = AwardWinnerServiceImpl.class)
public class AwardWinnerServiceImplTest extends BaseTest {

    @MockBean
    private AwardWinnerDAO awardWinnerDAOMock;

    @MockBean
    private CitizenReplicaDAO citizenReplicaDAOMock;

    @Autowired
    private AwardWinnerService awardWinnerService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerDAOMock);
        awardWinnerService = new AwardWinnerServiceImpl(
                awardWinnerDAOMock,citizenReplicaDAOMock);
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

    @Test
    public void insert_OK() throws Exception {
        AwardWinner awardWinner = AwardWinner.builder()
                .fiscalCode("TESTCF").consapId(1L).relatedId(1L).ticketId(1L).build();
        Citizen citizen = new Citizen();
        citizen.setFiscalCode("TESTCF");
        BDDMockito.doReturn(Optional.of(citizen)).when(citizenReplicaDAOMock).findById(awardWinner.getFiscalCode());
        BDDMockito.doReturn(Collections.emptyList()).when(awardWinnerDAOMock)
                .findByConsapIdAndRelatedIdAndTicketIdAndStatus(
                        awardWinner.getConsapId(),awardWinner.getRelatedId(),
                        awardWinner.getTicketId(),awardWinner.getStatus());
        BDDMockito.doReturn(awardWinner).when(awardWinnerDAOMock).update(Mockito.eq(awardWinner));
        awardWinner = awardWinnerService.insertIntegrationAwardWinner(awardWinner);
        Assert.assertNotNull(awardWinner);
        BDDMockito.verify(awardWinnerDAOMock).update(Mockito.eq(awardWinner));
    }

    @Test
    public void insert_KO_CF() throws Exception {
        Citizen citizen = new Citizen();
        citizen.setFiscalCode("TESTCF");
        BDDMockito.doReturn(Optional.of(citizen)).when(citizenReplicaDAOMock).findById("TESTCF");
        expectedException.expect(Exception.class);
        awardWinnerService.insertIntegrationAwardWinner(AwardWinner.builder().fiscalCode("TESTCF1").build());
        BDDMockito.verify(citizenReplicaDAOMock).findById(Mockito.any());
    }

    @Test
    public void insert_KO_ExistingClass() throws Exception {
        AwardWinner awardWinner = AwardWinner.builder()
                .fiscalCode("TESTCF").consapId(1L).relatedId(1L).ticketId(1L).build();
        Citizen citizen = new Citizen();
        citizen.setFiscalCode("TESTCF");
        BDDMockito.doReturn(Optional.of(citizen)).when(citizenReplicaDAOMock).findById("TESTCF");
        BDDMockito.doReturn(Collections.singletonList(awardWinner)).when(awardWinnerDAOMock)
                .findByConsapIdAndRelatedIdAndTicketIdAndStatus(
                        awardWinner.getConsapId(),awardWinner.getRelatedId(),
                        awardWinner.getTicketId(),awardWinner.getStatus());
        expectedException.expect(Exception.class);
        awardWinnerService.insertIntegrationAwardWinner(awardWinner);
        BDDMockito.verify(citizenReplicaDAOMock).findById(Mockito.any());
    }


}