package hfsgladegerador.comum;

import hfsgladegerador.objetos.ColunaStore;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;

public class Linguagem implements ILinguagem {

	private Tipo tipo;
	private Versao versao;
	private String headerExtensao;
	private String extensao;
	private String prefixoGtk;
	private String tipoString;
	private boolean aspasDuplo;
	private boolean possuiArquivoResource;
	private boolean possuiArquivoHeader;
	private boolean possuiFormHeader;
	
	public Linguagem(Tipo tipo, Versao versao){
		super();
		this.tipo = tipo;
		this.versao = versao;		
	}
	
	public Linguagem(Tipo tipo, Versao versao, String headerExtensao, String extensao,
			String prefixoGtk, String tipoString, boolean aspasDuplo, 
			boolean possuiArquivoResource,
			boolean possuiArquivoHeader, boolean possuiFormHeader) {
		super();
		this.tipo = tipo;
		this.versao = versao;
		this.headerExtensao = headerExtensao;
		this.extensao = extensao;
		this.prefixoGtk = prefixoGtk;
		this.tipoString = tipoString;
		this.aspasDuplo = aspasDuplo;
		this.possuiArquivoResource = possuiArquivoResource;
		this.possuiArquivoHeader = possuiArquivoHeader;
		this.possuiFormHeader = possuiFormHeader;
	}

	public String getHeaderExtensao() {
		return headerExtensao;
	}

	public String getExtensao() {
		return extensao;
	}

	public String getRecursoExtensao() {
		if (tipo.equals(Tipo.Java)) {
			return "._java";
		}
		return extensao;
	}

	public String getPrefixoGtk() {
		return prefixoGtk;
	}

	public Tipo getTipo() {
		return tipo;
	}
	
	public Versao getVersao() {
		return versao;
	}

	protected void setVersao(Versao versao) {
		this.versao = versao;
	}
	
	public boolean isAspasDuplo() {
		return aspasDuplo;
	}

	public boolean isPossuiArquivoResource() {
		return possuiArquivoResource;
	}
	
	public boolean isPossuiArquivoHeader() {
		return possuiArquivoHeader;
	}

	public boolean isPossuiFormHeader() {
		return possuiFormHeader;
	}
	
	public String getTab(String ret, boolean tab, String txt) {
		if (tab) {
			txt = txt.replaceAll("<tab>", "\t");
		} else {
			txt = txt.replaceAll("<tab>", "");
		}
		txt = txt.replaceAll("<novaLinha>", Rotinas.SEPARADOR_LINHA);
		ret += txt + Rotinas.SEPARADOR_LINHA;
		return ret;
	}
	
	
	public String getDeclaracaoForms(StringList formsFilhos) {
		return "";
	}

	public String getParametrosForm(String classe) {
		return "";
	}
	
	private String getConteudoDeleteEvent(){
		String txt = Rotinas.getRecurso("ConteudoDeleteEvent", tipo);
		return txt;
	}
	
	public String getConstrutorPaiForm(Objeto formulario) {
		if (formulario.getClasse().equals(Gtk.GtkWindow.toString())) {
			return Rotinas.getRecurso("ConstrutorPaiForm", tipo);
		}
		return "";
	}
	
	public String getConstrutorRender(){
		return "";
	}

	public String getBooleano(String valor) {
		return valor;
	}
	
	public String getPropriedadeNome(String nome, char separador) {		
		String ret = "";

		if (separador == '-')
			ret = nome.replaceAll("-", "_");
		else
			ret = nome;

		return ret;
	}

	public String getEventoNome(String nome, char separador) {		
		String ret = "";

		if (separador == '-')
			ret = nome.replaceAll("-", "_");
		else
			ret = nome;

		return ret;
	}
	
	public String getValor(String nome, String valor) {
		String ret;

		if (valor.equals("True") || valor.equals("False")) {
			return this.getBooleano(valor);
		}
		
		ret = valor.replaceAll("-", "_");

		if (nome.equals("label") || nome.equals("title")) {
			if (this.isAspasDuplo()) {
				ret = "\"" + valor + "\"";
			} else
				ret = "'" + valor + "'";
		}
		
		return ret;		
	}
	
	public String getClasse(String classe, char separador) {		
		return classe;
	}	
	
	public String getTipoStore(boolean tab, String idLoja, String tipoLoja, String nomeColuna) {
		tipoLoja = this.tipoString;
		return tipoLoja;		
	}

	public String getDeclaraColunaStore(boolean tab, String idLoja, String tipoLoja, String nomeColuna) {
		tipoLoja = this.tipoString;
		return tipoLoja;		
	}
	
	public String getValorColunaStore(boolean tab, boolean bPrimeiro, 
			ColunaStore coluna, String objStore, int numeroColuna, String nomeColuna) {		
		String txt = coluna.getValor();

		if (this.isAspasDuplo())
			txt = "\"" + coluna.getValor() + "\"";
		else
			txt = "'" + coluna.getValor() + "'";

		return txt;
	}
	
	public String getCastStore(String idLoja){
		return "";
	}
	
	public String getMetodoTipo(String nome){
		return "";
	}
	
	public String getEventoRetorno(String nome){
		String txt = "void";		
		return txt;		
	}

	public String getEventoComRetorno(String nome){
		return "";
	}
	
	public String getEventoParametros(String nome, boolean bClasseParam){
		return Rotinas.getEvento(tipo, versao, nome, bClasseParam);		
	}
	
	public String getEventoConteudo(String nome){
		return "";		
	}
	
	public String getConteudoMetodoMostrar(String classeForm, Objeto formulario){
		String txt = "";
		String objForm = formulario.getNome();		
		
		if (formulario.getClasse().equals(Gtk.GtkWindow.toString())) { 
			txt = Rotinas.getRecurso("MetodoMostrar", tipo);		
			txt += Rotinas.getRecurso("MetodoMostrarWindow", tipo);
			txt = txt.replaceAll("<classeForm>", classeForm);
			txt = txt.replaceAll("<paramForm>", "");
			txt = txt.replaceAll("<objForm>", objForm);
			txt = txt.replaceAll("<tab>", "\t");
			txt = txt.replaceAll("<novaLinha>", Rotinas.SEPARADOR_LINHA);						
		} else if (formulario.isJanela()) {
			txt = Rotinas.getRecurso("MetodoMostrar", tipo);
			txt += Rotinas.getRecurso("MetodoMostrarDialog", tipo);
			txt = txt.replaceAll("<classeForm>", classeForm);
			txt = txt.replaceAll("<paramForm>", "parent");
			txt = txt.replaceAll("<objForm>", objForm);
			txt = txt.replaceAll("<tab>", "\t");
			txt = txt.replaceAll("<novaLinha>", Rotinas.SEPARADOR_LINHA);			
		} else if (formulario.getClasse().equals(Gtk.GtkMenu.toString())) { 
			txt = Rotinas.getRecurso("MetodoMostrarMenuPopup", tipo);
			txt = txt.replaceAll("<classeForm>", classeForm);
			txt = txt.replaceAll("<paramForm>", "parent");
			txt = txt.replaceAll("<objForm>", objForm);
			txt = txt.replaceAll("<tab>", "\t");
			txt = txt.replaceAll("<novaLinha>", Rotinas.SEPARADOR_LINHA);
		}
		
		return txt;		
	}
	
	public String getInicializaConstruirFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto pai, Objeto filho) {
		String txt = Rotinas.getRecurso("InicializaConstruirFilho", versao, tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		return txt;
	}

	public String getInicializaPropNomeFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho) {
		
		if (filho.getClasse().equals(Gtk.GtkTreeViewColumn.toString())){
			return ret;
		}
			
		String txt = Rotinas.getRecurso("InicializaPropNomeFilho", tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		return getTab(ret, tab, txt);
	}
	
	public String getInicializaPropriedadeFilho(String ret, boolean tab, String classeForm,
			String objForm, Objeto filho, Propriedade prop, boolean bFormataValor) {
		String txt = Rotinas.getRecurso("InicializaPropriedadeFilho", tipo);
		
		return txt;
	}

	public String getInicializaMacroPropriedade(boolean bFilho, String filhoClasse, String macroFilhoClasse) {
		String txt = "";
		if (bFilho) {
			txt = Rotinas.getRecurso("InicializaMacroPropriedadeFilho", tipo);
			txt = txt.replaceAll("<filhoClasse>", filhoClasse);
			txt = txt.replaceAll("<macroFilhoClasse>", macroFilhoClasse);
		} else {
			txt = Rotinas.getRecurso("InicializaMacroPropriedadeFormulario", tipo);
			txt = txt.replaceAll("<formularioClasse>", filhoClasse);
			txt = txt.replaceAll("<macroFormularioClasse>", macroFilhoClasse);			
		}
		return txt;
	}
	
	public FormataPropNome formataPropriedadeNomes(String ret, Objeto filho, Propriedade prop,
			String txt) {		
		FormataPropNome fpn = new FormataPropNome();
		
		if (filho.getClasse().equals(Gtk.GtkDialog.toString()) && prop.getNome().equals("visible")){
			fpn.retorno = true;
			fpn.txt = "";
			return fpn;			
		}			
				
		return fpn;
	}

	public String getInicializaAddFilhoAoPai(String ret, boolean tab,
			String classeForm, String ObjetoForm, Objeto filho, Objeto subFilho, 
			boolean bUltimoSubFilho) {				
		return "";
	}
		
	public String getEmpacota(String ret, boolean tab, String classeForm, String objForm,
			Objeto filho, Objeto subFilho) {
		Propriedade pacoteExpand, pacoteFill, pacotePosition;
		Propriedade leftAttach, rightAttach, topAttach, bottomAttach, XPadding, YPadding;
		
		String txt = Rotinas.getRecurso("empacota", tipo);
		
		if (filho.getClasse().equals(Gtk.GtkTable.toString())){
			txt = Rotinas.getRecurso("tableAttach", tipo);
			txt = txt.replaceAll("<classeForm>", classeForm);
			txt = txt.replaceAll("<objForm>", objForm);
			txt = txt.replaceAll("<filhoId>", filho.getId());
			txt = txt.replaceAll("<subFilhoId>", subFilho.getId());		
			
			leftAttach = subFilho.getPacotes().pegaPorNome("left_attach");
			rightAttach = subFilho.getPacotes().pegaPorNome("right_attach");
			topAttach = subFilho.getPacotes().pegaPorNome("top_attach");
			bottomAttach = subFilho.getPacotes().pegaPorNome("bottom_attach");
			XPadding = subFilho.getPacotes().pegaPorNome("x_padding");
			YPadding = subFilho.getPacotes().pegaPorNome("y_padding");
			
			txt = txt.replaceAll("<leftAttach>", (leftAttach.getValor().equals("-1") ? "0":leftAttach.getValor()));
			txt = txt.replaceAll("<rightAttach>", (rightAttach.getValor().equals("-1") ? "1":rightAttach.getValor()));
			txt = txt.replaceAll("<topAttach>", (topAttach.getValor().equals("-1") ? "0":topAttach.getValor()));
			txt = txt.replaceAll("<bottomAttach>", (bottomAttach.getValor().equals("-1") ? "1":bottomAttach.getValor()));
			txt = txt.replaceAll("<XPadding>", (XPadding.getValor().equals("-1") ? "0":XPadding.getValor()));
			txt = txt.replaceAll("<YPadding>", (YPadding.getValor().equals("-1") ? "0":YPadding.getValor()));
			
			return getTab(ret, tab, txt);
		}		
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		txt = txt.replaceAll("<subFilhoId>", subFilho.getId());		
		
		/*
            <property name="resize">False</property>
            <property name="shrink">True</property>
		 */
		
		pacoteExpand = subFilho.getPacotes().pegaPorNome("expand"); 
		pacoteFill = subFilho.getPacotes().pegaPorNome("fill");
		pacotePosition = subFilho.getPacotes().pegaPorNome("position");
		
		if (pacoteExpand.getValor().equals("-1") || 
				pacoteFill.getValor().equals("-1") || 
				pacotePosition.getValor().equals("-1")){
			return ret;
		} else {		
			if (!pacoteExpand.getValor().equals("-1") && 
					!pacoteFill.getValor().equals("-1")){
				txt = txt.replaceAll("<pacoteExpand>", pacoteExpand.getValorLinguagem(this));
				txt = txt.replaceAll("<pacoteFill>", pacoteFill.getValorLinguagem(this));
			}
			if (!pacotePosition.getValor().equals("-1")){
				txt = txt.replaceAll("<pacotePosition>", pacotePosition.getValorLinguagem(this));
			}
		}
		
		switch (tipo) {		
		case CSharp:
			txt = txt.replaceAll("<tabFixo>", "\t\t\t");
			break;
		case Java:
			txt = txt.replaceAll("<tabFixo>", "\t\t");
			break;
		default:
			txt = txt.replaceAll("<tabFixo>", "\t");
			break;
		}
		return getTab(ret, tab, txt);
	}

	public String getInicializaPropNomeFormulario(String ret, boolean tab,
			String classeForm, Objeto formulario) {
		String txt = Rotinas.getRecurso("InicializaPropNomeFormulario", tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<formularioNome>", formulario.getNome());
		return getTab(ret, tab, txt);
	}

	public String getInicializaPropriedadeFormulario(String ret, boolean tab,
			String classeForm, Objeto formulario, Propriedade prop, boolean bFormataValor) {
		String txt = Rotinas.getRecurso("InicializaPropriedadeFormulario", tipo);
		
		return txt;
	}

	public String getInicializaAddFilhoAoFormulario(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, boolean bDialogClasse) {	
		String txt = Rotinas.getRecurso("InicializaAddFilhoAoFormulario", tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);		
		txt = txt.replaceAll("<filhoId>", filho.getId());

		return txt;
	}

	public String getConectaEventoFormulario(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Evento evento) {
		String txt = Rotinas.getRecurso("ConectaEventoFormulario", versao, tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<formularioId>", formulario.getId());
		txt = txt.replaceAll("<formularioNome>", formulario.getNome());
		txt = txt.replaceAll("<eventoNome>", evento.getNomeLinguagem(this));		
		txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
		
		return txt;
	}

	public String getConectaEventoFilho(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Objeto filho, Evento evento) {
		String txt = Rotinas.getRecurso("ConectaEventoFilho", versao, tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<formularioId>", formulario.getId());
		txt = txt.replaceAll("<formularioNome>", formulario.getNome());
		txt = txt.replaceAll("<filhoId>", filho.getId());
		txt = txt.replaceAll("<eventoNome>", evento.getNomeLinguagem(this));					
		txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
		
		return txt;
	}

	public String getMetodosFormulario(String ret, boolean tab,
			String classeForm, Evento evento, String sConteudo) {
		String txt = Rotinas.getRecurso("MetodosFormulario", tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<metodoTipo>", evento.getMetodoTipoLinguagem(this));	
		txt = txt.replaceAll("<eventoNome>",
				Rotinas.capitalize(evento.getNome()));
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		txt = txt.replaceAll("<eventoRetorno>", evento.getRetornoLinguagem(this));				
		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		txt = txt.replaceAll("<eventoParametros>", evento.getParametrosLinguagem(this, true));
		
		if (sConteudo.isEmpty()) {
			txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
			
			if (evento.getNome().equals("delete-event")){
				txt = txt.replaceAll("<conteudo>", this.getConteudoDeleteEvent());
			} else {
				txt = txt.replaceAll("<conteudo>", this.getEventoConteudo(evento.getNome()));
			}
			txt = txt.replaceAll("<tabFixo>", "\t");
			txt = txt.replaceAll("<novaLinha2>", Rotinas.SEPARADOR_LINHA);
		} else {
			txt = txt.replaceAll("<eventoManipulador>", "on"+evento.getNomeLinguagem(this));
			txt = txt.replaceAll("<conteudo>", sConteudo);
			txt = txt.replaceAll("<tabFixo>", "\t\t\t");
			txt = txt.replaceAll("<novaLinha2>", "");
		}
				
		return getTab(ret, tab, txt);
	}

	public String getMetodosFilho(String ret, boolean tab, String classeForm,
			Evento evento, String sConteudo) {
		if (tipo.equals(Tipo.Java)) {
			if (evento.getNome().equals("row-collapsed")){
				return ret;
			}
		}		
		
		String txt = Rotinas.getRecurso("MetodosFilho", tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<metodoTipo>", evento.getMetodoTipoLinguagem(this));
		txt = txt.replaceAll("<eventoNome>",
				Rotinas.capitalize(evento.getNome()));
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		txt = txt.replaceAll("<eventoRetorno>", evento.getRetornoLinguagem(this));		
		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		txt = txt.replaceAll("<eventoParametros>", evento.getParametrosLinguagem(this, true));
		
		if (sConteudo.isEmpty()) {
			txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
			txt = txt.replaceAll("<conteudo>", this.getEventoConteudo(evento.getNome()));
			txt = txt.replaceAll("<tabFixo>", "\t");
			txt = txt.replaceAll("<novaLinha2>", Rotinas.SEPARADOR_LINHA);
		} else {
			txt = txt.replaceAll("<eventoManipulador>", "on"+evento.getNomeLinguagem(this));
			txt = txt.replaceAll("<conteudo>", sConteudo);
			txt = txt.replaceAll("<tabFixo>", "\t\t\t");
			txt = txt.replaceAll("<novaLinha2>", "");
		}
		
		return getTab(ret, tab, txt);
	}

	public String getDeclaraMetodoFilho(String ret, boolean tab, Evento evento, boolean bClasseParam) {
		String txt = Rotinas.getRecurso("DeclaraMetodoFilho", tipo);
		txt = txt.replaceAll("<metodoTipo>", evento.getMetodoTipoLinguagem(this));	
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		txt = txt.replaceAll("<eventoRetorno>", evento.getRetornoLinguagem(this));
		txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
		txt = txt.replaceAll("<eventoParametros>", evento.getParametrosLinguagem(this, bClasseParam));
		return getTab(ret, tab, txt);
	}

	public String getDeclaraMetodoFormulario(String ret, boolean tab,
			Evento evento, boolean bClasseParam) {
		String txt = Rotinas.getRecurso("DeclaraMetodoFormulario", tipo);
		txt = txt.replaceAll("<metodoTipo>", evento.getMetodoTipoLinguagem(this));	
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		txt = txt.replaceAll("<eventoRetorno>", evento.getRetornoLinguagem(this));
		txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
		txt = txt.replaceAll("<eventoParametros>", evento.getParametrosLinguagem(this, bClasseParam));
		return getTab(ret, tab, txt);
	}

	public String getDeclaraObjeto(String ret, boolean tab, String classeForm,
			ObjetoBasico basico) {
		String txt = Rotinas.getRecurso("DeclaraObjeto", tipo);
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<basicoId>", basico.getId());
		
		return txt;
	}

	public String getStoreAddValores(String ret, boolean tab, String objStore, 
			String qtdColunas, String valoresStore) {
		String txt = Rotinas.getRecurso("StoreAddValores", tipo);
		txt = txt.replaceAll("<objStore>", objStore);
		txt = txt.replaceAll("<qtdColunas>", qtdColunas);
		txt = txt.replaceAll("<valoresStore>", valoresStore);		
		txt = txt.replaceAll("<tabFixo>", "\t");
		return txt;
	}

	public String propriedadeObjetoValor(Objeto filho, Propriedade prop,
			String nome) {
		return "";
	}

	public String formataPropriedadeValores(Objeto filho, Propriedade prop,
			String txt) {
		return txt;
	}
	
}
