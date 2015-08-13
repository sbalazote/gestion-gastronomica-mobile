package com.fiuba.diner.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.fiuba.diner.util.NotificationOnBar;
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
				int msg_id = Integer.parseInt(extras.getString("message_id"));
				NotificationOnBar.sendNotification(this, 1, msg_id, com.fiuba.diner.R.drawable.ic_launcher, "Diner", extras.getString("message"));
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		BroadcastReceiverGcm.completeWakefulIntent(intent);
	}
}
