package com.fiuba.diner.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class SharedPreferencesHelper {

	private static final String PREFERENCES_FILE_NAME = "appData";
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
	private static final String PROPERTY_USER = "user";

	private static SharedPreferences preferences;

	public static final void setValue(Context context, String attribute, String value) {
		// MODE_PRIVATE solo la aplicación puede acceder al archivo de preferencias.
		// MODE_WORLD_READABLE otras aplicaciones pueden consultar el archivo de preferencias
		// MODE_WORLD_WRITEABLE otras aplicaciones pueden consultar y modificar el archivo.
		// MODE_MULTI_PROCESS varios procesos pueden acceder (Requiere Android 2.3)
		SharedPreferencesHelper.preferences = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
		SharedPreferencesHelper.preferences.edit().putString(attribute, value);
		SharedPreferencesHelper.preferences.edit().commit();
		// editor.putInt("edad",3);
		// editor.putBoolean("activo", true);
		// editor.putFloat("altura", 2.3f);
	}

	public static final String getValue(Context context, String attribute) {
		SharedPreferencesHelper.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		return SharedPreferencesHelper.preferences.getString(attribute, ""); // defValue: valor que retorna si no encuentra el att buscado
		// int e=prefe.getInt("edad", 0);
		// boolean acti=prefe.getBoolean("activo", false);
		// float alt=prefe.getFloat("altura", 0f);
	}

	public static void setRegistrationId(Context context, String user, String regId) {
		SharedPreferencesHelper.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferencesHelper.preferences.edit().putString(PROPERTY_USER, user);
		SharedPreferencesHelper.preferences.edit().putString(PROPERTY_REG_ID, regId);
		SharedPreferencesHelper.preferences.edit().putInt(PROPERTY_APP_VERSION, getAppVersion(context));
		SharedPreferencesHelper.preferences.edit().commit();
	}

	public static String getRegistrationId(Context context) {
		SharedPreferencesHelper.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		String registrationId = SharedPreferencesHelper.preferences.getString(PROPERTY_REG_ID, "");

		if (registrationId.length() == 0) {
			return "";
		}

		// String registeredUser = SharedPreferencesHelper.preferences.getString(PROPERTY_USER, "");
		int registeredVersion = SharedPreferencesHelper.preferences.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		long expirationTime = SharedPreferencesHelper.preferences.getLong(PROPERTY_EXPIRATION_TIME, -1);

		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
		// String expirationDate = sdf.format(new Date(expirationTime));

		// "Registro GCM encontrado (usuario=" + registeredUser + ", version=" + registeredVersion + ", expira=" + expirationDate

		int currentVersion = getAppVersion(context);

		if (registeredVersion != currentVersion) {
			// "Nueva versión de la aplicación."
			return "";
		} else if (System.currentTimeMillis() > expirationTime) {
			// "Registro GCM expirado."
			return "";
			// } else if (!txtUsuario.getText().toString().equals(registeredUser)) {
			// "Nuevo nombre de usuario."
			// return "";
		}

		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Error al obtener versión: " + e);
		}
	}
}
