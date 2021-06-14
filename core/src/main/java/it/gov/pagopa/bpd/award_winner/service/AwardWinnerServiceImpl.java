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

        AwardWinner found = storedAwardWinner.get();

        found.setResult(awardWinner.getResult());
        found.setResultReason(awardWinner.getResultReason());
        found.setCro(awardWinner.getCro());
        found.setExecutionDate(awardWinner.getExecutionDate());
        found.setToNotify(Boolean.TRUE);
        found.setNotifyTimes(0L);

        return awardWinnerDAO.update(found);
    }
}
