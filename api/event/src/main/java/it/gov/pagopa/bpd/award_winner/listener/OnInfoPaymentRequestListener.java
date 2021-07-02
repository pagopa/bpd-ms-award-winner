package it.gov.pagopa.bpd.award_winner.listener;

import eu.sia.meda.eventlistener.BaseConsumerAwareEventListener;
import it.gov.pagopa.bpd.award_winner.command.InsertIntegratedPaymentCommand;
import it.gov.pagopa.bpd.award_winner.command.SavePaymentInfoOnErrorCommand;
import it.gov.pagopa.bpd.award_winner.command.UpdateAwardWinnerCommand;
import it.gov.pagopa.bpd.award_winner.constants.ListenerHeaders;
import it.gov.pagopa.bpd.award_winner.listener.factory.ModelFactory;
import it.gov.pagopa.bpd.award_winner.listener.factory.SaveOnErrorCommandModelFactory;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerCommandModel;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPaymentCommandModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
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
public class OnInfoPaymentRequestListener extends BaseConsumerAwareEventListener {

    private final ModelFactory<Pair<byte[], Headers>, AwardWinnerCommandModel> updateAwardWinnerCommandModelFactory;
    private final ModelFactory<Pair<byte[], Headers>, IntegratedPaymentCommandModel> integratedPaymentCommandModelModelFactory;
    private final SaveOnErrorCommandModelFactory saveAwardWinnerErrorCommandModelFactory;
    private final BeanFactory beanFactory;

    @Autowired
    public OnInfoPaymentRequestListener(
            ModelFactory<Pair<byte[], Headers>, AwardWinnerCommandModel> updateAwardWinnerCommandModelFactory,
            ModelFactory<Pair<byte[], Headers>, IntegratedPaymentCommandModel> integratedPaymentCommandModelModelFactory,
            SaveOnErrorCommandModelFactory saveAwardWinnerErrorCommandModelFactory,
            BeanFactory beanFactory) {
        this.updateAwardWinnerCommandModelFactory = updateAwardWinnerCommandModelFactory;
        this.saveAwardWinnerErrorCommandModelFactory = saveAwardWinnerErrorCommandModelFactory;
        this.integratedPaymentCommandModelModelFactory = integratedPaymentCommandModelModelFactory;
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

        AwardWinnerCommandModel awardWinnerCommandModel = null;
        AwardWinnerErrorCommandModel awardWinnerErrorCommandModel = null;

        IntegratedPaymentCommandModel integratedPaymentCommandModel = null;


        if (headers.toArray() != null && headers.toArray().length > 0 &&
                Arrays.stream(headers.toArray()).anyMatch(h -> ((RecordHeader) h).key().equals(ListenerHeaders.PAYMENT_INFO_HEADER))) {

            try {

                if (log.isDebugEnabled()) {
                    log.debug("Processing new request on inbound queue");
                }

                awardWinnerCommandModel = updateAwardWinnerCommandModelFactory
                        .createModel(Pair.of(payload, headers));
                UpdateAwardWinnerCommand command = beanFactory.getBean(
                        UpdateAwardWinnerCommand.class, awardWinnerCommandModel);


                if (!command.execute()) {
                    throw new Exception("Failed to execute UpdateAwardWinnerCommand");
                }

                if (log.isDebugEnabled()) {
                    log.debug("UpdateAwardWinnerCommand successfully executed for inbound message");
                } else if (headers.lastHeader(ListenerHeaders.INTEGRATION_PAYMENT_HEADER) == null ||
                        !Arrays.equals(headers.lastHeader(ListenerHeaders.INTEGRATION_PAYMENT_HEADER).value(),
                                "true".getBytes())) {

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

                awardWinnerErrorCommandModel = saveAwardWinnerErrorCommandModelFactory
                        .createModel(Pair.of(payload, headers), error, this);

                SavePaymentInfoOnErrorCommand errorCommand = beanFactory.getBean(
                        SavePaymentInfoOnErrorCommand.class, awardWinnerErrorCommandModel);

                if (!errorCommand.execute()) {
                    throw new Exception("Failed to execute SavePaymentInfoOnErrorCommand");
                }

                if (log.isDebugEnabled()) {
                    log.debug("SavePaymentInfoOnErrorCommand successfully executed for inbound message");
                }
            }

        } else if (headers.toArray() != null && headers.toArray().length > 0 &&
                Arrays.stream(headers.toArray()).anyMatch(h -> ((RecordHeader) h).key().equals(ListenerHeaders.INTEGRATION_PAYMENT_HEADER))) {

            try {

                if (log.isDebugEnabled()) {
                    log.debug("Processing new request on inbound queue");
                }

                integratedPaymentCommandModel = integratedPaymentCommandModelModelFactory
                        .createModel(Pair.of(payload, headers));
                InsertIntegratedPaymentCommand command = beanFactory.getBean(
                        InsertIntegratedPaymentCommand.class, integratedPaymentCommandModel);


                if (!command.execute()) {
                    throw new Exception("Failed to execute InsertIntegratedPaymentCommand");
                }

                if (log.isDebugEnabled()) {
                    log.debug("InsertIntegratedPaymentCommand successfully executed for inbound message");
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

                if (integratedPaymentCommandModel != null && integratedPaymentCommandModel.getPayload() != null) {
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

            }
        }
    }

}
