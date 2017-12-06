package hfsgladegerador.objetos;

import hfsgladegerador.comum.PropriedadeLista;

import java.util.ArrayList;

public class Objeto extends ObjetoBasico {
	
	private int ordemTreeViewColumn;
	
	private boolean possuiSubmenu;

	private ObjetoBasico pai;
	
	private AtributoFilho atributoFilho;

	private ArrayList<ObjetoBasico> todosFilhos;

	private ArrayList<ObjetoBasico> todosFilhosInvertidos;
	
	private ArrayList<ObjetoBasico> filhos;

	private PropriedadeLista propriedades;

	private ArrayList<Evento> eventos;

	private PropriedadeLista pacotes;

	private ArrayList<Acao> acoes;

	public Objeto() {
		super();
		this.ordemTreeViewColumn = 0;
		this.possuiSubmenu = false;
		this.pai = new ObjetoBasico();
		this.atributoFilho = new AtributoFilho();
		this.propriedades = new PropriedadeLista();
		this.todosFilhos = new ArrayList<ObjetoBasico>();
		this.todosFilhosInvertidos = new ArrayList<ObjetoBasico>();
		this.filhos = new ArrayList<ObjetoBasico>();
		this.eventos = new ArrayList<Evento>();
		this.pacotes = new PropriedadeLista();
		this.acoes = new ArrayList<Acao>();
	}

	public int getOrdemTreeViewColumn() {
		return ordemTreeViewColumn;
	}

	public void setOrdemTreeViewColumn(int ordemTreeViewColumn) {
		this.ordemTreeViewColumn = ordemTreeViewColumn;
	}
	
	public boolean isPossuiSubmenu() {
		return possuiSubmenu;
	}

	public void setPossuiSubmenu(boolean possuiSubmenu) {
		this.possuiSubmenu = possuiSubmenu;
	}
	
	public ObjetoBasico getPai() {
		return pai;
	}

	public void setPai(ObjetoBasico pai) {
		this.pai = pai;
	}

	public AtributoFilho getAtributoFilho() {
		return atributoFilho;
	}

	public void setAtributoFilho(AtributoFilho atributoFilho) {
		this.atributoFilho = atributoFilho;
	}

	public PropriedadeLista getPropriedades() {
		return propriedades;
	}

	public void setPropriedades(PropriedadeLista propriedades) {
		this.propriedades = propriedades;
	}

	public ArrayList<ObjetoBasico> getTodosFilhosInvertidos() {
		return todosFilhosInvertidos;
	}

	public void setTodosFilhosInvertidos(ArrayList<ObjetoBasico> todosFilhosInvertidos) {
		this.todosFilhosInvertidos = todosFilhosInvertidos;
	}

	public ArrayList<ObjetoBasico> getTodosFilhos() {
		return todosFilhos;
	}

	public void setTodosFilhos(ArrayList<ObjetoBasico> todosFilhos) {
		this.todosFilhos = todosFilhos;
	}
	
	public ArrayList<ObjetoBasico> getFilhos() {
		return filhos;
	}

	public void setFilhos(ArrayList<ObjetoBasico> filhos) {
		this.filhos = filhos;
	}

	public PropriedadeLista getPacotes() {
		return pacotes;
	}

	public void setPacotes(PropriedadeLista pacotes) {
		this.pacotes = pacotes;
	}

	public ArrayList<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}

	public ArrayList<Acao> getAcoes() {
		return acoes;
	}

	public void setAcoes(ArrayList<Acao> acoes) {
		this.acoes = acoes;
	}

	@Override
	public String toString() {		
		return "Objeto [atributoFilho=" + atributoFilho + ", todosFilhos="
				+ todosFilhos + ", filhos=" + filhos + ", propriedades="
				+ propriedades + ", eventos=" + eventos + ", pacotes="
				+ pacotes + ", acoes=" + acoes + "]";
	}

}
