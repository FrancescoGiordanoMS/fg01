package francesco.giordano.fg01.model;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Hardware {
	private String matricola;
	private String tipohw;
	private String marca;
	private String modello;
	private LocalDate dataacquisto;
	private float prezzoacquisto;
	private Blob immagine;
	private Image image;


	//public Hardware(String matricola, String tipohw, String marca, String modello, LocalDate dataacquisto,
	//		float prezzoacquisto, Blob immagine) {
	//	super();
	//	this.matricola = matricola;
	//	this.tipohw = tipohw;
	//	this.marca = marca;
	//	this.modello = modello;
	//	this.dataacquisto = dataacquisto;
	//	this.prezzoacquisto = prezzoacquisto;
	//	this.immagine = immagine;
	//}

	public Hardware() {
		super();
	}

	public String getMatricola() {
		return matricola;
	}

	public String getTipohw() {
		return tipohw;
	}




	public String getMarca() {
		return marca;
	}




	public String getModello() {
		return modello;
	}




	public LocalDate getDataacquisto() {
		return dataacquisto;
	}




	public float getPrezzoacquisto() {
		return prezzoacquisto;
	}








	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}




	public void setTipohw(String tipohw) {
		this.tipohw = tipohw;
	}




	public void setMarca(String marca) {
		this.marca = marca;
	}




	public void setModello(String modello) {
		this.modello = modello;
	}




	public void setDataacquisto(LocalDate dataacquisto) {
		this.dataacquisto = dataacquisto;
	}




	public void setPrezzoacquisto(float prezzoacquisto) {
		this.prezzoacquisto = prezzoacquisto;
	}


	public Blob getImmagine() {
		return immagine;
	}

	public void setImmagine(Blob immagine) {
		this.immagine = immagine;
		// qui sotto carico anche il campo image
		if (immagine == null) {
			image = null;
		} 
		else {
			InputStream is;
			try {
				is = new BufferedInputStream(immagine.getBinaryStream());
				BufferedImage bi = ImageIO.read(is);
				if (bi != null) image = SwingFXUtils.toFXImage(bi,null);
				is.close();
			}
			catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image im) {
		image = im;
		if (image != null) {
			// write data to in-memory stream
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedImage bi = SwingFXUtils.fromFXImage(image, null);
			try {
				ImageIO.write(bi, "jpg", bos);
				this.immagine = new SerialBlob(bos.toByteArray());
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	@Override
	public int hashCode() {
		return Objects.hash(dataacquisto, marca, matricola, modello, prezzoacquisto, tipohw);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hardware other = (Hardware) obj;
		return Objects.equals(dataacquisto, other.dataacquisto) && Objects.equals(marca, other.marca)
				&& Objects.equals(matricola, other.matricola) && Objects.equals(modello, other.modello)
				&& Float.floatToIntBits(prezzoacquisto) == Float.floatToIntBits(other.prezzoacquisto)
				&& Objects.equals(tipohw, other.tipohw);
	}



}
