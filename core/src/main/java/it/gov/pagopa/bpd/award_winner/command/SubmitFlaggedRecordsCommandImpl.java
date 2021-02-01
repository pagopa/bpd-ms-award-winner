package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.ResubmitAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.constants.AwardWinnerErrorConstants;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
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

    private ResubmitAwardWinnerMapper resubmitAwardWinnerMapper;


    public SubmitFlaggedRecordsCommandImpl() {
    }

    public SubmitFlaggedRecordsCommandImpl(
            AwardWinnerErrorService awardWinnerErrorService,
            AwardWinnerService awardWinnerService,
            ResubmitAwardWinnerMapper resubmitAwardWinnerMapper) {
        this.awardWinnerErrorService = awardWinnerErrorService;
        this.awardWinnerService = awardWinnerService;
        this.resubmitAwardWinnerMapper = resubmitAwardWinnerMapper;
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
                AwardWinner awardWinner = resubmitAwardWinnerMapper.map(awardWinnerError);

                RecordHeaders recordHeaders = new RecordHeaders();
                String requestId = awardWinnerError.getOriginRequestId() == null ?
                        "Resubmitted" :
                        "Resubmitted;".concat(awardWinnerError.getOriginRequestId());
                recordHeaders.add(AwardWinnerErrorConstants.REQUEST_ID_HEADER, requestId.getBytes());
                BaseContextHolder.getApplicationContext().setRequestId(requestId);
                recordHeaders.add(AwardWinnerErrorConstants.USER_ID_HEADER,
                        "bpd-ms-award-winner".getBytes());
                recordHeaders.add(AwardWinnerErrorConstants.LISTENER_HEADER,
                        awardWinnerError.getOriginListener() == null ?
                                null :
                                awardWinnerError.getOriginListener().getBytes());

//               switch (awardWinnerError.getOriginTopic()) {
//                   //TODO implementare publisher, inserire topic corretto e decommentare
//                   case "bpd-trx":
//                       bpdTransactionPublisherService.publishBpdTransactionEvent(transaction, recordHeaders);
//                       break;
//               }

                //TODO eliminare salvataggio su award winner
                try {
                    awardWinnerService.updateAwardWinner(awardWinner);
                } catch (Exception e) {
                    awardWinnerError.setExceptionMessage(e.getMessage());
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


}
