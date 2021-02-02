package it.gov.pagopa.bpd.award_winner.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"result"}, callSuper = false)
public class PaymentInfoAwardWinner {

    //TODO decommentare
//    @NotNull
//    @NotBlank
    String uniqueID;

    @NotNull
    @NotBlank
    String result;

    String resultReason;

    String cro;

    String executionDate;
}
