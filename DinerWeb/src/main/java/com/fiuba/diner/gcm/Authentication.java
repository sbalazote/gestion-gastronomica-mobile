package com.fiuba.diner.gcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Authentication {

	private static final String PARAM_EMAIL = "example@gmail.com";
	private static final String PARAM_PASSWORD = "666";

	public static String getToken() throws IOException {
		// create the post data
		// Requires a field with the email and the password
		StringBuilder builder = new StringBuilder();
		builder.append("Email=").append(PARAM_EMAIL);
		builder.append("&Passwd=").append(PARAM_PASSWORD);
		builder.append("&accountType=GOOGLE");
		builder.append("&source=Diner");
		builder.append("&service=ac2dm");

		// Setup the Http Post
		byte[] data = builder.toString().getBytes();
		URL url = new URL("https://www.google.com/accounts/ClientLogin");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", Integer.toString(data.length));

		// Issue the HTTP POST request
		OutputStream output = con.getOutputStream();
		output.write(data);
		output.close();

		// read the response
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		String auth_key = null;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("Auth=")) {
				auth_key = line.substring(5);
			}
		}

		// Finally get the authentication token
		// To something useful with it
		return auth_key;
	}
}
