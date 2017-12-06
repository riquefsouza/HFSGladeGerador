package hfsgladegerador.objetos;

public class Requerido {

	private String lib;

	private double versao;

	public String getLib() {
		return lib;
	}

	public void setLib(String lib) {
		this.lib = lib;
	}

	public double getVersao() {
		return versao;
	}

	public void setVersao(double versao) {
		this.versao = versao;
	}

	@Override
	public String toString() {
		return "Requerido [lib=" + lib + ", versao=" + versao + "]";
	}

}
