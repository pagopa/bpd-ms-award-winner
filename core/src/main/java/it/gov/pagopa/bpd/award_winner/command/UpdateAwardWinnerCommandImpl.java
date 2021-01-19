package it.gov.pagopa.bpd.award_winner.command;

import eu.sia.meda.core.command.BaseCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.model.AwardWinnerCommandModel;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerMapper;
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
 * Base implementation of the UpdateAwardWinnerCommandInterface, extending Meda BaseCommand class, the command
 * represents the class interacted with at api level, hiding the multiple calls to the integration connectors
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
class UpdateAwardWinnerCommandImpl extends BaseCommand<Boolean> implements UpdateAwardWinnerCommand {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    private AwardWinnerCommandModel awardWinnerCommandModel;
    private AwardWinnerService awardWinnerService;
    private AwardWinnerMapper awardWinnerMapper;


    public UpdateAwardWinnerCommandImpl(AwardWinnerCommandModel awardWinnerCommandModel) {
        this.awardWinnerCommandModel = awardWinnerCommandModel;
    }

    public UpdateAwardWinnerCommandImpl(
            AwardWinnerCommandModel awardWinnerCommandModel,
            AwardWinnerService awardWinnerService,
            AwardWinnerMapper awardWinnerMapper) {
        this.awardWinnerCommandModel = awardWinnerCommandModel;
        this.awardWinnerService = awardWinnerService;
        this.awardWinnerMapper = awardWinnerMapper;
    }

    @SneakyThrows
    @Override
    public Boolean doExecute() {

        PaymentInfoAwardWinner paymentInfoAwardWinner = awardWinnerCommandModel.getPayload();

        try {

            OffsetDateTime exec_start = OffsetDateTime.now();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss.SSSXXXXX");

            validateRequest(paymentInfoAwardWinner);

            AwardWinner awardWinner = awardWinnerMapper.map(paymentInfoAwardWinner);

            OffsetDateTime save_start = OffsetDateTime.now();

            awardWinnerService.updateAwardWinner(awardWinner);

            OffsetDateTime save_end = OffsetDateTime.now();

            log.info("Saved AwardWinner for id: {}" +
                            "- Started at {}, Ended at {} - Total exec time: {}",
                    awardWinner.getId(),
                    dateTimeFormatter.format(save_start),
                    dateTimeFormatter.format(save_end),
                    ChronoUnit.MILLIS.between(save_start, save_end));

            OffsetDateTime end_exec = OffsetDateTime.now();

            log.info("Executed UpdateAwardWinnerCommand for awardWinner: {}" +
                            "- Started at {}, Ended at {} - Total exec time: {}",
                    awardWinner.getId(),
                    dateTimeFormatter.format(exec_start),
                    dateTimeFormatter.format(end_exec),
                    ChronoUnit.MILLIS.between(exec_start, end_exec));

            return true;

        } catch (Exception e) {

            if (paymentInfoAwardWinner != null) {

                if (logger.isErrorEnabled()) {
                    logger.error("Error occured during processing for awardWinner: " +
                            paymentInfoAwardWinner.getUniqueID());
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
    public void setAwardWinnerMapper(AwardWinnerMapper awardWinnerMapper) {
        this.awardWinnerMapper = awardWinnerMapper;
    }


    /**
     * Method to process a validation check for the parsed PaymentInfoAwardWinner request
     *
     * @param request instance of PaymentInfoAwardWinner, parsed from the inbound byte[] payload
     * @throws ConstraintViolationException
     */
    private void validateRequest(PaymentInfoAwardWinner request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
