package francesco.giordano.fg01.model;

import java.util.Objects;

public class j02Software {

	private String codice;
	private String tiposw;
	private String nomesw;
	private String versione;
	
	
	public j02Software() {
		// TODO Auto-generated constructor stub
	}


	public String getCodice() {
		return codice;
	}


	public void setCodice(String codice) {
		this.codice = codice;
	}


	public String getTiposw() {
		return tiposw;
	}


	public void setTiposw(String tiposw) {
		this.tiposw = tiposw;
	}


	public String getNomesw() {
		return nomesw;
	}


	public void setNomesw(String nomesw) {
		this.nomesw = nomesw;
	}


	public String getVersione() {
		return versione;
	}


	public void setVersione(String versione) {
		this.versione = versione;
	}


	@Override
	public int hashCode() {
		return Objects.hash(codice, nomesw, tiposw, versione);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		j02Software other = (j02Software) obj;
		return Objects.equals(codice, other.codice) && Objects.equals(nomesw, other.nomesw)
				&& Objects.equals(tiposw, other.tiposw) && Objects.equals(versione, other.versione);
	}

	
	
}
