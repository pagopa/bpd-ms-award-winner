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


    @Modifying
    @Query(nativeQuery=true,
            value="UPDATE bpd_citizen.bpd_award_winner baw" +
                    " SET" +
                    " payoff_instr_s = tmp.payoff_instr_s," +
                    " update_date_t = current_timestamp," +
                    " update_user_s= 'CEN-131-NEW_IBAN'," +
                    " account_holder_cf_s = tmp.account_holder_cf_s," +
                    " account_holder_name_s= tmp.account_holder_name_s," +
                    " account_holder_surname_s = tmp.account_holder_surname_s," +
                    " technical_account_holder_s= tmp.technical_account_holder_s," +
                    " issuer_card_id_s = tmp.issuer_card_id_s" +
                    " from (select baw.id_n, bc.payoff_instr_s, baw.esito_bonifico_s, bc.account_holder_cf_s, bc.account_holder_name_s, bc.account_holder_surname_s,  bc.technical_account_holder_s, bc.issuer_card_id_s" +
                    " from bpd_citizen.bpd_award_winner baw" +
                    " inner join bpd_citizen.bpd_citizen bc on baw.fiscal_code_s = bc.fiscal_code_s" +
                    " where (baw.payoff_instr_s is null or baw.payoff_instr_s = '')" +
                    " and bc.payoff_instr_s is not null" +
                    " and bc.payoff_instr_s <> ''" +
                    " and (baw.esito_bonifico_s is not null or baw.esito_bonifico_s = '')" +
                    " and bc.enabled_b is true" +
                    " and (baw.update_date_t < current_timestamp - interval '24 hour' or baw.update_date_t is null)" +
                    " ) tmp" +
                    " WHERE baw.id_n = tmp.id_n;"
    )
    void insertNewIban();


    @Modifying
    @Query(nativeQuery=true,
            value="INSERT INTO bpd_citizen.bpd_award_winner(" +
                    " award_period_id_n," +
                    " fiscal_code_s," +
                    " payoff_instr_s," +
                    " amount_n," +
                    " insert_date_t," +
                    " insert_user_s," +
                    " update_date_t," +
                    " update_user_s," +
                    " enabled_b," +
                    " aw_period_start_d," +
                    " aw_period_end_d," +
                    " jackpot_n," +
                    " cashback_n," +
                    " typology_s," +
                    " account_holder_cf_s," +
                    " account_holder_name_s," +
                    " account_holder_surname_s," +
                    " check_instr_status_s," +
                    " technical_account_holder_s," +
                    " chunk_filename_s," +
                    " status_s," +
                    " to_notify_b," +
                    " notify_times_n," +
                    " notify_id_s," +
                    " esito_bonifico_s," +
                    " cro_s," +
                    " data_esecuzione_t," +
                    " result_reason_s," +
                    " consap_id_n," +
                    " ticket_id_n," +
                    " related_id_n," +
                    " issuer_card_id_s)" +
                    " select" +
                    " baw.award_period_id_n," +
                    " baw.fiscal_code_s," +
                    " bc.payoff_instr_s," +
                    " baw.amount_n," +
                    " current_timestamp," +
                    " 'CEN-131-IBAN_DIFFERENTE'," +
                    " null," +
                    " null," +
                    " true," +
                    " baw.aw_period_start_d," +
                    " baw.aw_period_end_d," +
                    " baw.jackpot_n," +
                    " baw.cashback_n," +
                    " baw.typology_s," +
                    " baw.account_holder_cf_s," +
                    " bc.account_holder_name_s," +
                    " bc.account_holder_surname_s," +
                    " bc.check_instr_status_s," +
                    " bc.technical_account_holder_s," +
                    " null," +
                    " 'NEW'," +
                    " false," +
                    " 0," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " bc.issuer_card_id_s" +
                  " from bpd_citizen.bpd_award_winner baw" +
                  " inner join bpd_citizen.bpd_citizen bc on baw.fiscal_code_s = bc.fiscal_code_s" +
                  " where baw.payoff_instr_s <> bc.payoff_instr_s" +
                  " and esito_bonifico_s <> 'ORDINE ESEGUITO'" +
                  " and bc.payoff_instr_s is not null" +
                  " and bc.enabled_b is true" +
                  " and bc.payoff_instr_s <> ''" +
                  " and baw.update_date_t < current_timestamp - interval '24 hour';"
    )
    void updateIban();


    @Modifying
    @Query(nativeQuery=true,
            value="INSERT INTO bpd_citizen.bpd_award_winner(" +
                    " award_period_id_n," +
                    " fiscal_code_s," +
                    " payoff_instr_s," +
                    " amount_n," +
                    " insert_date_t," +
                    " insert_user_s," +
                    " update_date_t," +
                    " update_user_s," +
                    " enabled_b," +
                    " aw_period_start_d," +
                    " aw_period_end_d," +
                    " jackpot_n," +
                    " cashback_n," +
                    " typology_s," +
                    " account_holder_cf_s," +
                    " account_holder_name_s," +
                    " account_holder_surname_s," +
                    " check_instr_status_s," +
                    " technical_account_holder_s," +
                    " chunk_filename_s," +
                    " status_s," +
                    " to_notify_b," +
                    " notify_times_n," +
                    " notify_id_s," +
                    " esito_bonifico_s," +
                    " cro_s," +
                    " data_esecuzione_t," +
                    " result_reason_s," +
                    " consap_id_n," +
                    " ticket_id_n," +
                    " related_id_n," +
                    " issuer_card_id_s)" +
                    " select" +
                    " coalesce(baw.award_period_id_n, bcr.award_period_id_n)," +
                    " coalesce(baw.fiscal_code_s, bc.fiscal_code_s)," +
                    " coalesce(baw.payoff_instr_s, bc.payoff_instr_s)," +
                    " CASE when (case when bcr.cashback_n > ap.amount_max_n then ap.amount_max_n else bcr.cashback_n end)+ coalesce(baw.cashback_n, 0) > ap.amount_max_n" +
                    " then (ap.amount_max_n - (case when bcr.cashback_n > ap.amount_max_n then ap.amount_max_n else bcr.cashback_n end)) else coalesce(baw.cashback_n, 0) end as amount_n," +
                    " current_timestamp," +
                    " 'CEN-131-DELTA'," +
                    " null," +
                    " null," +
                    " true," +
                    " coalesce(baw.aw_period_start_d, ap.aw_period_start_d)," +
                    " coalesce(baw.aw_period_end_d, ap.aw_period_end_d)," +
                    " coalesce(baw.jackpot_n, 0)," +
                    " CASE when (case when bcr.cashback_n > ap.amount_max_n then ap.amount_max_n else bcr.cashback_n end)+ coalesce(baw.cashback_n, 0) > ap.amount_max_n" +
                    " then (ap.amount_max_n - (case when bcr.cashback_n > ap.amount_max_n then ap.amount_max_n else bcr.cashback_n end)) else coalesce(baw.cashback_n, 0) end as cashback_n," +
                    " coalesce(baw.typology_s, '01')," +
                    " coalesce(baw.account_holder_cf_s, bc.account_holder_cf_s)," +
                    " coalesce(baw.account_holder_name_s, bc.account_holder_name_s)," +
                    " coalesce(baw.account_holder_surname_s, bc.account_holder_surname_s)," +
                    " coalesce(baw.check_instr_status_s, bc.check_instr_status_s)," +
                    " coalesce(baw.technical_account_holder_s, bc.technical_account_holder_s)," +
                    " null," +
                    " 'NEW'," +
                    " false," +
                    " 0," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " null," +
                    " coalesce(baw.issuer_card_id_s, bc.issuer_card_id_s)" +
                  " from bpd_citizen.bpd_citizen bc" +
                  " left outer join bpd_citizen.bpd_award_winner baw on baw.fiscal_code_s = bc.fiscal_code_s" +
                  " inner join bpd_citizen.bpd_citizen_ranking bcr on bc.fiscal_code_s = bcr.fiscal_code_c" +
                  " inner join bpd_award_period.bpd_award_period ap on bcr.award_period_id_n = ap.award_period_id_n" +
                  " where bcr.cashback_n > baw.cashback_n" +
                  " and bcr.transaction_n >= ap.trx_cashback_max_n" +
                  " and bcr.award_period_id_n = baw.award_period_id_n" +
                  " and bc.enabled_b is true" +
                  " and baw.update_date_t < current_timestamp - interval '24 hour';"
    )
    void checkAndUpdateUnprocessedPayment();


    @Query(
            "select baw from AwardWinner baw " +
            "where baw.fiscalCode = :fiscalCode " +
            "and baw.ticketId = :ticketId " +
            "and baw.relatedPaymentId = :relatedPaymentId " +
            "and baw.relatedPaymentId = :relatedPaymentId "
    )
    AwardWinner getAwardWinner(@Param("fiscalCode") String fiscalCode,
                               @Param("ticketId") Long ticketId,
                               @Param("relatedPaymentId") Long relatedPaymentId);

    @Query(nativeQuery=true,
            value="select max(id_n) from bpd_citizen.bpd_award_winner baw")
    Long getId();

}
