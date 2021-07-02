package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntegratedPaymentMapper {

    public AwardWinner map(Optional<AwardWinner> integratedPayment) {

        AwardWinner awardWinner = null;

        if (integratedPayment.isPresent()) {
            awardWinner = AwardWinner.builder().build();
            BeanUtils.copyProperties(integratedPayment.get(), awardWinner);
            awardWinner.setStatus(AwardWinner.Status.NEW);
            awardWinner.setResult(null);
            awardWinner.setCro(null);
            awardWinner.setExecutionDate(null);
            awardWinner.setResultReason(null);
            awardWinner.setInsertUser("INTEGRATIVO");
        }

        return awardWinner;

    }

}
