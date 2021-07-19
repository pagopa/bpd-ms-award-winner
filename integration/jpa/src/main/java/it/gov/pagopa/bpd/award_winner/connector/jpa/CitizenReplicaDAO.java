package it.gov.pagopa.bpd.award_winner.connector.jpa;

import it.gov.pagopa.bpd.award_winner.connector.jpa.model.Citizen;
import it.gov.pagopa.bpd.common.connector.jpa.CrudJpaDAO;
import it.gov.pagopa.bpd.common.connector.jpa.ReadOnlyRepository;


/**
 * Data Access Object to manage all CRUD operations to the database
 */
@ReadOnlyRepository
public interface CitizenReplicaDAO extends CrudJpaDAO<Citizen, String> {

}