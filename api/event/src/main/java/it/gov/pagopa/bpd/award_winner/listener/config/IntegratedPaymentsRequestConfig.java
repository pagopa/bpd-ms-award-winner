package it.gov.pagopa.bpd.award_winner.listener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for the OnIntegratedPaymentRequestListener class
 */

@Configuration
@PropertySource("classpath:config/integratedPaymentRequestListener.properties")
public class IntegratedPaymentsRequestConfig {
}
