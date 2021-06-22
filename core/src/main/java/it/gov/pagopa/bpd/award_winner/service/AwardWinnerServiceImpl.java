package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.CitizenReplicaDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @See AwardWinnerService
 */
@Service
class AwardWinnerServiceImpl extends BaseService implements AwardWinnerService {

    private final AwardWinnerDAO awardWinnerDAO;
    private final CitizenReplicaDAO citizenReplicaDAO;


    @Autowired
    public AwardWinnerServiceImpl(AwardWinnerDAO awardWinnerDAO,
                                  CitizenReplicaDAO citizenReplicaDAO) {
        this.awardWinnerDAO = awardWinnerDAO;
        this.citizenReplicaDAO = citizenReplicaDAO;
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

    @Override
    public AwardWinner insertIntegrationAwardWinner(AwardWinner awardWinner) throws Exception {

        Optional<Citizen> citizen = citizenReplicaDAO.findById(awardWinner.getFiscalCode());

        if(!citizen.isPresent()){
            throw new Exception("Citizen not found");
        }

        List<AwardWinner> awardWinnerList = awardWinnerDAO
                .findByConsapIdAndRelatedIdAndTicketIdAndStatus(
                        awardWinner.getConsapId(),
                        awardWinner.getRelatedId(),
                        awardWinner.getTicketId(),
                        awardWinner.getStatus()
        );

        if (!awardWinnerList.isEmpty()) {
            throw new Exception("Existing integration payment with equal ids");
        }

        return awardWinnerDAO.update(awardWinner);

    }

}
