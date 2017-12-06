package hfsgladegerador.objetos;

import java.util.ArrayList;

public class LinhaStore {

	private int linha;

	private ArrayList<ColunaStore> colunas;

	public LinhaStore() {
		super();
		this.linha = -1;
		this.colunas = new ArrayList<ColunaStore>();
	}

	public LinhaStore(int linha, ArrayList<ColunaStore> colunas) {
		super();
		this.linha = linha;
		this.colunas = colunas;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public ArrayList<ColunaStore> getColunas() {
		return colunas;
	}

	public void setColunas(ArrayList<ColunaStore> colunas) {
		this.colunas = colunas;
	}

	@Override
	public String toString() {
		return "LinhaStore [linha=" + linha + ", colunas=" + colunas + "]";
	}

}
