package com.fiuba.diner.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.tasks.SetUpTask;

public class Login extends Activity implements Caller<Void> {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.app_name);
		this.setContentView(R.layout.login);
		new SetUpTask(this).execute();

		this.usernameEditText = (EditText) this.findViewById(R.id.usernameEditText);
		this.passwordEditText = (EditText) this.findViewById(R.id.passwordEditText);
		this.loginButton = (Button) this.findViewById(R.id.loginButton);

		this.loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				String username = Login.this.usernameEditText.getText().toString();
				String password = Login.this.passwordEditText.getText().toString();

				if ((username.compareTo("Pi") == 0) && (password.compareTo("Sosa") == 0)) {
					Intent intent = new Intent(Login.this, HomeActivity.class);
					Login.this.startActivity(intent);
				} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
					alertDialogBuilder.setMessage("Ingreso incorrecto. Intente nuevamente.");
					alertDialogBuilder.setNeutralButton("Aceptar", null);
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			}
		});
	}

	@Override
	public void afterCall(Void result) {
		this.setView();
	}

	private void setView() {
		this.setContentView(R.layout.home);
	}
}