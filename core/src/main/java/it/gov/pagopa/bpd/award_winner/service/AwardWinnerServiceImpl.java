package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerErrorDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @See AwardWinnerService
 */
@Service
class AwardWinnerServiceImpl extends BaseService implements AwardWinnerService {

    private final AwardWinnerDAO awardWinnerDAO;
    private final AwardWinnerErrorDAO awardWinnerErrorDAO;


    @Autowired
    public AwardWinnerServiceImpl(AwardWinnerDAO awardWinnerDAO,
                                        AwardWinnerErrorDAO awardWinnerErrorDAO) {
        this.awardWinnerDAO = awardWinnerDAO;
        this.awardWinnerErrorDAO = awardWinnerErrorDAO;
    }

    @Override
    public void updateAwardWinner(AwardWinner awardWinner) {

    }
}
