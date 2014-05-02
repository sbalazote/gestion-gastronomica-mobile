package com.fiuba.diner.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

public class NotificationOnBar {

	public static void sendNotification(Context context, int requestCode, int msgid, int icon, String title, String msg) {
		try {
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			PendingIntent contentIntent = PendingIntent.getActivity(context, requestCode, new Intent(context, NotificationReceptor.class),
					PendingIntent.FLAG_ONE_SHOT);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
			mBuilder.setSmallIcon(icon);
			mBuilder.setContentTitle(title);
			mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
			mBuilder.setContentText(msg);
			mBuilder.setContentIntent(contentIntent);
			mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

			mNotificationManager.notify(msgid, mBuilder.build());

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
