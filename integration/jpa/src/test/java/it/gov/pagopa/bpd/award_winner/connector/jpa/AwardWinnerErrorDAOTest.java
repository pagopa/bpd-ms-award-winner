package it.gov.pagopa.bpd.award_winner.connector.jpa;


import eu.sia.meda.layers.connector.query.CriteriaQuery;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.common.connector.jpa.BaseCrudJpaDAOTest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class AwardWinnerErrorDAOTest extends BaseCrudJpaDAOTest<AwardWinnerErrorDAO, AwardWinnerError, Long> {

    @Data
    private static class CitizenCriteria implements CriteriaQuery<AwardWinnerError> {
        private Long id;
    }

    @Autowired
    private AwardWinnerErrorDAO awardWinnerErrorDAO;


    @Override
    protected CriteriaQuery<? super AwardWinnerError> getMatchAlreadySavedCriteria() {
        AwardWinnerErrorDAOTest.CitizenCriteria criteriaQuery = new AwardWinnerErrorDAOTest.CitizenCriteria();
        criteriaQuery.setId(getStoredId());

        return criteriaQuery;
    }

    @Override
    protected AwardWinnerErrorDAO getDao() {
        return awardWinnerErrorDAO;
    }


    @Override
    protected void setId(AwardWinnerError entity, Long id) {
        entity.setId(id);
    }


    @Override
    protected Long getId(AwardWinnerError entity) {
        return entity.getId();
    }


    @Override
    protected void alterEntityToUpdate(AwardWinnerError entity) {
        entity.setResultReason("changed");
    }


    @Override
    protected Function<Integer, Long> idBuilderFn() {
        return Long::valueOf;
    }


    @Override
    protected String getIdName() {
        return "id";
    }
}