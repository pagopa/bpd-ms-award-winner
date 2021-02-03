package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.model.constants.AwardWinnerErrorConstants;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Class to be used to map a {@link PaymentInfoAwardWinner from an* {@link AwardWinner}
 */

@Service
public class AwardWinnerErrorMapper {

    /**
     * @param awardWinnerErrorCommandModel instance of an  {@link AwardWinnerErrorCommandModel}, to be mapped into a {@link AwardWinner}
     * @return {@link AwardWinnerErrorCommandModel} instance from the input paymentInfoAwardWinner,
     */
    public AwardWinnerError map(AwardWinnerErrorCommandModel awardWinnerErrorCommandModel) {

        AwardWinnerError awardWinnerError = null;

        if (awardWinnerErrorCommandModel.getPayload() != null) {
            PaymentInfoAwardWinner payload = awardWinnerErrorCommandModel.getPayload();
            awardWinnerError = AwardWinnerError.builder().build();
            BeanUtils.copyProperties(awardWinnerErrorCommandModel, awardWinnerError);
            BeanUtils.copyProperties(payload, awardWinnerError);
//            awardWinnerError.setResult(payload.getResult());
//            awardWinnerError.setResultReason(payload.getResultReason() == null? null : payload.getResultReason());
//            awardWinnerError.setCro(payload.getCro() == null ? null : payload.getCro());
//            awardWinnerError.setExecutionDate(payload.getExecutionDate() == null ? null : payload.getExecutionDate());
            awardWinnerError.setUniqueID(awardWinnerErrorCommandModel.getPayload().getUniqueID());
            awardWinnerError.setExceptionMessage(awardWinnerErrorCommandModel.getExceptionDescription());
            Header requestIdHeader = awardWinnerErrorCommandModel.getHeaders() == null ? null
                    : awardWinnerErrorCommandModel.getHeaders()
                    .lastHeader(AwardWinnerErrorConstants.REQUEST_ID_HEADER);
            awardWinnerError.setOriginRequestId(
                    requestIdHeader == null ? null : new String(requestIdHeader.value()));
            awardWinnerError.setRecordId(UUID.randomUUID().toString());
            awardWinnerError.setToResubmit(false);
        }

        return awardWinnerError;

    }

}
