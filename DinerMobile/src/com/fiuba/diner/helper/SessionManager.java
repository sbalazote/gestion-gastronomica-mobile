package com.fiuba.diner.helper;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.fiuba.diner.activities.Login;

public class SessionManager {

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "AndroidHivePref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
		this.editor = this.pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String email) {
		// Storing login value as TRUE
		this.editor.putBoolean(IS_LOGIN, true);

		// Storing name in pref
		this.editor.putString(KEY_NAME, name);

		// Storing email in pref
		this.editor.putString(KEY_EMAIL, email);

		// commit changes
		this.editor.commit();
	}

	/**
	 * Check login method wil check user login status If false it will redirect user to login page Else won't do anything
	 * */
	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(this._context, Login.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			this._context.startActivity(i);
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, this.pref.getString(KEY_NAME, null));

		// user email id
		user.put(KEY_EMAIL, this.pref.getString(KEY_EMAIL, null));

		// return user
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		this.editor.clear();
		this.editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(this._context, Login.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		this._context.startActivity(i);
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return this.pref.getBoolean(IS_LOGIN, false);
	}
}