package it.gov.pagopa.bpd.award_winner.service;

import java.time.LocalDate;

public interface AwardPeriodConnectorService {

    Long findAwardPeriodId(LocalDate periodStart, LocalDate periodEnd);

}
