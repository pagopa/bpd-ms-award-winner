package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.event.transformer.SimpleEventResponseTransformer;
import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.integration.event.AwardWinnerIntegrationPublisherConnector;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentIntegration;
import it.gov.pagopa.bpd.award_winner.transformer.HeaderAwareRequestTransformer;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.stereotype.Service;

/**
 * @See AwardWinnerIntegrationPublisherService
 */
@Service
public class AwardWinnerIntegrationPublisherServiceImpl extends BaseService implements AwardWinnerIntegrationPublisherService {

    private final AwardWinnerIntegrationPublisherConnector awardWinnerIntegrationPublisherConnector;
    private final HeaderAwareRequestTransformer<PaymentIntegration> simpleEventRequestTransformer;
    private final SimpleEventResponseTransformer simpleEventResponseTransformer;

    public AwardWinnerIntegrationPublisherServiceImpl(AwardWinnerIntegrationPublisherConnector awardWinnerIntegrationPublisherConnector,
                                                      HeaderAwareRequestTransformer<PaymentIntegration> simpleEventRequestTransformer,
                                                      SimpleEventResponseTransformer simpleEventResponseTransformer) {
        this.awardWinnerIntegrationPublisherConnector = awardWinnerIntegrationPublisherConnector;
        this.simpleEventRequestTransformer = simpleEventRequestTransformer;
        this.simpleEventResponseTransformer = simpleEventResponseTransformer;
    }

    @Override
    public void publishIntegrationAwardWinnerEvent(PaymentIntegration paymentIntegration, RecordHeaders recordHeaders) {
        awardWinnerIntegrationPublisherConnector.doCall(
                paymentIntegration, simpleEventRequestTransformer, simpleEventResponseTransformer, recordHeaders);
    }
}
