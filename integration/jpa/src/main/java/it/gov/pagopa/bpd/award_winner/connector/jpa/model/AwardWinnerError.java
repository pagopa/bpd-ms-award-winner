package it.gov.pagopa.bpd.award_winner.connector.jpa.model;

import it.gov.pagopa.bpd.common.connector.jpa.model.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"recordId"}, callSuper = false)
@Table(name = "bpd_award_winner_error")
public class AwardWinnerError extends BaseEntity {

    @Id
    @Column(name = "record_id_s")
    String recordId;

    @Column(name = "id_s")
    String uniqueID;

    @Column(name = "result_s")
    String result;

    @Column(name = "result_reason_s")
    String resultReason;

    @Column(name = "id_consap_s")
    String idConsap;

    @Column(name = "id_complaint_s")
    String idComplaint;

    @Column(name = "id_pagopa_s")
    String idPagoPa;

    @Column(name = "fiscal_code_s")
    String fiscalCode;

    @Column(name = "iban_s")
    String iban;

    @Column(name = "name_s")
    String name;

    @Column(name = "surname_s")
    String surname;

    @Column(name = "amount_n")
    BigDecimal amount;

    @Column(name = "jackpot_amount_n")
    BigDecimal jackpotAmount;

    @Column(name = "cashback_amount_n")
    BigDecimal cashbackAmount;

    @Column(name = "period_start_date_s")
    String periodStartDate;

    @Column(name = "period_end_date_s")
    String periodEndDate;

    @Column(name = "award_period_id_s")
    String awardPeriodId;

    @Column(name = "technical_count_property_s")
    String technicalCountProperty;

    @Column(name = "cro_s")
    String cro;

    @Column(name = "execution_date_s")
    String executionDate;

    @Column(name = "exception_message_s")
    String exceptionMessage;

    @Column(name = "last_resubmit_date_t")
    OffsetDateTime lastResubmitDate;

    @Column(name = "to_resubmit_b")
    Boolean toResubmit;

    @Column(name = "origin_topic_s")
    String originTopic;

    @Column(name = "origin_listener_s")
    String originListener;

    @Column(name = "origin_request_id_s")
    String originRequestId;

    @Column(name = "origin_integration_header_s")
    String integrationHeader;

}
