package it.gov.pagopa.bpd.consap_csv_connector.integration.event.config;

import it.gov.pagopa.bpd.consap_csv_connector.integration.event.AwardWinnerPublisherConnector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for {@link AwardWinnerPublisherConnector}
 */
@Configuration
@PropertySource("classpath:config/csvPaymentInfoPublisher.properties")
public class AwardWinnerPublisherConfig {
}
