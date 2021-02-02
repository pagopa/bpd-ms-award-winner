package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerErrorMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

        try {

            AwardWinnerError awardWinnerError = awardWinnerErrorMapper.map(awardWinnerErrorCommandModel);
            awardWinnerErrorService.saveErrorRecord(awardWinnerError);

            return true;

        } catch (Exception e) {

            if (awardWinnerErrorCommandModel != null && awardWinnerErrorCommandModel.getPayload() != null) {

                if (logger.isErrorEnabled()) {
                    logger.error("Error occured during processing for awardWinnerError: " +
                            awardWinnerErrorCommandModel.getPayload().getUniqueID());
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
