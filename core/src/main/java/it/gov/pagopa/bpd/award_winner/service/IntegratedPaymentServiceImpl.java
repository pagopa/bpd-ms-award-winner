package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.model.IntegratedPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @See IntegratedPaymentService
 */
@Service
public class IntegratedPaymentServiceImpl extends BaseService implements IntegratedPaymentService {


    private final AwardWinnerDAO awardWinnerDAO;

    @Autowired
    public IntegratedPaymentServiceImpl(AwardWinnerDAO awardWinnerDAO) {
        this.awardWinnerDAO = awardWinnerDAO;
    }


    @Override
    public AwardWinner createRecord(IntegratedPayment integratedPayment) throws Exception {

        Optional<AwardWinner> storedAwardWinner = Optional.ofNullable(awardWinnerDAO.getAwardWinner(
                integratedPayment.getFiscalCode(),
                integratedPayment.getTicketId(),
                integratedPayment.getRelatedPaymentId()));

        if(!storedAwardWinner.isPresent()){
            throw new Exception("Related payment Id not found");
        }

        storedAwardWinner.get().setAmount(integratedPayment.getAmount());
        storedAwardWinner.get().setCashback(integratedPayment.getCashbackAmount());
        storedAwardWinner.get().setJackpot(integratedPayment.getJackpotAmount());

        return awardWinnerDAO.save(storedAwardWinner.get());
    }

}
