package it.gov.pagopa.bpd.award_winner.integration.event.config;

import it.gov.pagopa.bpd.award_winner.integration.event.AwardWinnerIntegrationPublisherConnector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for {@link AwardWinnerIntegrationPublisherConnector}
 */
@Configuration
@PropertySource("classpath:config/awardWinnerIntegrationPublisher.properties")
public class AwardWinnerIntegrationPublisherConfig {
}
