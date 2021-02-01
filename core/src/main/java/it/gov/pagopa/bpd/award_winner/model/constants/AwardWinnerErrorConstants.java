package it.gov.pagopa.bpd.award_winner.model.constants;

import java.util.HashMap;
import java.util.Map;

public class AwardWinnerErrorConstants {

    public static String LISTENER_HEADER = "LISTENER";
    public static String REQUEST_ID_HEADER = "x-request-id";
    public static String USER_ID_HEADER = "x-user-id";

    //TODO modificare Map
    public static Map<String, String> originListenerToTopic;

    static {
        originListenerToTopic = new HashMap<>();
        originListenerToTopic.put(
                "it.gov.pagopa.bpd.payment_instrument.listener.OnTransactionFilterRequestListener",
                "rtd-trx");
        originListenerToTopic.put(
                "it.gov.pagopa.bpd.point_processor.listener.OnTransactionProcessRequestListener",
                "bpd-trx");
        originListenerToTopic.put(
                "it.gov.pagopa.bpd.winning_transaction.listener.OnTransactionSaveRequestListener",
                "bpd-trx-cashback");
    }

}
