package it.gov.pagopa.bpd.award_winner.connector.jpa;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.common.connector.jpa.CrudJpaDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object to manage all CRUD operations to the database
 */
@Repository
public interface AwardWinnerDAO extends CrudJpaDAO<AwardWinner, Long> {


    @Query(nativeQuery = true, value = "SELECT 1 from bpd_citizen.integration_bpd_award_winner()")
    void updateWinnerTwiceWeek(@Param("is_no_iban_enabled") Boolean isNoIbanEnabled,
                               @Param("is_correttivi_enabled") Boolean isCorrettiviEnabled,
                               @Param("is_integrativi_enabled") Boolean isIntegrativiEnabled);


    @Query(
            "select baw from AwardWinner baw " +
                    "where baw.fiscalCode = :fiscalCode " +
                    "and baw.ticketId = :ticketId " +
                    "and baw.relatedPaymentId = :relatedPaymentId "
    )
    AwardWinner getAwardWinner(@Param("fiscalCode") String fiscalCode,
                               @Param("ticketId") Long ticketId,
                               @Param("relatedPaymentId") Long relatedPaymentId);


    @Query(nativeQuery=true,
            value="select max(id_n) from bpd_citizen.bpd_award_winner baw")
    Long getId();

}
