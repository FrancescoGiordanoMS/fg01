package francesco.giordano.fg01.model;

import java.util.Objects;

public class J03HwSw {

	private String matricola;
	private String codice;
	
	public J03HwSw() {
		super();
	}

	public String getMatricola() {
		return matricola;
	}

	public String getCodice() {
		return codice;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, matricola);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		J03HwSw other = (J03HwSw) obj;
		return Objects.equals(codice, other.codice) && Objects.equals(matricola, other.matricola);
	}
	
	
	
}
