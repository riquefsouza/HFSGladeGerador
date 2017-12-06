package hfsgladegerador.objetos;

public class Acao {

	private String response;

	private String valor;

	public Acao() {
		super();
		this.response = "";
		this.valor = "";
	}

	public Acao(String response, String valor) {
		super();
		this.response = response;
		this.valor = valor;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Acao [response=" + response + ", valor=" + valor + "]";
	}

}
