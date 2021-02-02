package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerErrorMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.model.constants.AwardWinnerErrorConstants;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Base implementation of the SavePaymentInfoOnErrorCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
class SavePaymentInfoOnErrorCommandImpl extends BaseCommand<Boolean> implements SavePaymentInfoOnErrorCommand {


    private AwardWinnerErrorCommandModel awardWinnerErrorCommandModel;
    private AwardWinnerErrorService awardWinnerErrorService;
    private AwardWinnerErrorMapper awardWinnerErrorMapper;

    public SavePaymentInfoOnErrorCommandImpl(AwardWinnerErrorCommandModel awardWinnerErrorCommandModel) {
        this.awardWinnerErrorCommandModel = awardWinnerErrorCommandModel;
    }

    public SavePaymentInfoOnErrorCommandImpl(
            AwardWinnerErrorCommandModel awardWinnerErrorCommandModel,
            AwardWinnerErrorService awardWinnerErrorService,
            AwardWinnerErrorMapper awardWinnerErrorMapper) {
        this.awardWinnerErrorCommandModel = awardWinnerErrorCommandModel;
        this.awardWinnerErrorService = awardWinnerErrorService;
        this.awardWinnerErrorMapper = awardWinnerErrorMapper;
    }

    @SneakyThrows
    @Override
    public Boolean doExecute() {

        PaymentInfoAwardWinner paymentInfoAwardWinner = awardWinnerErrorCommandModel.getPayload();
        Headers headers = awardWinnerErrorCommandModel.getHeaders();

        try {

            AwardWinnerError awardWinnerError = awardWinnerErrorMapper.map(paymentInfoAwardWinner,
                    awardWinnerErrorCommandModel.getExceptionDescription());
            awardWinnerError.setToResubmit(false);
//            Header listenerHeader = headers.lastHeader(AwardWinnerErrorConstants.LISTENER_HEADER);
//            awardWinnerError.setOriginListener(
//                    listenerHeader == null ? null : new String(listenerHeader.value()));
//            String originTopic = listenerHeader == null ? "bpd-consap" : AwardWinnerErrorConstants
//                    .originListenerToTopic.get(new String(listenerHeader.value()));
//            awardWinnerError.setOriginTopic(originTopic);
            Header requestIdHeader = headers.lastHeader(AwardWinnerErrorConstants.REQUEST_ID_HEADER);
            awardWinnerError.setOriginRequestId(
                    requestIdHeader == null ? null : new String(requestIdHeader.value()));
            awardWinnerError.setRecordId(UUID.randomUUID().toString());
            awardWinnerErrorService.saveErrorRecord(awardWinnerError);

            return true;

        } catch (Exception e) {

            if (paymentInfoAwardWinner != null) {

                if (logger.isErrorEnabled()) {
                    logger.error("Error occured during processing for awardWinnerError: " +
                            paymentInfoAwardWinner.getUniqueID());
                    logger.error(e.getMessage(), e);
                }

            }

            throw e;

        }

    }


    @Autowired
    public void setAwardWinnerErrorService(AwardWinnerErrorService awardWinnerErrorService) {
        this.awardWinnerErrorService = awardWinnerErrorService;
    }

    @Autowired
    public void setAwardWinnerErrorMapper(AwardWinnerErrorMapper awardWinnerErrorMapper) {
        this.awardWinnerErrorMapper = awardWinnerErrorMapper;
    }

}
