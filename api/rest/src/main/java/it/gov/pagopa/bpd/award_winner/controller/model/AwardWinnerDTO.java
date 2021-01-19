package it.gov.pagopa.bpd.award_winner.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import it.gov.pagopa.bpd.common.converter.UpperCaseConverter;
import it.gov.pagopa.bpd.common.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class AwardWinnerDTO {

    @ApiModelProperty(value = "${swagger.awardWinner.fiscalCode}", required = true)
    @JsonProperty(required = true)
    @NotBlank
    @Size(min = 16, max = 16)
    @JsonDeserialize(converter = UpperCaseConverter.class)
    // FIXME rimuovere la dipendenza dal pom bpd-ms-award-winner-api-rest
    @Pattern(regexp = Constants.FISCAL_CODE_REGEX)
    private String fiscalCode;
    //    @FutureOrPresent//FIXME
    @ApiModelProperty(value = "${swagger.awardWinner.activationDate}", required = true)
    @JsonProperty(required = true)
    private OffsetDateTime activationDate;

    private String channel;

    @ApiModelProperty(value = "${swagger.awardWinner.tokenPanList}")
    @JsonProperty()
    private List<String> tokenPanList;

}
