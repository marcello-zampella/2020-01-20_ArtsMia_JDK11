package it.polito.tdp.artsmia.model;

public class Artisti {
	
	int id;
	String nome;
	public Artisti(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Artisti other = (Artisti) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return nome+ " id "+this.id;
	}
	
	

}
