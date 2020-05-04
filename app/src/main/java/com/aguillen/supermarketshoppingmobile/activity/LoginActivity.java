package com.aguillen.supermarketshoppingmobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aguillen.supermarketshoppingmobile.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUser;
    private EditText etPass;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.et_user);
        etPass = (EditText) findViewById(R.id.et_pass);
        btLogin = (Button) findViewById(R.id.bt_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUser(etUser.getText().toString(), etPass.getText().toString())) {
                    Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                    finish();
                    startActivity(i);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error");
                    builder.setMessage("El usuario o contraseña son incorrectos.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
                    return;
                }
            }
        });
    }

    private boolean validateUser(String user, String pass) {
        try {
            if (user.equals("abel") && pass.equals("abel")) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
