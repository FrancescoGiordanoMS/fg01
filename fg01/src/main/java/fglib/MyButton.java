package fglib;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MyButton {

	private AnchorPane anchorpane;
	private Button btnSave;
	private Button btnCancel;

	public MyButton() {
		anchorpane = new AnchorPane();

		btnSave = new Button();
		btnCancel = new Button();
		
		btnSave.setText("Salva");
		btnSave.setMaxHeight(41);
		btnSave.setDisable(true);
		
		btnCancel.setText("Annulla");
		btnCancel.setMaxHeight(52);
		btnCancel.setDisable(true);

		anchorpane.setRightAnchor(btnCancel, 10d);
		anchorpane.setBottomAnchor(btnCancel, 5d);

		anchorpane.setRightAnchor(btnSave, 85d);
		anchorpane.setBottomAnchor(btnSave, 5d);
		anchorpane.getChildren().addAll(btnSave, btnCancel);

	}

	public AnchorPane getAnchorpane() {
		return anchorpane;
	}

	public void setAnchorpane(AnchorPane anchorpane) {
		this.anchorpane = anchorpane;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}


	public void setVisible(boolean v) {
		this.anchorpane.setVisible(v);
	}
}
