package it.gov.pagopa.bpd.award_winner.listener.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.eventlistener.BaseListener;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerIntegrationErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.model.PaymentIntegrationAwardWinner;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of the ModelFactory interface, that maps a pair containing Kafka related byte[] payload and Headers
 * into a single model for usage inside the microservice core classes
 */

@Component
public class SaveOnIntegrationErrorCommandModelFactory {

    private final ObjectMapper objectMapper;

    @Autowired
    public SaveOnIntegrationErrorCommandModelFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @param requestData
     * @return instance of SaveTransactionModel, containing a Transaction instance,
     * mapped from the byte[] payload in the requestData, and the inbound Kafka headers
     */

    @SneakyThrows
    public AwardWinnerIntegrationErrorCommandModel createModel(Pair<byte[], Headers> requestData,
                                                               String exceptionDescription, BaseListener originListener) {
        PaymentIntegrationAwardWinner paymentIntegrationAwardWinner = parsePayload(requestData.getLeft());
        return AwardWinnerIntegrationErrorCommandModel.builder()
                .payload(paymentIntegrationAwardWinner)
                .headers(requestData.getRight())
                .exceptionMessage(exceptionDescription)
                .originListener(originListener.getClass().getName())
                .originTopic(originListener.getTopic())
                .build();
    }

    /**
     * Method containing the logic for the parsing of the byte[] payload into an instance of PaymentInfoAwardWinner,
     * using the ObjectMapper
     *
     * @param payload inbound JSON payload in byte[] format, defining a PaymentInfoAwardWinner
     * @return instance of PaymentInfoAwardWinner, mapped from the input json byte[] payload
     */
    private PaymentIntegrationAwardWinner parsePayload(byte[] payload) {
        String json = new String(payload, StandardCharsets.UTF_8);
        try {
            return objectMapper.readValue(json, PaymentIntegrationAwardWinner.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("Cannot parse the payload as a valid %s", PaymentIntegrationAwardWinner.class), e);
        }
    }

}
