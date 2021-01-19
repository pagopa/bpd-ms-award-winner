package it.gov.pagopa.bpd.award_winner.controller.factory;

import it.gov.pagopa.bpd.award_winner.controller.model.AwardWinnerDTO;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Mapper between <AwardWinnerDTO> DTO class and <AwardWinner> Entity class
 */
@Component
public class AwardWinnerFactory implements ModelFactory<AwardWinnerDTO, PaymentInfoAwardWinner> {

    @Override
    public PaymentInfoAwardWinner createModel(AwardWinnerDTO dto) {
        final PaymentInfoAwardWinner result = new PaymentInfoAwardWinner();

        BeanUtils.copyProperties(dto, result);

        return result;
    }
}
