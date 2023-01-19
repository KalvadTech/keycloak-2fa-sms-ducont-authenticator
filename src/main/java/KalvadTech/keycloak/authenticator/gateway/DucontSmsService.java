package KalvadTech.keycloak.authenticator.gateway;

import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.UUID;

/**
 * @author Niko Köbler, https://www.n-k.de, @KalvadTech
 */
public class DucontSmsService implements SmsService {

	private final OkHttpClient client = new OkHttpClient();

	private final String senderId;
	private final String ducontUserId;
	private final String ducontPassword;

	DucontSmsService(Map<String, String> config) {
		senderId = config.get("senderId");
		ducontUserId = config.get("ducontUserId");
		ducontPassword = config.get("ducontPassword");
	}

	@Override
	public void send(String phoneNumber, String message) {
		MediaType mediaType = MediaType.parse("application/json");
		UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
		formattedPhoneNumber = phoneNumber.replace("+", "");
		String[] recipients = {formattedPhoneNumber};
		content = new JSONObject()
			.put("userId", ducontUserId)
			.put("password", ducontPassword)
			.put("channelId", "")
			.put("senderId", senderId)
			.put("messageId", uuidAsString)
			.put("priority", "1")
			.put("recipients", recipients)
			.put("templateId", "")
			.put("templateVariables", "")
			.put("statusCallbackURL", "")
			.put("confirmDelivery", false)
			.put("validityPeriod", "7200")
			.put("body", message)
			.put("languageId", "EN").toString();
		RequestBody body = RequestBody.create(mediaType, content);
		Request request = new Request.Builder().url("http://www.ducont.ae/RESTAPISMS/api/Service/PushSMSSub").post(body)
				.addHeader("content-type", "application/json").build();

		Response response = client.newCall(request).execute();
	}
}
