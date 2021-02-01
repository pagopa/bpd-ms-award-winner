package it.gov.pagopa.bpd.award_winner.service;

import it.gov.pagopa.bpd.consap_csv_connector.integration.event.model.PaymentInfo;
import org.apache.kafka.common.header.internals.RecordHeaders;

public interface AwardWinnerPublisherService {

    /**
     * Method that has the logic for publishing an Award Winner Payment Info to the bpd outbound channel,
     * calling on the appropriate connector
     *
     * @param paymentInfo PaymentInfo instance to be published
     */
    void publishBpdTransactionEvent(PaymentInfo paymentInfo, RecordHeaders recordHeaders);

}
