package it.gov.pagopa.bpd.award_winner.connector.jpa;

import it.gov.pagopa.bpd.common.connector.jpa.CrudJpaDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Data Access Object to manage all CRUD operations to the database
 */
@Repository
public interface AwardWinnerDAO extends CrudJpaDAO<AwardWinner, Long> {

    List<AwardWinner> findByConsapIdAndRelatedIdAndTicketIdAndStatus(
            Long consapId, Long relatedId, Long ticketId, AwardWinner.Status status);

}
