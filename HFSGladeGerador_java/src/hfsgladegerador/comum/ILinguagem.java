package hfsgladegerador.comum;

import hfsgladegerador.objetos.ColunaStore;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;

public interface ILinguagem {

	final class FormataPropNome {
		public boolean retorno;
		public String txt;
	};

	public enum Tipo {
		C, CPP, Javascript, Perl, Python, Vala, Ada, CSharp, VBNet, D, Euphoria, 
		Fortran, FreeBASIC, Go, Guile, Haskell, Java, Lua, OCaml, Pascal, PHP, R, Ruby;
	};

	public enum Versao {
		Gtk2, Gtk3;
	};
	
	public String getDeclaracaoForms(StringList formsFilhos);
	public String getParametrosForm(String classe);
	public String getConstrutorPaiForm(Objeto formulario);
	public String getBooleano(String valor);
	public String getPropriedadeNome(String nome, char separador);
	public String getEventoNome(String nome, char separador);
	public String getValor(String nome, String valor);
	public String getClasse(String classe, char separador);
	public String getTipoStore(boolean tab, String idLoja, String tipoLoja, String nomeColuna);
	public String getDeclaraColunaStore(boolean tab, String idLoja, String tipoLoja, String nomeColuna);
	public String getValorColunaStore(boolean tab, boolean bPrimeiro, 
			ColunaStore coluna, String objStore, int numeroColuna, String nomeColuna);
	public String getCastStore(String idLoja);
	public String getMetodoTipo(String nome);
	public String getEventoRetorno(String nome);
	public String getEventoComRetorno(String nome);
	public String getEventoParametros(String nome, boolean bClasseParam);
	public String getEventoConteudo(String nome);
	public String getInicializaConstruirFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto pai, Objeto filho);
	public String propriedadeObjetoValor(Objeto filho, Propriedade prop, String nome);
	public String formataPropriedadeValores(Objeto filho, Propriedade prop, String txt);
	public String getInicializaPropriedadeFilho(String ret, boolean tab, String classeForm,
			String objForm, Objeto filho, Propriedade prop, boolean bFormataValor);
	public FormataPropNome formataPropriedadeNomes(String ret, Objeto filho, Propriedade prop,
			String txt);
	public String getInicializaAddFilhoAoPai(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, Objeto subFilho, 
			boolean bUltimoSubFilho);
	public String getInicializaPropriedadeFormulario(String ret, boolean tab,
			String classeForm, Objeto formulario, Propriedade prop, boolean bFormataValor);	
	public String getInicializaAddFilhoAoFormulario(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, boolean bDialogClasse);
	public String getConectaEventoFormulario(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Evento evento);	
	public String getConectaEventoFilho(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Objeto filho, Evento evento);	
	public String getDeclaraObjeto(String ret, boolean tab, String classeForm,
			ObjetoBasico basico);
	public String getStoreAddValores(String ret, boolean tab, String objStore, 
			String qtdColunas, String valoresStore);
}
