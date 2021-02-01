package it.gov.pagopa.bpd.award_winner.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_winner.connector.jpa.AwardWinnerErrorDAO;
import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinnerError;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @See AwardWinnerErrorService
 */
@Service
public class AwardWinnerErrorServiceImpl extends BaseService implements AwardWinnerErrorService {

    private final AwardWinnerErrorDAO awardWinnerErrorDAO;

    public AwardWinnerErrorServiceImpl(AwardWinnerErrorDAO awardWinnerErrorDAO) {
        this.awardWinnerErrorDAO = awardWinnerErrorDAO;
    }

    @Override
    public AwardWinnerError saveErrorRecord(AwardWinnerError awardWinnerErrorRecord) {
        return awardWinnerErrorDAO.update(awardWinnerErrorRecord);
    }

    @Override
    public List<AwardWinnerError> findRecordsToResubmit() {
        return awardWinnerErrorDAO.findByToResubmit(true);
    }
}
