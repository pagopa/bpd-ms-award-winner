package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.mapper.IntegrationAwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerIntegrationCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentIntegrationAwardWinner;
import it.gov.pagopa.bpd.award_winner.service.AwardPeriodConnectorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

/**
 * Base implementation of the UpdateAwardWinnerCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
class InsertAwardWinnerCommandImpl extends BaseCommand<Boolean> implements InsertAwardWinnerCommand {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    private AwardWinnerIntegrationCommandModel awardWinnerIntegrationCommandModel;
    private AwardWinnerService awardWinnerService;
    private AwardPeriodConnectorService awardPeriodConnectorService;
    private IntegrationAwardWinnerMapper awardWinnerMapper;


    public InsertAwardWinnerCommandImpl(AwardWinnerIntegrationCommandModel awardWinnerIntegrationCommandModel) {
        this.awardWinnerIntegrationCommandModel = awardWinnerIntegrationCommandModel;
    }

    public InsertAwardWinnerCommandImpl(
            AwardWinnerIntegrationCommandModel awardWinnerIntegrationCommandModel,
            AwardWinnerService awardWinnerService,
            AwardPeriodConnectorService awardPeriodConnectorService,
            IntegrationAwardWinnerMapper awardWinnerMapper) {
        this.awardWinnerIntegrationCommandModel = awardWinnerIntegrationCommandModel;
        this.awardWinnerService = awardWinnerService;
        this.awardWinnerMapper = awardWinnerMapper;
        this.awardPeriodConnectorService = awardPeriodConnectorService;
    }

    @SneakyThrows
    @Override
    public Boolean doExecute() {

        PaymentIntegrationAwardWinner paymentIntegrationAwardWinner = awardWinnerIntegrationCommandModel.getPayload();

        try {

            if (logger.isDebugEnabled()) {
                logger.debug("Saving integration payment for awardWinner: " +
                        paymentIntegrationAwardWinner.getIdConsap());
            }

            validateRequest(paymentIntegrationAwardWinner);
            AwardWinner awardWinner = awardWinnerMapper.map(paymentIntegrationAwardWinner);

            awardWinner.setAwardPeriodId(awardPeriodConnectorService.findAwardPeriodId(
                    awardWinner.getAwardPeriodStart(),awardWinner.getAwardPeriodEnd()));

            awardWinnerService.insertIntegrationAwardWinner(awardWinner);

            if (logger.isDebugEnabled()) {
                logger.debug("Saved integration payment for awardWinner: " +
                        paymentIntegrationAwardWinner.getIdConsap());
            }

            return true;

        } catch (Exception e) {

            if (paymentIntegrationAwardWinner != null) {

                if (logger.isErrorEnabled()) {
                    logger.error("Error occured during processing for awardWinner: " +
                            paymentIntegrationAwardWinner.getIdConsap());
                    logger.error(e.getMessage(), e);
                }

            }

            throw e;

        }

    }


    @Autowired
    public void setAwardWinnerService(AwardWinnerService awardWinnerService) {
        this.awardWinnerService = awardWinnerService;
    }

    @Autowired
    public void setAwardPeriodConnectorService(AwardPeriodConnectorService awardPeriodConnectorService) {
        this.awardPeriodConnectorService = awardPeriodConnectorService;
    }

    @Autowired
    public void setAwardWinnerMapper(IntegrationAwardWinnerMapper awardWinnerMapper) {
        this.awardWinnerMapper = awardWinnerMapper;
    }


    /**
     * Method to process a validation check for the parsed PaymentInfoAwardWinner request
     *
     * @param request instance of PaymentInfoAwardWinner, parsed from the inbound byte[] payload
     * @throws ConstraintViolationException
     */
    private void validateRequest(PaymentIntegrationAwardWinner request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
