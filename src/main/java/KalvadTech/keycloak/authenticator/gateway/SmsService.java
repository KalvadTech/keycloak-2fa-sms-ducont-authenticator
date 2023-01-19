package KalvadTech.keycloak.authenticator.gateway;

import java.util.Map;

/**
 * @author Niko Köbler, https://www.n-k.de, @KalvadTech
 */
public interface SmsService {

	void send(String phoneNumber, String message);

}
