package hfsgladegerador.objetos;

import hfsgladegerador.comum.Linguagem;

public class Evento {

	private String nome;

	private String manipulador;

	public Evento() {
		this.nome = "";
		this.manipulador = "";
	}

	public Evento(String nome, String manipulador) {
		super();
		this.nome = nome;
		this.manipulador = manipulador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getManipulador() {
		return manipulador;
	}

	public void setManipulador(String manipulador) {
		this.manipulador = manipulador;
	}

	public String getNomeLinguagem(Linguagem linguagem) {
		return linguagem.getEventoNome(nome, '-');
	}
	
	public String getPrefixoLinguagem(Linguagem linguagem) {
		if (isEventoNaoSistema()){
			return linguagem.getPrefixoGtk() + getNomeLinguagem(linguagem);
		} else {
			return "System.Event"; 
		}
	}
	
	public String getParametrosLinguagem(Linguagem linguagem, boolean bClasseParam) {
		return linguagem.getEventoParametros(nome, bClasseParam);
	}

	public String getMetodoTipoLinguagem(Linguagem linguagem){
		return linguagem.getMetodoTipo(nome);
	}
	
	public String getRetornoLinguagem(Linguagem linguagem) {
		return linguagem.getEventoRetorno(nome);
	}

	public String getComRetornoLinguagem(Linguagem linguagem) {
		return linguagem.getEventoComRetorno(nome);
	}
	
	public boolean isEventoNaoSistema(){
		return (nome.equals("delete-event") || nome.equals("switch-page") || 
				nome.equals("select-page") || nome.equals("row-collapsed") || 
				nome.equals("button-release-event") || 
				nome.equals("row-expanded") || nome.equals("row-activated"));
	}
	
	@Override
	public String toString() {
		return "Evento [nome=" + nome + ", manipulador=" + manipulador + "]";
	}

}
