package it.gov.pagopa.bpd.award_winner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@PropertySource("classpath:config/awardWinnerService.properties")
@EnableScheduling
public class SchedulerConfig {
}
