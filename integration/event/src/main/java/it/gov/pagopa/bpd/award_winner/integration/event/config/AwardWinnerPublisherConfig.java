package it.gov.pagopa.bpd.award_winner.integration.event.config;

import it.gov.pagopa.bpd.award_winner.integration.event.AwardWinnerPublisherConnector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for {@link AwardWinnerPublisherConnector}
 */
@Configuration
@PropertySource("classpath:config/awardWinnerPublisher.properties")
public class AwardWinnerPublisherConfig {
}
