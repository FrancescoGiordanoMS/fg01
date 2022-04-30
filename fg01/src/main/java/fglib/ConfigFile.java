package fglib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author francesco
 *
 */
public class ConfigFile {
	private static String configfile="config.txt";
	
	private static String database_ip = null;
	private static String database_nome = null;
	private static String database_user = null;
	private static String database_password = null;
	private static String connString = null;

	public static void read(String file) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line; // = reader.readLine();
			while ((line= reader.readLine())!=null) {
				int index = line.indexOf('=');
				if (line.substring(0, 12).equals("#Database-IP")) 
					database_ip=line.substring(index+1);
				if (line.substring(0, 14).equals("#Database-Name"))
					database_nome=line.substring(index+1);					
				if (line.substring(0, 14).equals("#Database-User"))
					database_user=line.substring(index+1);									
				if (line.substring(0, 18).equals("#Database-Password"))
					database_password=line.substring(index+1);					
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("File not found");
			alert.setHeaderText("config.txt (Impossibile trovare il file specificato)");
			alert.setContentText("Il file config.txt deve trovarsi nella cartella dell'eseguibile");
			alert.showAndWait();	
			Runtime.getRuntime().exit(0); 
		}
	}

	/***************************************************************************************
	 * Legge il file di configurazione config.txt e genera la stringa di connessione
	 * al db MySql componendo i parametri contenuti all'interno
	 * @return la stringa di connessione al db MySql
	 */
	public static String getMySqlConnString() {
		read(configfile);
		connString = "jdbc:mysql://"+ConfigFile.getDatabase_ip()+":3306/"+
				ConfigFile.getDatabase_nome()+
				"?user="+ConfigFile.getDatabase_user()+
				"&password="+ConfigFile.getDatabase_password()+
				"&useSSL=false&allowPublicKeyRetrieval=true";
		System.out.println("Lettura stringa connection da config.txt (classe ConfigFile)");
		return connString;		
	}
	
	public static String getDatabase_ip() {
		return database_ip;
	}
	public void setDatabase_ip(String database_ip) {
		this.database_ip = database_ip;
	}
	public static String getDatabase_nome() {
		return database_nome;
	}
	public void setDatabase_nome(String database_nome) {
		this.database_nome = database_nome;
	}
	public static String getDatabase_user() {
		return database_user;
	}
	public void setDatabase_user(String database_user) {
		this.database_user = database_user;
	}
	public static String getDatabase_password() {
		return database_password;
	}
	public void setDatabase_password(String database_password) {
		this.database_password = database_password;
	}
	
}
