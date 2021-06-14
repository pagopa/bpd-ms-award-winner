package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerIntegrationDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.CitizenReplicaDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerIntegration;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @See AwardWinnerService
 */
@Service
class AwardWinnerServiceImpl extends BaseService implements AwardWinnerService {

    private final AwardWinnerDAO awardWinnerDAO;
    private final AwardWinnerIntegrationDAO awardWinnerIntegrationDAO;
    private final CitizenReplicaDAO citizenReplicaDAO;


    @Autowired
    public AwardWinnerServiceImpl(AwardWinnerDAO awardWinnerDAO,
                                  AwardWinnerIntegrationDAO awardWinnerIntegrationDAO,
                                  CitizenReplicaDAO citizenReplicaDAO) {
        this.awardWinnerDAO = awardWinnerDAO;
        this.awardWinnerIntegrationDAO = awardWinnerIntegrationDAO;
        this.citizenReplicaDAO = citizenReplicaDAO;
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

    @Override
    public AwardWinnerIntegration insertIntegrationAwardWinner(AwardWinnerIntegration awardWinner) throws Exception {

        Optional<Citizen> citizen = citizenReplicaDAO.findById(awardWinner.getFiscalCode());

        if(!citizen.isPresent()){
            throw new Exception("Citizen not found");
        }

        return awardWinnerIntegrationDAO.update(awardWinner);
    }

}
