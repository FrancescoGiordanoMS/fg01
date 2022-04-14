package francesco.giordano.fg01.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Objects;

public class Hardware {
private String matricola;
private String tipohw;
private String marca;
private String modello;
private LocalDate dataacquisto;
private float prezzoacquisto;
private Blob immagine;

public Hardware(String matricola, String tipohw, String marca, String modello, LocalDate dataacquisto,
		float prezzoacquisto, Blob immagine) {
	super();
	this.matricola = matricola;
	this.tipohw = tipohw;
	this.marca = marca;
	this.modello = modello;
	this.dataacquisto = dataacquisto;
	this.prezzoacquisto = prezzoacquisto;
	this.immagine = immagine;
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




public Blob getImmagine() {
	return immagine;
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




public void setImmagine(Blob immagine) {
	this.immagine = immagine;
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
