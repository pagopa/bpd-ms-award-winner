package it.gov.pagopa.bpd.award_winner.integration.event;

import eu.sia.meda.event.BaseEventConnectorTest;
import eu.sia.meda.util.TestUtils;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

/**
 * Class for unit testing of the AwardWinnerPublisherConnector class
 */
@Import({AwardWinnerPublisherConnector.class})
@TestPropertySource(
        locations = "classpath:config/awardWinnerPublisher.properties",
        properties = {

                "connectors.eventConfigurations.items.AwardWinnerPublisherConnector.bootstrapServers=${spring.embedded.kafka.brokers}"
        })
public class AwardWinnerPublisherConnectorTest extends
        BaseEventConnectorTest<PaymentInfo, Boolean, PaymentInfo, Void, AwardWinnerPublisherConnector> {

    @Value("${connectors.eventConfigurations.items.AwardWinnerPublisherConnector.topic}")
    private String topic;

    @Autowired
    private AwardWinnerPublisherConnector awardWinnerPublisherConnector;

    @Override
    protected AwardWinnerPublisherConnector getEventConnector() {
        return awardWinnerPublisherConnector;
    }

    @Override
    protected PaymentInfo getRequestObject() {
        return TestUtils.mockInstance(new PaymentInfo());
    }

    @Override
    protected String getTopic() {
        return topic;
    }

}