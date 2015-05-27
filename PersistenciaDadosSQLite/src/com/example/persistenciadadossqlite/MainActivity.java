package com.example.persistenciadadossqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) {
		Intent it;
		switch (item.getItemId()) {
		case R.id.novoLivro:
			//Toast.makeText(this,"novo livro",Toast.LENGHT_SHORT).show();
			it = new Intent(this,CadLivro.class);
			startActivity(it);
			break;
		case R.id.listarLivros:
			//Toast.makeText(this,"Listar livros",Toast.LENGHT_SHORT).show();
			it = new Intent(this,ListarLivros.class);
			startActivity(it);
			break;	
		
		}
		return true;
	}
}
