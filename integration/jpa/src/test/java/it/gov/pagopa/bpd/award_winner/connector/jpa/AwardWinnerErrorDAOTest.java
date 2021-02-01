package it.gov.pagopa.bpd.award_winner.connector.jpa;


import eu.sia.meda.layers.connector.query.CriteriaQuery;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.common.connector.jpa.BaseCrudJpaDAOTest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class AwardWinnerErrorDAOTest extends BaseCrudJpaDAOTest<AwardWinnerErrorDAO, AwardWinnerError, String> {

    @Autowired
    private AwardWinnerErrorDAO awardWinnerErrorDAO;

    @Override
    protected CriteriaQuery<? super AwardWinnerError> getMatchAlreadySavedCriteria() {
        AwardWinnerErrorDAOTest.CitizenCriteria criteriaQuery = new AwardWinnerErrorDAOTest.CitizenCriteria();
        criteriaQuery.setRecordId(String.valueOf(getStoredId()));

        return criteriaQuery;
    }

    @Override
    protected void setId(AwardWinnerError entity, String id) {
        entity.setRecordId(id);
    }

    @Override
    protected AwardWinnerErrorDAO getDao() {
        return awardWinnerErrorDAO;
    }

    @Override
    protected String getId(AwardWinnerError entity) {
        return entity.getRecordId();
    }

    @Override
    protected Function<Integer, String> idBuilderFn() {
        return (bias) -> "recordId" + bias;
    }


    @Override
    protected void alterEntityToUpdate(AwardWinnerError entity) {
        entity.setResultReason("changed");
    }

    @Data
    private static class CitizenCriteria implements CriteriaQuery<AwardWinnerError> {
        private String recordId;
    }


    @Override
    protected String getIdName() {
        return "id";
    }
}