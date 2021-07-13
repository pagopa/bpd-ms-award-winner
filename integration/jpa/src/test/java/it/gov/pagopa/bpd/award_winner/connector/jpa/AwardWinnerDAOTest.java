package it.gov.pagopa.bpd.award_winner.connector.jpa;

import eu.sia.meda.layers.connector.query.CriteriaQuery;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.common.connector.jpa.BaseCrudJpaDAOTest;
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.function.Function;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AwardWinnerDAOTest extends BaseCrudJpaDAOTest<AwardWinnerDAO, AwardWinner, Long> {

    @Autowired
    private AwardWinnerDAO awardWinnerDAO;

    @Override
    protected CriteriaQuery<? super AwardWinner> getMatchAlreadySavedCriteria() {
        AwardWinnerDAOTest.AwardWWinnerCriteria criteriaQuery = new AwardWinnerDAOTest.AwardWWinnerCriteria();
        criteriaQuery.setId(getStoredId());
        return criteriaQuery;
    }

    @Override
    protected AwardWinnerDAO getDao() {
        return awardWinnerDAO;
    }

    @Override
    protected void setId(AwardWinner entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected Long getId(AwardWinner entity) {
        return entity.getId();
    }

    @Override
    protected void alterEntityToUpdate(AwardWinner entity) {
        entity.setPayoffInstr("changed");
    }

    @Override
    protected Function<Integer, Long> idBuilderFn() {
        return Long::valueOf;
    }

    @Override
    protected String getIdName() {
        return "id";
    }

    @Data
    private static class AwardWWinnerCriteria implements CriteriaQuery<AwardWinner> {
        private Long id;
    }
}