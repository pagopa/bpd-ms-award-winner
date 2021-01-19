package it.gov.pagopa.bpd.award_winner.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to expose MicroService
 */
@Api(tags = "Bonus Pagamenti Digitali award-winner Controller")
@RequestMapping("/bpd/award-winners")
public interface BpdAwardWinnerController {

//
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    AwardWinnerResource find(
//            @ApiParam(value = "${swagger.awardWinner.hpan}", required = true)
//            @PathVariable("id")
//            @NotBlank
//                    String hpan,
//            @ApiParam(value = "${swagger.awardWinner.fiscalCode}", required = false)
//            @RequestParam(value = "fiscalCode", required = false)
//            @Size(min = 16, max = 16)
//            @JsonDeserialize(converter = UpperCaseConverter.class)
//            @Pattern(regexp = Constants.FISCAL_CODE_REGEX)
//                    String fiscalCode
//    );



}
