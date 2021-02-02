package it.gov.pagopa.bpd.award_winner.mapper;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.AwardWinner;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * Class to be used to map a {@link PaymentInfoAwardWinner from an* {@link AwardWinner}
 */

@Service
public class AwardWinnerMapper {

    /**
     * @param paymentInfoAwardWinner instance of an  {@link PaymentInfoAwardWinner}, to be mapped into a {@link AwardWinner}
     * @return {@link PaymentInfoAwardWinner} instance from the input paymentInfoAwardWinner,
     */
    public AwardWinner map(
            PaymentInfoAwardWinner paymentInfoAwardWinner) {

        AwardWinner awardWinner = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (paymentInfoAwardWinner != null) {
            awardWinner = AwardWinner.builder().build();
            BeanUtils.copyProperties(paymentInfoAwardWinner, awardWinner);
            //TODO rimuovere check null
            long leftLimit = 1000000000L;
            long rightLimit = 1000000000000L;
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            awardWinner.setId(paymentInfoAwardWinner.getUniqueID() == null ? Long.valueOf(generatedLong) :
                    Long.valueOf(paymentInfoAwardWinner.getUniqueID()));

            //TODO decommentare
//            if(paymentInfoAwardWinner.getExecutionDate()!=null && !paymentInfoAwardWinner.getExecutionDate().isEmpty()){
//                awardWinner.setExecutionDate(LocalDate.parse(paymentInfoAwardWinner.getExecutionDate(), dtf));
//            }
        }

        return awardWinner;

    }

}
