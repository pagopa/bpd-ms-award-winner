package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerErrorMapper;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerErrorCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerErrorService;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Base implementation of the SavePaymentInfoOnErrorCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
class SavePaymentInfoOnErrorCommandImpl extends BaseCommand<Boolean> implements SavePaymentInfoOnErrorCommand {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    private AwardWinnerErrorCommandModel awardWinnerErrorCommandModel;
    private AwardWinnerErrorService awardWinnerErrorService;
    private AwardWinnerErrorMapper awardWinnerErrorMapper;

    public SavePaymentInfoOnErrorCommandImpl(AwardWinnerErrorCommandModel awardWinnerErrorCommandModel, String description) {
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

        try {

            OffsetDateTime exec_start = OffsetDateTime.now();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss.SSSXXXXX");

            //TODO Abilitare il validate?
//            validateRequest(paymentInfoAwardWinner);

            AwardWinnerError awardWinnerError = awardWinnerErrorMapper.map(paymentInfoAwardWinner, "description");

            OffsetDateTime save_start = OffsetDateTime.now();

            awardWinnerErrorService.saveErrorRecord(awardWinnerError);

            OffsetDateTime save_end = OffsetDateTime.now();

            log.info("Saved AwardWinnerError for id: {}" +
                            "- Started at {}, Ended at {} - Total exec time: {}",
                    awardWinnerError.getId(),
                    dateTimeFormatter.format(save_start),
                    dateTimeFormatter.format(save_end),
                    ChronoUnit.MILLIS.between(save_start, save_end));

            OffsetDateTime end_exec = OffsetDateTime.now();

            log.info("Executed SavePaymentInfoOnErrorCommand for awardWinnerError: {}" +
                            "- Started at {}, Ended at {} - Total exec time: {}",
                    awardWinnerError.getId(),
                    dateTimeFormatter.format(exec_start),
                    dateTimeFormatter.format(end_exec),
                    ChronoUnit.MILLIS.between(exec_start, end_exec));

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


//    /**
//     * Method to process a validation check for the parsed PaymentInfoAwardWinner request
//     *
//     * @param request instance of PaymentInfoAwardWinner, parsed from the inbound byte[] payload
//     * @throws ConstraintViolationException
//     */
//    private void validateRequest(PaymentInfoAwardWinner request) {
//        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
//        if (constraintViolations.size() > 0) {
//            throw new ConstraintViolationException(constraintViolations);
//        }
//    }

}
