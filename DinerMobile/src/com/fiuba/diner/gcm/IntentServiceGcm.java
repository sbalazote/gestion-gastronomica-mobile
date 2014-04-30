package com.fiuba.diner.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class IntentServiceGcm extends IntentService {

	public IntentServiceGcm() {
		super("IntentServiceGcm");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				// int msg_id = Integer.parseInt(extras.getString("message_id"));
				// llama al metodo que envia al status bar la notificacion
				// Utils.sendNotification(this, Utils.REQUEST_CODE_GCM, msg_id, com.flowpro.courier.R.drawable.ic_launcher, "Notificacion de Flowpro Courier",
				// extras.getString("message"));
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		BroadcastReceiverGcm.completeWakefulIntent(intent);
	}
}
