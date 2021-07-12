package it.gov.pagopa.bpd.award_winner.listener;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class OnIntegrationPaymentRequestListenerEnabledCondition extends AllNestedConditions {

    public OnIntegrationPaymentRequestListenerEnabledCondition() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(prefix = "listeners.eventConfigurations.items.OnIntegrationPaymentRequestListener",
            name = "enabled",
            havingValue = "true")
    public static class OnIntegrationPaymentRequestListenerEnabled{}

}