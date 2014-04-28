package com.fiuba.diner.gcm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class Notification {

	private static final String PARAM_REGISTRATION_ID = "registration_id";
	private static final String PARAM_COLLAPSE_KEY = "collapse_key";
	private static final String PARAM_MESSAGE = "data.message";
	private static final String UTF8 = "UTF-8";
	private static final String GOOGLE_URL_SENDING_MESSAGE = "https://android.clients.google.com/c2dm/send";
	private static final String API_KEY = "AIzaSyCQc2ty0CCAr7nwVyCMg5G28Gd68vRiONQ";

	// private static final String AUTH = "authentication";
	// private static final String UPDATE_CLIENT_AUTH = "Update-Client-Auth";
	// private static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";

	public static int send(String registrationId, String message) throws IOException {
		// Carga de parametros que se envian al GCM
		StringBuilder postDataBuilder = new StringBuilder();
		postDataBuilder.append(PARAM_REGISTRATION_ID).append("=").append(registrationId);
		postDataBuilder.append("&").append(PARAM_COLLAPSE_KEY).append("=").append("0");
		postDataBuilder.append("&").append(PARAM_MESSAGE).append("=").append(URLEncoder.encode(message, UTF8));

		byte[] postData = postDataBuilder.toString().getBytes(UTF8);

		URL url = new URL(GOOGLE_URL_SENDING_MESSAGE);
		HttpsURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		conn.setRequestProperty("Authorization", "GoogleLogin auth=" + API_KEY);

		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		int responseCode = conn.getResponseCode();
		return responseCode;

		// 200. El mensaje se ha procesado correctamente.
		// 401. Ha fallado la autenticación de la aplicación web contra los servidores de GCM. Normalmente algún problema con la API Key utilizada.
		// 500. Se ha producido un error al procesarse el mensaje.
		// En este caso la respuesta incluirá en su contenido un parámetro “Error=” que indicará el código de error concreto devuelto por GCM.
		// 501. El servidor de GCM no está disponible temporalmente.
	}

	private static class CustomizedHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
