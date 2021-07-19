package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.connector.award_period.AwardPeriodRestClient;
import it.gov.pagopa.bpd.award_winner.connector.award_period.model.AwardPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @See AwardPeriodService
 */
@Service
public class AwardPeriodConnectorServiceImpl implements AwardPeriodConnectorService {

    private AwardPeriodRestClient awardPeriodRestClient;

    @Autowired
    public AwardPeriodConnectorServiceImpl(AwardPeriodRestClient awardPeriodRestClient) {
        this.awardPeriodRestClient = awardPeriodRestClient;
    }

    /**
     * Implementation of {@link AwardPeriodConnectorService#findAwardPeriodId} (LocalDate, OffsetDateTime)}, that contacts
     * THe endpoint managed with {@link AwardPeriodRestClient} to recover {@link List <AwardPeriod>}, and recovers
     * the first active period available
     *
     * @param periodStartDate {@link LocalDate} used for searching a {@link AwardPeriod}
     * @param periodEndDate {@link LocalDate} used for searching a {@link AwardPeriod}
     * @return instance of {@link AwardPeriod} associated to the input param
     */
    public Long findAwardPeriodId(LocalDate periodStartDate, LocalDate periodEndDate) {
        List<AwardPeriod> awardPeriods = awardPeriodRestClient.getAwardPeriods();
        return Objects.requireNonNull(
                awardPeriods.stream().sorted(Comparator.comparing(AwardPeriod::getStartDate))
                .filter(awardPeriod -> {
                    LocalDate startDate = awardPeriod.getStartDate();
                    LocalDate endDate = awardPeriod.getEndDate();
                    return (periodStartDate.equals(startDate) && periodEndDate.equals(endDate));
                })
                .findFirst().orElse(null)).getAwardPeriodId();
    }

}
