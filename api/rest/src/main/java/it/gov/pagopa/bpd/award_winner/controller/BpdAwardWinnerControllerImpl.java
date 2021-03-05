package it.gov.pagopa.bpd.award_winner.controller;

import eu.sia.meda.core.controller.StatelessController;
import it.gov.pagopa.bpd.award_winner.command.SubmitFlaggedRecordsCommand;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerIntegration;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * See {@link BpdAwardWinnerController}
 */
@RestController
class BpdAwardWinnerControllerImpl extends StatelessController implements BpdAwardWinnerController {

    private final BeanFactory beanFactory;

    @Autowired
    BpdAwardWinnerControllerImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void resubmitInfoPayments() throws Exception {
        SubmitFlaggedRecordsCommand command =
                beanFactory.getBean(SubmitFlaggedRecordsCommand.class);
        command.execute();
    }

}
