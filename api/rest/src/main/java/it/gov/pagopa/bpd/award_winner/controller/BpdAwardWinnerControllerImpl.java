package it.gov.pagopa.bpd.award_winner.controller;

import eu.sia.meda.core.controller.StatelessController;
import it.gov.pagopa.bpd.award_winner.controller.factory.ModelFactory;
import it.gov.pagopa.bpd.award_winner.controller.model.AwardWinnerDTO;
import it.gov.pagopa.bpd.award_winner.model.PaymentInfoAwardWinner;
import it.gov.pagopa.bpd.award_winner.service.AwardWinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * See {@link BpdAwardWinnerController}
 */
@RestController
class BpdAwardWinnerControllerImpl extends StatelessController implements BpdAwardWinnerController {

    private final AwardWinnerService awardWinnerService;
    private final ModelFactory<AwardWinnerDTO, PaymentInfoAwardWinner> awardWinnerFactory;


    @Autowired
    public BpdAwardWinnerControllerImpl(AwardWinnerService awardWinnerService,
                                              ModelFactory<AwardWinnerDTO, PaymentInfoAwardWinner> awardWinnerFactory) {
        this.awardWinnerService = awardWinnerService;
        this.awardWinnerFactory = awardWinnerFactory;
    }


//    @Override
//    public AwardWinnerResource find(String hpan, String fiscalCode) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("BpdAwardWinnerControllerImpl.find");
//            logger.debug("hpan = [" + hpan + "]");
//            logger.debug("fiscalCode = [" + fiscalCode + "]");
//        }
//
//        final List<AwardWinner> entity = awardWinnerService.find(hpan, fiscalCode);
//
//        return awardWinnerResourceAssembler.toResource(entity);
//    }

}
