package it.gov.pagopa.bpd.award_winner.connector.jpa.model;

import it.gov.pagopa.bpd.common.connector.jpa.model.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Table(name = "bpd_award_winner_error")
public class AwardWinnerError extends BaseEntity {

    @Id
    @Column(name = "id_n")
    private Long id;

    @Column(name = "result_s")
    String result;

    @Column(name = "result_reason_s")
    String resultReason;

    @Column(name = "cro_s")
    String cro;

    @Column(name = "execution_date_t")
    LocalDate executionDate;

    @Column(name="exception_message_s")
    String exceptionMessage;

    @Column(name="last_resubmit_date_t")
    OffsetDateTime lastResubmitDate;

    @Column(name="to_resubmit_b")
    Boolean toResubmit;
}
