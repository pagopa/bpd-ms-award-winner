package it.gov.pagopa.bpd.award_winner.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"uniqueID"}, callSuper = false)
public class PaymentInfoAwardWinner {

    @NotNull
    @NotBlank
    String uniqueID;

    @NotNull
    @NotBlank
    String result;

    String resultReason;

    String cro;

    String executionDate;
}
