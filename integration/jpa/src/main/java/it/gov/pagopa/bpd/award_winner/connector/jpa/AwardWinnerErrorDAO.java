package it.gov.pagopa.bpd.award_winner.connector.jpa;


import it.gov.pagopa.bpd.common.connector.jpa.CrudJpaDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object to manage all CRUD operations to the database
 */
@Repository
public interface AwardWinnerErrorDAO extends CrudJpaDAO<AwardWinnerError, Long> {

    List<AwardWinnerError> findByToResubmit(Boolean toResubmit);

}
