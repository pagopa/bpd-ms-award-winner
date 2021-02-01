package it.gov.pagopa.bpd.award_winner.connector.jpa;


import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.common.connector.jpa.CrudJpaDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object to manage all CRUD operations to the database
 */
@Repository
public interface AwardWinnerErrorDAO extends CrudJpaDAO<AwardWinnerError, String> {

    List<AwardWinnerError> findByToResubmit(Boolean toResubmit);

}
