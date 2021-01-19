package it.gov.pagopa.bpd.award_winner.controller.factory;

public interface ModelFactory<T, U> {

    U createModel(T dto);

}
