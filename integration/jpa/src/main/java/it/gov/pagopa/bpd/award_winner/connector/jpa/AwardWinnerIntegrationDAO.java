package it.gov.pagopa.bpd.award_winner.connector.jpa;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerIntegration;
import it.gov.pagopa.bpd.common.connector.jpa.CrudJpaDAO;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object to manage all CRUD operations to the database
 */
@Repository
public interface AwardWinnerIntegrationDAO extends CrudJpaDAO<AwardWinnerIntegration, Long> {

}
