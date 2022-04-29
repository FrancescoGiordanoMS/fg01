package fglib;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MyMenuBar  extends MenuBar {

	private MenuBar menuBar;
	private MenuItem MenuItem_Inserisci;
	private MenuItem MenuItem_Modifica;
	private MenuItem MenuItem_Delete;
	private MenuItem MenuItem_Close,MenuItem_Refresh;

	public MyMenuBar() {
		menuBar = new MenuBar();
		Menu file = new Menu("File");
		//Creating file menu items
		MenuItem_Refresh = new MenuItem("Refresh");
		SeparatorMenuItem sm = new SeparatorMenuItem();
		MenuItem_Close = new MenuItem("Chiudi");
		file.getItems().addAll(MenuItem_Refresh, sm, MenuItem_Close);
		
		Menu Edit = new Menu("Edit");
		MenuItem_Inserisci = new MenuItem("Inserisci");
		MenuItem_Modifica = new MenuItem("Modifica");
		MenuItem_Delete = new MenuItem("Elimina");
		Edit.getItems().addAll(MenuItem_Inserisci,MenuItem_Modifica,MenuItem_Delete);
		menuBar.getMenus().addAll(file, Edit);	
	}
	
	
	public MenuItem getMenuItem_Close() {
		return MenuItem_Close;
	}
	public void setMenuItem_Close(MenuItem menuItem_Close) {
		MenuItem_Close = menuItem_Close;
	}
	public MenuItem getMenuItem_Modifica() {
		return MenuItem_Modifica;
	}
	public void setMenuItem_Modifica(MenuItem menuItem_Modifica) {
		MenuItem_Modifica = menuItem_Modifica;
	}
	public MenuItem getMenuItem_Delete() {
		return MenuItem_Delete;
	}
	public void setMenuItem_Delete(MenuItem menuItem_Delete) {
		MenuItem_Delete = menuItem_Delete;
	}
	public MenuBar getMenuBar() {
		return menuBar;
	}
	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
	public MenuItem getMenuItem_Inserisci() {
		return MenuItem_Inserisci;
	}
	public void setMenuItem_Inserisci(MenuItem menuItem_Inserisci) {
		MenuItem_Inserisci = menuItem_Inserisci;
	}


	public MenuItem getMenuItem_Refresh() {
		return MenuItem_Refresh;
	}
	public void setMenuItem_Refresh(MenuItem menuItem_Refresh) {
		MenuItem_Refresh = menuItem_Refresh;
	}
	
}
