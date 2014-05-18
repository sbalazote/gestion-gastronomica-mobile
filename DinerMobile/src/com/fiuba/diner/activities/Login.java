package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.model.User;
import com.fiuba.diner.tasks.LoginTask;

public class Login extends Activity implements Caller<Boolean> {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.app_name);
		this.setContentView(R.layout.login);

		this.usernameEditText = (EditText) this.findViewById(R.id.usernameEditText);
		this.passwordEditText = (EditText) this.findViewById(R.id.passwordEditText);
		this.loginButton = (Button) this.findViewById(R.id.loginButton);

		this.loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				String username = Login.this.usernameEditText.getText().toString();
				String password = Login.this.passwordEditText.getText().toString();

				User user = new User();
				user.setName(username);
				user.setPassword(password);

				new LoginTask(Login.this).execute(user);

			}
		});
	}

	@Override
	public void afterCall(Boolean result) {
		if (result) {
			Intent intent = new Intent(Login.this.getApplicationContext(), HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Login.this.startActivity(intent);
			Login.this.finish();
			this.setView();
		} else {
			Toast.makeText(this.getApplicationContext(), "Ingreso incorrecto. Intente nuevamente.", Toast.LENGTH_LONG).show();
			Login.this.usernameEditText.setText("");
			Login.this.passwordEditText.setText("");
			Login.this.usernameEditText.requestFocus();
		}
	}

	private void setView() {
		this.setContentView(R.layout.home);
	}
}