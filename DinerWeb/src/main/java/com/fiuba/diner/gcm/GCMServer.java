package com.fiuba.diner.gcm;

//import java.util.ArrayList;

import java.io.IOException;

import com.google.android.gcm.server.Message;
//import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMServer {

	private static final String REGISTRATION_ID = "APA91bFPYekAloaJiYqHGJbQIcNE7oSu6V1XHA0-iWl6haN1wjEqg-aK861RxVTepMd_oljocE_25_lSPDYQ36lVSEbILiAjgOkmAjB9LkIWAtBdVlcxi-k5EUw1PrGiHyTWKrqdB6G9JGSoDc2JaSnVqIQXV2HR_A";
	private static final String SENDER_ID = "AIzaSyBsNjZXoDVfiv4AjiYz-JiCoTv2szqEs-4";

	static public void sendNotification(String messageToSend) throws IOException {

		Sender sender = new Sender(SENDER_ID);// add your own google APIkey here

		// use this to send message with payload data
		Message message = new Message.Builder().collapseKey("message").timeToLive(3).delayWhileIdle(true).addData("message", messageToSend)
				.addData("message_id", "1").build();

		// Use this code to send notification message to a single device
		Result result = sender.send(message, REGISTRATION_ID, 1);

		System.out.println("Message Result: " + result.toString()); // Print message result on console

		// Use this code to send notification message to multiple devices
		// ArrayList<String> devicesList = new ArrayList<String>();

		// add your devices RegisterationID, one for each device
		// devicesList.add("APA91bEC0ZwWeWgtaWIIarY8M_1lVN1GGyQuwwWikkuTmtRyibUHuAsiMb4ctfHyQZlkM018hI-BgbPGeZbbbQmDfLScPUdEZp2pEwJPh9LNDS-knetjQHCkgXFNKY5hOKAcRdSdPOR2");
		// devicesList.add("APA91bE2w5kK_LTmbm0vUL9VvaXfT5mqdIo9a719K_U18M1bbK2cTbbnQVhMsogxczRpoPEjeyExCkyPI19L1bJz2fBln-k_5yJA3T9-XRBceMyjai9cPYbEKVwBRbEuurpR0ki1LJfP");

		// Use this code for multicast messages
		// MulticastResult multicastResult = sender.send(message, devicesList, 0);
		// sender.send(message, devicesList, 0);
		// System.out.println("Message Result: "+multicastResult.toString());//Print multicast message result on console
	}
}
