package it.gov.pagopa.bpd.award_winner.connector.jpa.model;

import it.gov.pagopa.bpd.common.connector.jpa.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

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
    private Long id;

    @Column(name = "cashback_n")
    private BigDecimal cashback;

    @Column(name = "fiscal_code_s")
    private String fiscalCode;

    @Column(name = "account_holder_cf_s")
    private String accountHolderFiscalCode;

    @Column(name = "payoff_instr_s")
    private String payoffInstr;

    @Column(name = "account_holder_name_s")
    private String accountHolderName;

    @Column(name = "account_holder_surname_s")
    private String accountHolderSurname;

    @Column(name = "check_instr_status_s")
    private String checkInstrStatus;

    @Column(name = "award_period_id_n")
    private Long awardPeriodId;

    @Column(name = "aw_period_start_d")
    private LocalDate awardPeriodStart;

    @Column(name = "aw_period_end_d")
    private LocalDate awardPeriodEnd;

    @Column(name = "amount_n")
    private BigDecimal amount;

    @Column(name = "typology_s")
    private String typology;

    @Column(name = "jackpot_n")
    private BigDecimal jackpot;

    @Column(name = "technical_account_holder_s")
    private String technicalAccountHolder;

    @Column(name = "chunk_filename_s")
    private String chunkFilename;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_s")
    private Status status;

    public enum Status {
        NEW, SENT, RECOVERY
    }

    @Column(name = "enabled_b")
    private Boolean enabled;

    @Column(name = "esito_bonifico_s")
    String result;

    @Column(name = "result_reason_s")
    String resultReason;

    @Column(name = "cro_s")
    String cro;

    @Column(name = "data_esecuzione_t")
    LocalDate executionDate;



}




