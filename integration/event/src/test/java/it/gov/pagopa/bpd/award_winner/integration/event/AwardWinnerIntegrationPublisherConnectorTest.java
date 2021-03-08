package it.gov.pagopa.bpd.award_winner.integration.event;

import eu.sia.meda.event.BaseEventConnectorTest;
import eu.sia.meda.util.TestUtils;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

/**
 * Class for unit testing of the AwardWinnerIntegrationPublisherConnector class
 */
@Import({AwardWinnerIntegrationPublisherConnector.class})
@TestPropertySource(
        locations = "classpath:config/awardWinnerIntegrationPublisher.properties",
        properties = {

                "connectors.eventConfigurations.items.AwardWinnerIntegrationPublisherConnector.bootstrapServers=${spring.embedded.kafka.brokers}"
        })
public class AwardWinnerIntegrationPublisherConnectorTest extends
        BaseEventConnectorTest<PaymentIntegration, Boolean, PaymentIntegration, Void, AwardWinnerIntegrationPublisherConnector> {

    @Value("${connectors.eventConfigurations.items.AwardWinnerIntegrationPublisherConnector.topic}")
    private String topic;

    @Autowired
    private AwardWinnerIntegrationPublisherConnector awardWinnerIntegrationPublisherConnector;

    @Override
    protected AwardWinnerIntegrationPublisherConnector getEventConnector() {
        return awardWinnerIntegrationPublisherConnector;
    }

    @Override
    protected PaymentIntegration getRequestObject() {
        return TestUtils.mockInstance(new PaymentIntegration());
    }

    @Override
    protected String getTopic() {
        return topic;
    }

}