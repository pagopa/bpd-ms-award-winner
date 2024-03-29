package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.BaseTest;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerErrorDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AwardWinnerErrorServiceImpl.class)
public class AwardWinnerErrorServiceImplTest extends BaseTest {


    @MockBean
    private AwardWinnerErrorDAO awardWinnerErrorDAOMock;

    @Autowired
    private AwardWinnerErrorService awardWinnerErrorService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initTest() {
        Mockito.reset(awardWinnerErrorDAOMock);
        awardWinnerErrorService = new AwardWinnerErrorServiceImpl(awardWinnerErrorDAOMock);
    }

    @Test
    public void save_OK() {
        AwardWinnerError awardWinnerError = AwardWinnerError.builder().build();
        BDDMockito.doReturn(awardWinnerError).when(awardWinnerErrorDAOMock).update(Mockito.eq(awardWinnerError));
        awardWinnerError = awardWinnerErrorService.saveErrorRecord(awardWinnerError);
        Assert.assertNotNull(awardWinnerError);
        BDDMockito.verify(awardWinnerErrorDAOMock).update(Mockito.eq(awardWinnerError));
    }

    @Test
    public void save_KO() {
        BDDMockito.when(awardWinnerErrorDAOMock.update(Mockito.any())).thenAnswer(
                invocation -> {
                    throw new Exception();
                });
        expectedException.expect(Exception.class);
        awardWinnerErrorService.saveErrorRecord(AwardWinnerError.builder().build());
        BDDMockito.verify(awardWinnerErrorDAOMock).update(Mockito.any());
    }

    @Test
    public void find_OK() {
        AwardWinnerError awardWinnerError = AwardWinnerError.builder().build();
        List<AwardWinnerError> awardWinnerList = new ArrayList<>();
        awardWinnerList.add(awardWinnerError);
        BDDMockito.doReturn(awardWinnerList).when(awardWinnerErrorDAOMock).findByToResubmit(true);
        List<AwardWinnerError> resultList = awardWinnerErrorService.findRecordsToResubmit();
        Assert.assertNotNull(resultList);
        Assert.assertEquals(resultList, awardWinnerList);
        BDDMockito.verify(awardWinnerErrorDAOMock).findByToResubmit(true);
    }


}