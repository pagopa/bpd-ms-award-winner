package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerIntegrationErrorMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerIntegrationErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Base implementation of the SavePaymentIntegrationOnErrorCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
class SavePaymentIntegrationOnErrorCommandImpl extends BaseCommand<Boolean> implements SavePaymentInfoOnErrorCommand {


    private AwardWinnerIntegrationErrorCommandModel awardWinnerIntegrationErrorCommandModel;
    private AwardWinnerErrorService awardWinnerErrorService;
    private AwardWinnerIntegrationErrorMapper awardWinnerIntegrationErrorMapper;

    public SavePaymentIntegrationOnErrorCommandImpl(AwardWinnerIntegrationErrorCommandModel awardWinnerIntegrationErrorCommandModel) {
        this.awardWinnerIntegrationErrorCommandModel = awardWinnerIntegrationErrorCommandModel;
    }

    public SavePaymentIntegrationOnErrorCommandImpl(
            AwardWinnerIntegrationErrorCommandModel awardWinnerIntegrationErrorCommandModel,
            AwardWinnerErrorService awardWinnerErrorService,
            AwardWinnerIntegrationErrorMapper awardWinnerIntegrationErrorMapper) {
        this.awardWinnerIntegrationErrorCommandModel = awardWinnerIntegrationErrorCommandModel;
        this.awardWinnerErrorService = awardWinnerErrorService;
        this.awardWinnerIntegrationErrorMapper = awardWinnerIntegrationErrorMapper;
    }

    @SneakyThrows
    @Override
    public Boolean doExecute() {

        try {

            if (logger.isDebugEnabled()) {
                logger.debug("Saving error record for awardWinnerIntegration");
            }

            AwardWinnerError awardWinnerError = awardWinnerIntegrationErrorMapper
                    .map(awardWinnerIntegrationErrorCommandModel);
            awardWinnerErrorService.saveErrorRecord(awardWinnerError);

            if (logger.isDebugEnabled()) {
                logger.debug("Saved error record for awardWinner:");
            }

            return true;

        } catch (Exception e) {

            if (awardWinnerIntegrationErrorCommandModel != null && awardWinnerIntegrationErrorCommandModel.getPayload() != null) {

                if (logger.isErrorEnabled()) {
                    logger.error("Error occured during processing for awardWinnerErrorIntegration:");
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
    public void setAwardWinnerIntegrationErrorMapper(
            AwardWinnerIntegrationErrorMapper awardWinnerIntegrationErrorMapper) {
        this.awardWinnerIntegrationErrorMapper = awardWinnerIntegrationErrorMapper;
    }

}
