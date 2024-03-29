package it.gov.pagopa.bpd.award_winner.model.constants;

import java.util.HashMap;
import java.util.Map;

public class AwardWinnerErrorConstants {

    public static String REQUEST_ID_HEADER = "x-request-id";
    public static String USER_ID_HEADER = "x-user-id";

    public static Map<String, String> originListenerToTopic;

    static {
        originListenerToTopic = new HashMap<>();
        originListenerToTopic.put(
                "it.gov.pagopa.bpd.award_winner.listener.OnInfoPaymentRequestListener",
                "bpd-winner-outcome");
    }

}
