package com.example.persistenciadedadosspref;
//

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	EditText edtEmail, edtPontos;
	SharedPreferences prefs;
	String 	email;
	int pontos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edtEmail = (EditText)findViewById(R.id.edtEmail);
		edtPontos = (EditText)findViewById(R.id.edtPontos);
	}
	public void salvarClick(View v){
		prefs 	= getSharedPreferences(	"configuracao",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		email = edtEmail.getText().toString();
		pontos = Integer.parseInt(edtPontos.getText().toString());
		editor.putString("email", email);
		editor.putInt("pontos", pontos);
		Toast.makeText(this, "Configuração salva"+"\n"+"com sucesso", Toast.LENGTH_SHORT).show();
		edtEmail.setText("");
		edtPontos.setText("");
		editor.commit();
	}
	public void lerClick(View v){
		email = prefs.getString("email", null);
		pontos = prefs.getInt("pontos",0);
		Toast.makeText(this, "email:"+email.toString() +"\npontos:"+pontos, Toast.LENGTH_SHORT).show();
	}
}
