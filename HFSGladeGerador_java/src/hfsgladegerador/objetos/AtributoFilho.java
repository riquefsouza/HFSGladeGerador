package hfsgladegerador.objetos;

public class AtributoFilho {

	private String tipo;

	private String internalChild;

	public AtributoFilho() {
		this.tipo = "";
		this.internalChild = "";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getInternalChild() {
		return internalChild;
	}

	public void setInternalChild(String internalChild) {
		this.internalChild = internalChild;
	}

	@Override
	public String toString() {
		return "AtributoFilho [tipo=" + tipo + ", internalChild="
				+ internalChild + "]";
	}

}
