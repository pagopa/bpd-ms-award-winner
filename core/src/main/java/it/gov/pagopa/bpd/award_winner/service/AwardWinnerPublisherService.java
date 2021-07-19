package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentIntegration;
import org.apache.kafka.common.header.internals.RecordHeaders;

public interface AwardWinnerPublisherService {

    /**
     * Method that has the logic for publishing an Award Winner Payment Info to the bpd outbound channel,
     * calling on the appropriate connector
     *
     * @param paymentInfo PaymentInfo instance to be published
     */
    void publishAwardWinnerEvent(PaymentInfo paymentInfo, RecordHeaders recordHeaders);

}
