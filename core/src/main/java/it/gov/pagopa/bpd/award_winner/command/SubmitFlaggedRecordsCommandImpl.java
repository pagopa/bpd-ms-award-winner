package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.ResubmitAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.constants.AwardWinnerErrorConstants;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerPublisherService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import it.gov.pagopa.bpd.consap_csv_connector.integration.event.model.PaymentInfo;
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
    private AwardWinnerService awardWinnerService;
    private AwardWinnerPublisherService awardWinnerPublisherService;
    private ResubmitAwardWinnerMapper resubmitAwardWinnerMapper;


    public SubmitFlaggedRecordsCommandImpl() {
    }

    public SubmitFlaggedRecordsCommandImpl(
            AwardWinnerErrorService awardWinnerErrorService,
            AwardWinnerService awardWinnerService,
            ResubmitAwardWinnerMapper resubmitAwardWinnerMapper,
            AwardWinnerPublisherService awardWinnerPublisherService
    ) {
        this.awardWinnerErrorService = awardWinnerErrorService;
        this.awardWinnerService = awardWinnerService;
        this.resubmitAwardWinnerMapper = resubmitAwardWinnerMapper;
        this.awardWinnerPublisherService = awardWinnerPublisherService;
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
                PaymentInfo paymentInfoAwardWinner = resubmitAwardWinnerMapper.map(awardWinnerError);

                RecordHeaders recordHeaders = new RecordHeaders();
                String requestId = awardWinnerError.getOriginRequestId() == null ?
                        "Resubmitted" :
                        "Resubmitted;".concat(awardWinnerError.getOriginRequestId());
                recordHeaders.add(AwardWinnerErrorConstants.REQUEST_ID_HEADER, requestId.getBytes());
                BaseContextHolder.getApplicationContext().setRequestId(requestId);
                recordHeaders.add(AwardWinnerErrorConstants.USER_ID_HEADER,
                        "bpd-ms-award-winner".getBytes());
//                recordHeaders.add(AwardWinnerErrorConstants.LISTENER_HEADER,
//                        awardWinnerError.getOriginListener() == null ?
//                                null :
//                                awardWinnerError.getOriginListener().getBytes());

                awardWinnerPublisherService.publishAwardWinnerEvent(paymentInfoAwardWinner, recordHeaders);


//                //TODO eliminare salvataggio su award winner
//                try {
//                    awardWinnerService.updateAwardWinner(paymentInfoAwardWinner);
//                } catch (Exception e) {
//                    awardWinnerError.setExceptionMessage(e.getMessage());
//                }

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
    public void setResubmitAwardWinnerMapper(ResubmitAwardWinnerMapper resubmitAwardWinnerMapper) {
        this.resubmitAwardWinnerMapper = resubmitAwardWinnerMapper;
    }

    @Autowired
    public void setAwardWinnerErrorService(
            AwardWinnerErrorService awardWinnerErrorService) {
        this.awardWinnerErrorService = awardWinnerErrorService;
    }

    @Autowired
    public void setAwardWinnerService(
            AwardWinnerService awardWinnerService) {
        this.awardWinnerService = awardWinnerService;
    }

    @Autowired
    public void setAwardWinnerPublisherService(
            AwardWinnerPublisherService awardWinnerPublisherService) {
        this.awardWinnerPublisherService = awardWinnerPublisherService;
    }


}
