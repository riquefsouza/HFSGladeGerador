package hfsgladegerador.objetos;


public class ColunaStore {

	private int linha;

	private String id;

	private boolean traduzivel;

	private String valor;

	public ColunaStore() {
		super();
		this.linha = -1;
		this.id = "";
		this.traduzivel = false;
		this.valor = "";
	}

	public ColunaStore(int linha, String id, boolean traduzivel, String valor) {
		super();
		this.linha = linha;
		this.id = id;
		this.traduzivel = traduzivel;
		this.valor = valor;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isTraduzivel() {
		return traduzivel;
	}

	public void setTraduzivel(boolean traduzivel) {
		this.traduzivel = traduzivel;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "ColunaStore [linha=" + linha + ", id=" + id + ", traduzivel="
				+ traduzivel + ", valor=" + valor + "]";
	}

}
