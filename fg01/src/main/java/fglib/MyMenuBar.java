package fglib;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenuBar  extends MenuBar {

	private MenuBar menuBar;
	private MenuItem MenuItem_Inserisci;

	
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
	
	public MyMenuBar() {
		menuBar = new MenuBar();
		Menu file = new Menu("File");
		//Creating file menu items
		MenuItem item1 = new MenuItem("Chiudi");
		file.getItems().addAll(item1);
		
		Menu Edit = new Menu("Edit");
		MenuItem_Inserisci = new MenuItem("Inserisci");
		MenuItem itemEdit2 = new MenuItem("Modifica");
		MenuItem itemEdit3 = new MenuItem("Elimina");
		Edit.getItems().addAll(MenuItem_Inserisci,itemEdit2,itemEdit3);
		menuBar.getMenus().addAll(file, Edit);	
	}
	
	
	

//	public MenuBar StandardMenu() {
//		MenuBar mb = new MenuBar();
//		Menu file = new Menu("File");
//		//Creating file menu items
//		MenuItem item1 = new MenuItem("Chiudi");
//		file.getItems().addAll(item1);
//		
//		Menu Edit = new Menu("Edit");
//		MenuItem MenuItem_Inserisci = new MenuItem("Inserisci");
//		MenuItem itemEdit2 = new MenuItem("Modifica");
//		MenuItem itemEdit3 = new MenuItem("Elimina");
//		Edit.getItems().addAll(MenuItem_Inserisci,itemEdit2,itemEdit3);
//		mb.getMenus().addAll(file, Edit);	
//
////		MenuItem_Inserisci.setOnAction(e -> {
////			//MenuInserisci();
////		    System.out.println("Menu Item 1 Selected");
////            }
////        ); 
//		return mb;
//	}

}
