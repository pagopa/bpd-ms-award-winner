package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @See AwardWinnerService
 */
@Service
class AwardWinnerServiceImpl extends BaseService implements AwardWinnerService {

    private final AwardWinnerDAO awardWinnerDAO;


    @Autowired
    public AwardWinnerServiceImpl(AwardWinnerDAO awardWinnerDAO) {
        this.awardWinnerDAO = awardWinnerDAO;
    }

    @Override
    public AwardWinner updateAwardWinner(AwardWinner awardWinner) throws Exception {

        Optional<AwardWinner> storedAwardWinner = awardWinnerDAO.findById(awardWinner.getId());

        if(!storedAwardWinner.isPresent()){
            throw new Exception("Id not found");
        }

        storedAwardWinner.get().setResult(awardWinner.getResult());
        storedAwardWinner.get().setResultReason(awardWinner.getResultReason());
        storedAwardWinner.get().setCro(awardWinner.getCro());
        storedAwardWinner.get().setExecutionDate(awardWinner.getExecutionDate());

        return awardWinnerDAO.update(storedAwardWinner.get());
    }
}
