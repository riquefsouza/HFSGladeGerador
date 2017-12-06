package hfsgladegerador.objetos;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.Rotinas;

public class Propriedade {

	private String nome;

	private String valor;
	
	public Propriedade() {
		this.nome = "";
		this.valor = "";
	}

	public Propriedade(String nome, String valor) {
		super();
		this.nome = nome;
		this.valor = valor;		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getClasse(String filhoClasse) {
		return Rotinas.getPropriedadeClasse(nome, filhoClasse);
	}

	public String getNomeLinguagem(Linguagem linguagem) {
		return linguagem.getPropriedadeNome(nome, '_');
	}

	public String getValorLinguagem(Linguagem linguagem) {
		return linguagem.getValor(nome, valor);
	}
	
	public String getClasseLinguagem(Linguagem linguagem, String filhoClasse) {
		return linguagem.getClasse(getClasse(filhoClasse), '_');		
	}
	
	@Override
	public String toString() {
		return "Propriedade [nome=" + nome + ", valor=" + valor + "]";
	}

}
