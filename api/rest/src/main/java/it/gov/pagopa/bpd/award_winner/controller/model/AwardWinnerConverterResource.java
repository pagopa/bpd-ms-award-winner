package it.gov.pagopa.bpd.award_winner.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AwardWinnerConverterResource {

    @ApiModelProperty(value = "${swagger.awardWinner.channel}", required = true)
    @JsonProperty(required = true)
    private String channel;
    @JsonProperty(required = true)
    private Long count;
}
