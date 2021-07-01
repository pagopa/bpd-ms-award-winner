package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPayment;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPaymentCommandModel;
import it.gov.pagopa.bpd.award_winner.service.IntegratedPaymentService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

/**
 * Base implementation of the InsertIntegratedPaymentCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class InsertIntegratedPaymentCommandImpl extends BaseCommand<Boolean> implements InsertIntegratedPaymentCommand {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    private IntegratedPaymentCommandModel integratedPaymentCommandModel;
    private IntegratedPaymentService integratedPaymentService;


    public InsertIntegratedPaymentCommandImpl(IntegratedPaymentCommandModel integratedPaymentCommandModel) {
        this.integratedPaymentCommandModel = integratedPaymentCommandModel;
    }

    public InsertIntegratedPaymentCommandImpl(
            IntegratedPaymentCommandModel integratedPaymentCommandModel,
            IntegratedPaymentService integratedPaymentService) {
        this.integratedPaymentCommandModel = integratedPaymentCommandModel;
        this.integratedPaymentService = integratedPaymentService;
    }

    @SneakyThrows
    @Override
    public Boolean doExecute() {

        IntegratedPayment integratedPayment = integratedPaymentCommandModel.getPayload();

        try {

            if (logger.isDebugEnabled()) {
                logger.debug("Saving info payment for awardWinner: " +
                        integratedPayment.getTicketId());
            }

            validateRequest(integratedPayment);

                integratedPaymentService.createRecord(integratedPayment);

                if (logger.isDebugEnabled()) {
                    logger.debug("Saved info payment for awardWinner: " +
                            integratedPayment.getTicketId());
                }
            return true;

        } catch (Exception e) {

            if (integratedPayment != null) {

                if (logger.isErrorEnabled()) {
                    logger.error("Error occured during processing for awardWinner: " +
                            integratedPayment.getTicketId());
                    logger.error(e.getMessage(), e);
                }

            }

            throw e;

        }

    }


    @Autowired
    public void setIntegratedPaymentService(IntegratedPaymentService integratedPaymentService) {
        this.integratedPaymentService = integratedPaymentService;
    }


    /**
     * Method to process a validation check for the parsed IntegratedPayment request
     *
     * @param request instance of IntegratedPayment, parsed from the inbound byte[] payload
     * @throws ConstraintViolationException
     */
    private void validateRequest(IntegratedPayment request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
