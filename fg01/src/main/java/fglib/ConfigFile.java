package fglib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConfigFile {
	private String file=null;
	private String database_ip = null;
	private String database_nome = null;
	private String database_user = null;
	private String database_password = null;

	public ConfigFile(String txtfile) {
		this.file = txtfile;
	}

	public void read() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line; // = reader.readLine();
			while ((line= reader.readLine())!=null) {
				int index = line.indexOf('=');
				if (line.substring(0, 12).equals("#Database-IP")) {
					this.database_ip=line.substring(index+1);
				}
				if (line.substring(0, 14).equals("#Database-Name")) {
					this.database_nome=line.substring(index+1);					
				}
				if (line.substring(0, 14).equals("#Database-User"))
					this.database_user=line.substring(index+1);									
				if (line.substring(0, 18).equals("#Database-Password"))
					this.database_password=line.substring(index+1);					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("File not found");
			alert.setHeaderText("config.txt (Impossibile trovare il file specificato)");
			alert.setContentText("Il file config.txt contiene i dati di configurazione e deve trovarsi nella cartella dell'eseguibile");
			alert.showAndWait();	
			Runtime.getRuntime().exit(0); 
		}
	}

	public String getDatabase_ip() {
		return database_ip;
	}

	public void setDatabase_ip(String database_ip) {
		this.database_ip = database_ip;
	}

	public String getDatabase_nome() {
		return database_nome;
	}

	public void setDatabase_nome(String database_nome) {
		this.database_nome = database_nome;
	}

	public String getDatabase_user() {
		return database_user;
	}

	public void setDatabase_user(String database_user) {
		this.database_user = database_user;
	}

	public String getDatabase_password() {
		return database_password;
	}

	public void setDatabase_password(String database_password) {
		this.database_password = database_password;
	}

	public String getFile() {
		return file;
	}


	public void setFile(String file) {
		this.file = file;
	}


}
