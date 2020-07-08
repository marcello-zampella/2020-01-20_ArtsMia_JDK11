package it.polito.tdp.artsmia.model;

public class Collegamento {
	
	Artisti a1;
	Artisti a2;
	int peso;
	public Collegamento(Artisti a1, Artisti a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Artisti getA1() {
		return a1;
	}
	public void setA1(Artisti a1) {
		this.a1 = a1;
	}
	public Artisti getA2() {
		return a2;
	}
	public void setA2(Artisti a2) {
		this.a2 = a2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collegamento other = (Collegamento) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!(a1.equals(other.a1)||a1.equals(other.a2)))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2)||a2.equals(other.a1))
			return false;
		return true;
	}
	
	
	
	

}
