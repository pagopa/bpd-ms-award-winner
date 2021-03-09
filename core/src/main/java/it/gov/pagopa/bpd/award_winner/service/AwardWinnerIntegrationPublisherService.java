package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentIntegration;
import org.apache.kafka.common.header.internals.RecordHeaders;

public interface AwardWinnerIntegrationPublisherService {

    /**
     * Method that has the logic for publishing an Award Winner Payment Integration to the bpd outbound channel,
     * calling on the appropriate connector
     *
     * @param paymentIntegration PaymentInfo instance to be published
     */
    void publishIntegrationAwardWinnerEvent(PaymentIntegration paymentIntegration, RecordHeaders recordHeaders);

}
