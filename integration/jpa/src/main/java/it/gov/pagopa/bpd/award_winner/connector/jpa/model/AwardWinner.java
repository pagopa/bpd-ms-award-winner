package it.gov.pagopa.bpd.award_winner.connector.jpa.model;

import it.gov.pagopa.bpd.common.connector.jpa.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Table(name = "bpd_award_winner")
public class AwardWinner extends BaseEntity {

    @Id
    @Column(name = "id_n")
    Long id;

    @Column(name = "cashback_n")
    BigDecimal cashback;

    @Column(name = "fiscal_code_s")
    String fiscalCode;

    @Column(name = "account_holder_cf_s")
    String accountHolderFiscalCode;

    @Column(name = "payoff_instr_s")
    String payoffInstr;

    @Column(name = "account_holder_name_s")
    String accountHolderName;

    @Column(name = "account_holder_surname_s")
    String accountHolderSurname;

    @Column(name = "check_instr_status_s")
    String checkInstrStatus;

    @Column(name = "award_period_id_n")
    Long awardPeriodId;

    @Column(name = "aw_period_start_d")
    LocalDate awardPeriodStart;

    @Column(name = "aw_period_end_d")
    LocalDate awardPeriodEnd;

    @Column(name = "amount_n")
    BigDecimal amount;

    @Column(name = "typology_s")
    String typology;

    @Column(name = "jackpot_n")
    BigDecimal jackpot;

    @Column(name = "technical_account_holder_s")
    String technicalAccountHolder;

    @Column(name = "chunk_filename_s")
    String chunkFilename;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_s")
    Status status;

    @Column(name = "consap_id_n")
    Long consapId;

    @Column(name = "esito_bonifico_s")
    String result;

    @Column(name = "result_reason_s")
    String resultReason;

    @Column(name = "cro_s")
    String cro;

    @Column(name = "data_esecuzione_t")
    LocalDate executionDate;

    @Column(name = "ticket_id_n")
    Long ticketId;

    @Column(name = "related_id_n")
    Long relatedPaymentId;

    @Column(name = "issuer_card_id_s")
    String issuerCardId;

    public enum Status {
        NEW, SENT, RECOVERY, INTEGRATION
    }

    @Column(name = "to_notify_b")
    private Boolean toNotify;

    @Column(name = "notify_times_n")
    private Long notifyTimes;

}




