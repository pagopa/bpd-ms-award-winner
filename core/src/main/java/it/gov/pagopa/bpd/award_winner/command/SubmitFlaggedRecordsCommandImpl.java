package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentInfo;
import it.gov.pagopa.bpd.award_winner.integration.event.model.PaymentIntegration;
import it.gov.pagopa.bpd.award_winner.mapper.ResubmitAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.mapper.ResubmitIntegrationAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.PaymentIntegrationAwardWinner;
import it.gov.pagopa.bpd.award_winner.model.constants.AwardWinnerErrorConstants;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerIntegrationPublisherService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerPublisherService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Base implementation of the SubmitFlaggedRecordsCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
class SubmitFlaggedRecordsCommandImpl extends BaseCommand<Boolean> implements SubmitFlaggedRecordsCommand {

    private AwardWinnerErrorService awardWinnerErrorService;
    private AwardWinnerPublisherService awardWinnerPublisherService;
    private AwardWinnerIntegrationPublisherService awardWinnerIntegrationPublisherService;
    private ResubmitAwardWinnerMapper resubmitAwardWinnerMapper;
    private ResubmitIntegrationAwardWinnerMapper resubmitIntegrationAwardWinnerMapper;

    public SubmitFlaggedRecordsCommandImpl() {}

    public SubmitFlaggedRecordsCommandImpl(
            AwardWinnerErrorService awardWinnerErrorService,
            ResubmitAwardWinnerMapper resubmitAwardWinnerMapper,
            AwardWinnerPublisherService awardWinnerPublisherService,
            AwardWinnerIntegrationPublisherService awardWinnerIntegrationPublisherService,
            ResubmitIntegrationAwardWinnerMapper resubmitIntegrationAwardWinnerMapper
    ) {
        this.awardWinnerErrorService = awardWinnerErrorService;
        this.resubmitAwardWinnerMapper = resubmitAwardWinnerMapper;
        this.awardWinnerPublisherService = awardWinnerPublisherService;
        this.awardWinnerIntegrationPublisherService = awardWinnerIntegrationPublisherService;
        this.resubmitIntegrationAwardWinnerMapper = resubmitIntegrationAwardWinnerMapper;
    }

    /**
     * Implementation of the MEDA Command doExecute method,
     *
     * @return boolean to indicate if the command is successfully executed
     */

    @SneakyThrows
    @Override
    public Boolean doExecute() {

        try {
            List<AwardWinnerError> awardWinnerErrorList = awardWinnerErrorService.findRecordsToResubmit();
            for (AwardWinnerError awardWinnerError : awardWinnerErrorList) {

                RecordHeaders recordHeaders = new RecordHeaders();
                String requestId = awardWinnerError.getOriginRequestId() == null ?
                        "Resubmitted" :
                        "Resubmitted;".concat(awardWinnerError.getOriginRequestId());
                recordHeaders.add(AwardWinnerErrorConstants.REQUEST_ID_HEADER, requestId.getBytes());
                BaseContextHolder.getApplicationContext().setRequestId(requestId);
                recordHeaders.add(AwardWinnerErrorConstants.USER_ID_HEADER,
                        "bpd-ms-award-winner".getBytes());


                if (awardWinnerError.getIntegrationHeader() == null ||
                        awardWinnerError.getIntegrationHeader().equals("true")) {

                    PaymentInfo paymentInfoAwardWinner = resubmitAwardWinnerMapper.map(awardWinnerError);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Resubmitting info payment for awardWinner: " +
                                paymentInfoAwardWinner.getUniqueID());
                    }

                    awardWinnerPublisherService.publishAwardWinnerEvent(paymentInfoAwardWinner, recordHeaders);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Resubmitted info payment for awardWinner: " +
                                paymentInfoAwardWinner.getUniqueID());
                    }

                } else {

                    PaymentIntegration paymentIntegration =
                            resubmitIntegrationAwardWinnerMapper.map(awardWinnerError);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Resubmitting integration payment for awardWinner: " +
                                awardWinnerError.getIdConsap());
                    }

                    awardWinnerIntegrationPublisherService.publishIntegrationAwardWinnerEvent(
                            paymentIntegration, recordHeaders);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Resubmitted info payment for awardWinner: " +
                                paymentIntegration.getIdConsap());
                    }

                }

                awardWinnerError.setToResubmit(false);
                awardWinnerError.setLastResubmitDate(OffsetDateTime.now());
                awardWinnerErrorService.saveErrorRecord(awardWinnerError);

            }

            return true;

        } catch (Exception e) {

            if (logger.isErrorEnabled()) {
                logger.error("Error occurred while attempting to submit flagged records");
                logger.error(e.getMessage(), e);
            }

            throw e;
        }

    }

    @Autowired
    public void setResubmitAwardWinnerMapper(
            ResubmitAwardWinnerMapper resubmitAwardWinnerMapper) {
        this.resubmitAwardWinnerMapper = resubmitAwardWinnerMapper;
    }

    @Autowired
    public void setResubmitIntegrationAwardWinnerMapper(
            ResubmitIntegrationAwardWinnerMapper resubmitIntegrationAwardWinnerMapper) {
        this.resubmitIntegrationAwardWinnerMapper = resubmitIntegrationAwardWinnerMapper;
    }

    @Autowired
    public void setAwardWinnerErrorService(
            AwardWinnerErrorService awardWinnerErrorService) {
        this.awardWinnerErrorService = awardWinnerErrorService;
    }

    @Autowired
    public void setAwardWinnerPublisherService(
            AwardWinnerPublisherService awardWinnerPublisherService) {
        this.awardWinnerPublisherService = awardWinnerPublisherService;
    }


}
