package com.fiuba.diner.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.model.LoginRequest;
import com.fiuba.diner.model.LoginResponse;
import com.fiuba.diner.model.Waiter;
import com.fiuba.diner.tasks.LoginTask;
import com.fiuba.diner.util.MacAddress;

public class Login extends Activity implements Caller<Boolean> {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;

	private SessionManager session;
	private String mobileId;
	private LoginResponse loginResponse;

	private ProgressDialog pdialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.app_name);
		this.setContentView(R.layout.login);

		this.session = new SessionManager(this.getApplicationContext());
		this.mobileId = MacAddress.get(this);

		this.usernameEditText = (EditText) this.findViewById(R.id.usernameEditText);
		this.passwordEditText = (EditText) this.findViewById(R.id.passwordEditText);
		this.loginButton = (Button) this.findViewById(R.id.loginButton);

		this.loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Login.this.attemptLogin();
			}
		});
	}

	@Override
	public void afterCall(Boolean result) {
		Boolean valid = false;

		this.pdialog.dismiss();

		if (this.loginResponse != null) {
			valid = this.loginResponse.getValid();
		}

		if (valid) {

			Login.this.session.createLoginSession(Login.this.usernameEditText.getText().toString(), Login.this.passwordEditText.getText().toString());

			// Se carga el waiter -----------------------------
			Waiter waiter = new Waiter();
			waiter.setId(this.loginResponse.getUserId());
			waiter.setName(this.loginResponse.getUserName());
			waiter.setActive(true);
			DataHolder.setCurrentWaiter(waiter);
			// -------------------------------------------------

			Intent intent = new Intent(Login.this.getApplicationContext(), HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Login.this.startActivity(intent);
			Login.this.finish();
			// this.setView();

		} else {
			// Toast.makeText(this.getApplicationContext(), "Ingreso incorrecto. Intente nuevamente.", Toast.LENGTH_LONG).show();
			Toast.makeText(this.getApplicationContext(), this.loginResponse.getMessage(), Toast.LENGTH_LONG).show();
			Login.this.usernameEditText.setText("");
			Login.this.passwordEditText.setText("");
			Login.this.usernameEditText.requestFocus();
		}
	}

	private void attemptLogin() {
		Boolean cancel = false;
		String userName = Login.this.usernameEditText.getText().toString();
		String userPassword = Login.this.passwordEditText.getText().toString();
		View focusView = null;

		// Se validan los campos obligatorios
		if (TextUtils.isEmpty(userPassword)) {
			Login.this.passwordEditText.setError("El campo contraseña es obligatorio");
			focusView = Login.this.passwordEditText;
			cancel = true;
		}
		if (TextUtils.isEmpty(userName)) {
			Login.this.usernameEditText.setError("El campo usuario es obligatorio");
			focusView = Login.this.usernameEditText;
			cancel = true;
		}

		if (!cancel) {
			LoginRequest userLogin = new LoginRequest();
			userLogin.setUserName(userName);
			userLogin.setUserPassword(userPassword);
			userLogin.setMobileId(this.mobileId);

			this.pdialog = new ProgressDialog(this);
			this.pdialog.setCancelable(false);
			this.pdialog.setMessage("Autenticando usuario ....");
			this.pdialog.show();

			new LoginTask(Login.this).execute(userLogin);

		} else {
			// Se devuelve el foco al campo que no fue completado
			focusView.requestFocus();
		}
	}

	// private void setView() {
	// this.setContentView(R.layout.home);
	// }

	public void setLoginResponse(LoginResponse loginResponse) {
		this.loginResponse = loginResponse;
	}

}