package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.CitizenReplicaDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.IOException;
import java.util.Optional;

/**
 * @See AwardWinnerService
 */
@Service
class AwardWinnerServiceImpl extends BaseService implements AwardWinnerService {

    private final AwardWinnerDAO awardWinnerDAO;
    private final CitizenReplicaDAO citizenReplicaDAO;
    @Value("${core.AwardWinnerService.updatingWinnersTwiceWeeks.is_no_iban_enabled}")
    private Boolean isNoIbanEnabled;
    @Value("${core.AwardWinnerService.updatingWinnersTwiceWeeks.is_correttivi_enabled}")
    private Boolean isCorrettiviEnabled;
    @Value("${core.AwardWinnerService.updatingWinnersTwiceWeeks.is_integrativi_enabled}")
    private Boolean isIntegrativiEnabled;


    @Autowired
    public AwardWinnerServiceImpl(AwardWinnerDAO awardWinnerDAO,
                                  CitizenReplicaDAO citizenReplicaDAO) {
        this.awardWinnerDAO = awardWinnerDAO;
        this.citizenReplicaDAO = citizenReplicaDAO;
    }

    @Override
    public AwardWinner updateAwardWinner(AwardWinner awardWinner) throws Exception {

        Optional<AwardWinner> storedAwardWinner = awardWinnerDAO.findById(awardWinner.getId());

        if (!storedAwardWinner.isPresent()) {
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
    @Scheduled(cron = "${core.AwardWinnerService.updatingWinnersTwiceWeeks.scheduler}")
    public void updatingWinnersTwiceWeeks() throws IOException {

        if (logger.isInfoEnabled()) {
            logger.info("AwardWinnerServiceImpl.updateAwardWinners start");
        }

        awardWinnerDAO.updateWinnerTwiceWeek(isNoIbanEnabled, isCorrettiviEnabled, isIntegrativiEnabled);

        if (logger.isInfoEnabled()) {
            logger.info("AwardWinnerServiceImpl.updateAwardWinners end");
        }
    }

    @Override
    public AwardWinner insertIntegrationAwardWinner(AwardWinner awardWinner) throws Exception {

        Optional<Citizen> citizen = citizenReplicaDAO.findById(awardWinner.getFiscalCode());

        if(!citizen.isPresent()){
            throw new Exception("Citizen not found");
        }

        List<AwardWinner> awardWinnerList = awardWinnerDAO
                .findByConsapIdAndRelatedPaymentIdAndTicketIdAndStatus(
                        awardWinner.getConsapId(),
                        awardWinner.getRelatedPaymentId(),
                        awardWinner.getTicketId(),
                        awardWinner.getStatus()
                );

        if (!awardWinnerList.isEmpty()) {
            throw new Exception("Existing integration payment with equal ids");
        }

        return awardWinnerDAO.update(awardWinner);

    }

}
