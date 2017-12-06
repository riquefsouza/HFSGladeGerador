package hfsgladegerador.objetos;

import java.util.ArrayList;

//Store
public class Store extends ObjetoBasico {

	private ArrayList<String> tipos;

	private ArrayList<LinhaStore> linhas;

	public Store() {
		super();
		this.tipos = new ArrayList<String>();
		this.linhas = new ArrayList<LinhaStore>();
	}

	public ArrayList<String> getTipos() {
		return tipos;
	}

	public void setTipos(ArrayList<String> tipos) {
		this.tipos = tipos;
	}

	public ArrayList<LinhaStore> getLinhas() {
		return linhas;
	}

	public void setLinhas(ArrayList<LinhaStore> linhas) {
		this.linhas = linhas;
	}

	@Override
	public String toString() {
		return "Store [classe=" + getClasse() + ", id=" + getId() + ", tipos=" + tipos
				+ ", linhas=" + linhas + "]";
	}

}
