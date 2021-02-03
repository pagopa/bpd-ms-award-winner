package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.integration.event.AwardWinnerPublisherConnector;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import it.gov.pagopa.bpd.award_winner.transformer.HeaderAwareRequestTransformer;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.stereotype.Service;

/**
 * @See AwardWinnerPublisherService
 */
@Service
public class AwardWinnerPublisherServiceImpl extends BaseService implements AwardWinnerPublisherService {

    private final AwardWinnerPublisherConnector awardWinnerPublisherConnector;
    private final HeaderAwareRequestTransformer<PaymentInfo> simpleEventRequestTransformer;
    private final SimpleEventResponseTransformer simpleEventResponseTransformer;

    public AwardWinnerPublisherServiceImpl(AwardWinnerPublisherConnector awardWinnerPublisherConnector,
                                           HeaderAwareRequestTransformer<PaymentInfo> simpleEventRequestTransformer,
                                           SimpleEventResponseTransformer simpleEventResponseTransformer) {
        this.awardWinnerPublisherConnector = awardWinnerPublisherConnector;
        this.simpleEventRequestTransformer = simpleEventRequestTransformer;
        this.simpleEventResponseTransformer = simpleEventResponseTransformer;
    }

    @Override
    public void publishAwardWinnerEvent(PaymentInfo paymentInfo, RecordHeaders recordHeaders) {
        awardWinnerPublisherConnector.doCall(
                paymentInfo, simpleEventRequestTransformer, simpleEventResponseTransformer, recordHeaders);
    }
}
