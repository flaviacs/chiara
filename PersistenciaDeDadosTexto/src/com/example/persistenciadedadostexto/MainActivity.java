package com.example.persistenciadedadostexto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
// memoria interna = memória da aplicação
// memória externa privada = cartão com "senha", sem acesso
// memória externa pública = armazenado no cartão



public class MainActivity extends Activity
implements View.OnClickListener {


	EditText edtTexto;
	TextView txtTexto;
	RadioGroup rgTipo;
	Button btnSalvar, btnLer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edtTexto = (EditText)findViewById(R.id.edtText);
		txtTexto = (TextView)findViewById(R.id.txtTexto);
		btnSalvar = (Button)findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(this);
		btnLer  = (Button)findViewById(R.id.btnLer);
		btnLer.setOnClickListener(this);
		rgTipo = (RadioGroup)findViewById(R.id.rgTipoGravacao);
	}

	@Override
	public void onClick(View view) {
		boolean ler = false;
		if (view.getId() == R.id.btnLer) {
			ler = true;
		}
		int tipo = rgTipo.getCheckedRadioButtonId();
		if (ler) {
			switch (tipo) {
			case R.id.rbMemoInterna:
				carregarInterno();
				break;
			case R.id.rbMemoExternaPrivado:
				carregarDoSdCard(true);
				break;
			case R.id.rbMemoExternaPublica:
				carregarDoSdCard(false);
				break;
			}
		} else {
			switch (tipo) {
			case R.id.rbMemoInterna:
				salvarInterno();
				break;
			case R.id.rbMemoExternaPrivado:
				salvarNoSdCard(true);
				break;
			case R.id.rbMemoExternaPublica:
				salvarNoSdCard(false);
				break;
			}
		}
	}

	private void salvarInterno() {
		try {
			FileOutputStream fos = openFileOutput("arquivo.txt", Context.MODE_PRIVATE);
			salvar(fos);
		} catch (IOException e) {
			Toast.makeText(this, "Erro ao salvar arquivo: "+e, Toast.LENGTH_SHORT).show();
		}
	}

	private void carregarInterno() {
		try {
			FileInputStream fis = openFileInput("arquivo.txt");
			carregar(fis);
		} catch (IOException e) {
			Toast.makeText(this, "Erro ao carregar arquivo: "+e, Toast.LENGTH_SHORT).show();
		}
	}

	private void salvarNoSdCard(boolean privado) {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File meuDir = getExternalDir(privado);
			try {
				if (!meuDir.exists()) {
					meuDir.mkdir();
				}
				File arquivoTxt = new File(meuDir, "arquivo.txt");
				if (!arquivoTxt.exists()) {
					arquivoTxt.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(arquivoTxt);
				salvar(fos);
			} catch (IOException e) {
				Toast.makeText(this, "Erro ao salvar arquivo: "+e, Toast.LENGTH_SHORT).show();
				
			}
		} else {
			Toast.makeText(this, "Não é possível escrever no SD Card ", Toast.LENGTH_SHORT).show();
		}
	}

	private void carregarDoSdCard(boolean privado) {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

			File meuDir = getExternalDir(privado);
			if (meuDir.exists()) {
				File arquivoTxt = new File(meuDir, "arquivo.txt");
				if (arquivoTxt.exists()) {
					try {
						arquivoTxt.createNewFile();
						FileInputStream fis = new FileInputStream(arquivoTxt);
						carregar(fis);
					} catch (IOException e) {
						Toast.makeText(this, "Erro ao carregar arquivo: "+e, Toast.LENGTH_SHORT).show();
					}
				}
			}
		} else {
			Toast.makeText(this, "SD Card indisponivel: ", Toast.LENGTH_SHORT).show();
		}
	}

	private File getExternalDir(boolean privado) {
		if (privado) {
			// SDCard/Android/data/pacote.da.app/files
			return getExternalFilesDir(null);

		} else {
			// SDCard/DCIM
			return Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DCIM);
		}
	}

	private void salvar(FileOutputStream fos) throws IOException {
		String[] linhas = TextUtils.split(
				edtTexto.getText().toString(), "\n");

		PrintWriter writter = new PrintWriter(fos);
		for (String linha : linhas) {
			writter.println(linha);
		}
		writter.flush();
		writter.close();
		fos.close();
		Toast.makeText(this, "ok ", Toast.LENGTH_SHORT).show();
	}

	private void carregar(FileInputStream fis) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(fis));
		StringBuilder sb = new StringBuilder();
		String linha;
		while ((linha = reader.readLine()) != null) {
			if (sb.length() != 0) sb.append('\n');
			sb.append(linha);
		}
		reader.close();
		fis.close();

		txtTexto.setText(sb.toString());
	}

}
/*
public class MainActivity extends Activity 
implements OnClickListener{


	EditText edtTexto;
	TextView txtTexto;
	RadioGroup rgTipo;
	Button btnSalvar, btnLer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edtTexto = (EditText)findViewById(R.id.edtText);
		txtTexto = (TextView)findViewById(R.id.txtTexto);
		btnSalvar = (Button)findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(this);
		btnLer  = (Button)findViewById(R.id.btnLer);
		btnLer.setOnClickListener(this);
		rgTipo = (RadioGroup)findViewById(R.id.rgTipoGravacao);
	}

	@Override
	public void onClick(View v){
		boolean ler = false;
		if(v.getId() ==  R.id.btnLer){
			ler = true;
		}
		int tipo = rgTipo.getCheckedRadioButtonId();
		if(ler){
			switch(tipo){
			case R.id.rbMemoInterna:
				carregarInterno();
				break;
			case R.id.rbMemoExternaPrivado:
				carregarDoSDCard(true);
				break;
			case R.id.rbMemoExternaPublica:
				carregarDoSDCard(false);
				break;
			}
		}
		else {
			switch(tipo){
			case R.id.rbMemoInterna:
				salvarInterno();
				break;
			case R.id.rbMemoExternaPrivado:
				salvarNoSDCard(true);
				break;
			case R.id.rbMemoExternaPublica:
				salvarNoSDCard(false);
				break;
			}
		}
	}
	public void salvarInterno(){
		try{
			FileOutputStream fos = openFileOutput("arquivo.txt", Context.MODE_PRIVATE);
			salvar(fos);
		}catch(IOException e){
			Toast.makeText(this, "erro ao salvar o arquivo", Toast.LENGTH_SHORT).show();
		}
	}
	public void carregarInterno(){
		try {
			FileInputStream fis = openFileInput("arquivo.txt");
			carregar(fis);
		} catch (IOException e) {
			Toast.makeText(this, "erro ao carregar o aquivo", Toast.LENGTH_SHORT).show();
		}

	}
	public void salvarNoSDCard(boolean privado){
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File meuDir = getExternalDir(privado);
			try {
				if (!meuDir.exists()) {
					meuDir.mkdir();
				}
				File arquivoTxt = new File(meuDir, "arquivo.txt");
				if (!arquivoTxt.exists()) {
					arquivoTxt.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(arquivoTxt);
				salvar(fos);
			} catch (IOException e) {
				Toast.makeText(this, "erro ao salvar o arquivo", Toast.LENGTH_SHORT).show();

			}
		} else {
			Toast.makeText(this, "Não é possível escrever no SD Card", Toast.LENGTH_SHORT).show();
		}

	}
	public void carregarDoSDCard(boolean privado){

	}

	private void salvar(FileOutputStream fos) throws IOException{
		String [] linhas = TextUtils.split(
				edtTexto.getText().toString(), "\n");
		PrintWriter writter = new PrintWriter(fos);
		for(String linha:linhas){
			writter.println(linha);
		}
		writter.flush();
		writter.close();
		fos.close();
	}

	private void carregar(FileInputStream fis) throws IOException{
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(fis));

		StringBuilder sb = new StringBuilder();
		String linha;
		while((linha = reader.readLine()) != null){
			if (sb.length() != 0)
				sb.append('\n');
				sb.append(linha);
		}
		reader.close();
		fis.close();
		Toast.makeText(this, "leitura", Toast.LENGTH_SHORT).show();
		txtTexto.setText(sb.toString());
	}
	private File getExternalDir(boolean privado){
		if(privado){
			// SDCard/Android/data/pacote.da.app/files
			return getExternalFilesDir(null);
		} else {
			// SDCard/DCIM
			return Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DCIM);
		}	


	}
}
 */