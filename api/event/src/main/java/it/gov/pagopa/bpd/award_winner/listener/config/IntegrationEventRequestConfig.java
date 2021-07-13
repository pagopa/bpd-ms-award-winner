package it.gov.pagopa.bpd.award_winner.listener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for the OnInfoPaymentRequestListener class
 */

@Configuration
@PropertySource("classpath:config/integrationPaymentRequestListener.properties")
public class IntegrationEventRequestConfig {
}
