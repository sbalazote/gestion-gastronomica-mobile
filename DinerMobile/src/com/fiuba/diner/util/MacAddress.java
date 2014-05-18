package com.fiuba.diner.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class MacAddress {

	public static final String get(Context context) {
		String macAddress = com.fiuba.diner.constant.Constants.MAC;

		try {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			if (wifiManager != null) {
				if (wifiManager.isWifiEnabled()) {
					// WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
					WifiInfo info = wifiManager.getConnectionInfo();
					macAddress = info.getMacAddress();

				} else {
					// ENABLE THE WIFI FIRST
					wifiManager.setWifiEnabled(true);

					// WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
					WifiInfo info = wifiManager.getConnectionInfo();
					macAddress = info.getMacAddress();

					// NOW DISABLE IT AGAIN
					wifiManager.setWifiEnabled(false);
				}

				// formatea el valor de la MAC
				if (macAddress != null) {
					macAddress = macAddress.replace(":", "");
				} else {
					macAddress = com.fiuba.diner.constant.Constants.MAC;
				}
			} else {
				// msg = "WiFi no encontrada: No se puede obtener la MacAddress";
			}
		} catch (Exception e) {
			// msg = "Error al obtener la MacAddress: " + e.getMessage();
		}

		return macAddress;
	}
}
