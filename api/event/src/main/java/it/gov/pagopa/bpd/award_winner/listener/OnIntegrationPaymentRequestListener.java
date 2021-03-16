package it.gov.pagopa.bpd.award_winner.listener;

import eu.sia.meda.eventlistener.BaseConsumerAwareEventListener;
import it.gov.pagopa.bpd.award_winner.command.InsertAwardWinnerCommand;
import it.gov.pagopa.bpd.award_winner.command.SavePaymentIntegrationOnErrorCommand;
import it.gov.pagopa.bpd.award_winner.constants.ListenerHeaders;
import it.gov.pagopa.bpd.award_winner.listener.factory.ModelFactory;
import it.gov.pagopa.bpd.award_winner.listener.factory.SaveOnIntegrationErrorCommandModelFactory;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerIntegrationCommandModel;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerIntegrationErrorCommandModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Class Extending the MEDA BaseEventListener, manages the inbound requests, and calls on the appropriate
 * command for the check and send logic associated to the Transaction payload
 */

@Service
@Slf4j
public class OnIntegrationPaymentRequestListener extends BaseConsumerAwareEventListener {

    private final ModelFactory<Pair<byte[], Headers>, AwardWinnerIntegrationCommandModel>
            insertAwardWinnerCommandModelFactory;
    private final SaveOnIntegrationErrorCommandModelFactory saveOnIntegrationErrorCommandModelFactory;
    private final BeanFactory beanFactory;

    @Autowired
    public OnIntegrationPaymentRequestListener(
            ModelFactory<Pair<byte[], Headers>, AwardWinnerIntegrationCommandModel> insertAwardWinnerCommandModelFactory,
            SaveOnIntegrationErrorCommandModelFactory saveOnIntegrationErrorCommandModelFactory,
            BeanFactory beanFactory) {
        this.insertAwardWinnerCommandModelFactory = insertAwardWinnerCommandModelFactory;
        this.saveOnIntegrationErrorCommandModelFactory = saveOnIntegrationErrorCommandModelFactory;
        this.beanFactory = beanFactory;
    }

    /**
     * Method called on receiving a message in the inbound queue,
     * that should contain a JSON payload containing transaction data,
     * calls on a command to execute the check and send logic for the input Transaction data
     * In case of error, sends data to an error channel
     *
     * @param payload Message JSON payload in byte[] format
     * @param headers Kafka headers from the inbound message
     */

    @SneakyThrows
    @Override
    public void onReceived(byte[] payload, Headers headers) {

        AwardWinnerIntegrationCommandModel awardWinnerCommandModel = null;
        AwardWinnerIntegrationErrorCommandModel awardWinnerErrorCommandModel = null;

        try {

            if (log.isDebugEnabled()) {
                log.debug("Processing new request on inbound queue");
            }

            awardWinnerCommandModel = insertAwardWinnerCommandModelFactory
                    .createModel(Pair.of(payload, headers));
            InsertAwardWinnerCommand command = beanFactory.getBean(
                    InsertAwardWinnerCommand.class, awardWinnerCommandModel);

            if (headers.lastHeader(ListenerHeaders.INTEGRATION_HEADER) != null &&
                    Arrays.equals(headers.lastHeader(ListenerHeaders.INTEGRATION_HEADER).value(),
                            "true".getBytes())) {

                if (!command.execute()) {
                    throw new Exception("Failed to execute InsertAwardWinnerCommand");
                }

                if (log.isDebugEnabled()) {
                    log.debug("InsertAwardWinnerCommand successfully executed for inbound message");
                }

            }

        } catch (Exception e) {

            String payloadString = "null";
            String error = "Unexpected error during transaction processing";

            try {
                payloadString = new String(payload, StandardCharsets.UTF_8);
            } catch (Exception e2) {
                if (logger.isErrorEnabled()) {
                    logger.error("Something gone wrong converting the payload into String", e2);
                }
            }

            if (awardWinnerCommandModel != null && awardWinnerCommandModel.getPayload() != null) {
                payloadString = new String(payload, StandardCharsets.UTF_8);
                error = String.format("Unexpected error during transaction processing: %s, %s",
                        payloadString, e.getMessage());

            } else if (payload != null) {
                error = String.format("Something gone wrong during the evaluation of the payload: %s, %s",
                        payloadString, e.getMessage());
                if (logger.isErrorEnabled()) {
                    logger.error(error, e);
                }
            }

            awardWinnerErrorCommandModel = saveOnIntegrationErrorCommandModelFactory
                    .createModel(Pair.of(payload, headers), error, this);

            SavePaymentIntegrationOnErrorCommand errorCommand = beanFactory.getBean(
                    SavePaymentIntegrationOnErrorCommand.class, awardWinnerErrorCommandModel);

            if (!errorCommand.execute()) {
                throw new Exception("Failed to execute SavePaymentInfoOnErrorCommand");
            }

            if (log.isDebugEnabled()) {
                log.debug("SavePaymentIntegrationOnErrorCommand successfully executed for inbound message");
            }

        }
    }

}
