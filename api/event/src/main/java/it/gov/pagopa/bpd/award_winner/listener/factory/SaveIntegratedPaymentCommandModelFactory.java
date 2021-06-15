package it.gov.pagopa.bpd.award_winner.listener.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPayment;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPaymentCommandModel;
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
class SaveIntegrationPaymentCommandModelFactory implements
        ModelFactory<Pair<byte[], Headers>, IntegratedPaymentCommandModel> {

    private final ObjectMapper objectMapper;

    @Autowired
    public SaveIntegrationPaymentCommandModelFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @param requestData
     * @return instance of SaveTransactionModel, containing a Transaction instance,
     * mapped from the byte[] payload in the requestData, and the inbound Kafka headers
     */

    @SneakyThrows
    @Override
    public IntegratedPaymentCommandModel createModel(Pair<byte[], Headers> requestData) {
        IntegratedPayment paymentIntegrationAwardWinner = parsePayload(requestData.getLeft());
        return IntegratedPaymentCommandModel.builder()
                .payload(paymentIntegrationAwardWinner)
                .headers(requestData.getRight())
                .build();
    }

    /**
     * Method containing the logic for the parsing of the byte[] payload into an instance of PaymentInfoAwardWinner,
     * using the ObjectMapper
     *
     * @param payload inbound JSON payload in byte[] format, defining a PaymentInfoAwardWinner
     * @return instance of PaymentInfoAwardWinner, mapped from the input json byte[] payload
     */
    private IntegratedPayment parsePayload(byte[] payload) {
        String json = new String(payload, StandardCharsets.UTF_8);
        try {
            return objectMapper.readValue(json, IntegratedPayment.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("Cannot parse the payload as a valid %s", IntegratedPayment.class), e);
        }
    }
}
