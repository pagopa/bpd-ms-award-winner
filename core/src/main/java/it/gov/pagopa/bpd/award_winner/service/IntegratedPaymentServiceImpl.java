package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.mapper.AwardWinnerMapper;
import it.gov.pagopa.bpd.award_winner.mapper.IntegratedPaymentMapper;
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
    private  final IntegratedPaymentMapper integratedPaymentMapper;

    @Autowired
    public IntegratedPaymentServiceImpl(AwardWinnerDAO awardWinnerDAO, IntegratedPaymentMapper integratedPaymentMapper) {
        this.awardWinnerDAO = awardWinnerDAO;
        this.integratedPaymentMapper = integratedPaymentMapper;
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

        AwardWinner awardWinner = integratedPaymentMapper.map(storedAwardWinner);
        Long id = awardWinnerDAO.getId() + 1;


        awardWinner.setAmount(integratedPayment.getAmount());
        awardWinner.setCashback(integratedPayment.getCashbackAmount());
        awardWinner.setJackpot(integratedPayment.getJackpotAmount());
        awardWinner.setId(id);

        return awardWinnerDAO.save(awardWinner);
    }

}
