package it.gov.pagopa.bpd.award_winner.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "hpan", callSuper = false)
public class AwardWinnerResource {

    @ApiModelProperty(value = "${swagger.awardWinner.hpan}", required = true)
    @JsonProperty(required = true)
    private String hpan;
    @ApiModelProperty(value = "${swagger.awardWinner.fiscalCode}", required = true)
    @JsonProperty(required = true)
    private String fiscalCode;
    @ApiModelProperty(value = "${swagger.awardWinner.activationDate}", required = true)
    @JsonProperty(required = true)
    private OffsetDateTime activationDate;
    @ApiModelProperty(value = "${swagger.awardWinner.deactivationDate}", required = true)
    @JsonProperty(required = true)
    private OffsetDateTime deactivationDate;
    @ApiModelProperty(value = "${swagger.awardWinner.Status}", required = true)
    @JsonProperty(required = true)
    private AwardWinner.Status Status;
    @ApiModelProperty(value = "${swagger.awardWinner.tokenizedInstruments}")
    @JsonProperty()
    private List<TokenizedInstrument> tokenizedInstruments;


}
