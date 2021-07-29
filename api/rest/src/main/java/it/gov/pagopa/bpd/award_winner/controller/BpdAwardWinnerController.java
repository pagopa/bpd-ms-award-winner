package it.gov.pagopa.bpd.award_winner.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller to expose MicroService
 */
@Api(tags = "Bonus Pagamenti Digitali award-winner Controller")
@RequestMapping("/bpd/award-winners")
public interface BpdAwardWinnerController {

    @PostMapping(value = "/resubmitInfoPayments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void resubmitInfoPayments() throws Exception;

}
