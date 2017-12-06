package hfsgladegerador.linguagens;

import hfsgladegerador.comum.Gtk;
import hfsgladegerador.comum.ILinguagem;
import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.objetos.ColunaStore;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;

public class LinguagemCSharp extends Linguagem implements ILinguagem {

	public LinguagemCSharp() {
		super(Tipo.CSharp, Versao.Gtk2, ".cs", ".cs", "Gtk.", "string", true, false, false, true);
	}

	public String getParametrosForm(String classe) {
		return super.getParametrosForm(classe);
	}

	public String getConstrutorPaiForm(Objeto formulario) {
		return super.getConstrutorPaiForm(formulario);
	}

	public String getBooleano(String valor) {
		return valor.toLowerCase();
	}

	public String getPropriedadeNome(String nome, char separador) {
		String ret;
		
		if (nome.equals("activate")){
			nome = "activated";
		}
		
		ret = Rotinas.juntar(nome, separador, true);
		
		return ret;
	}
	
	public String getEventoNome(String nome, char separador) {
		String ret;
		
		if (nome.equals("activate")){
			nome = "activated";
		}
		
		ret = Rotinas.juntar(nome, separador, true);
		
		return ret;
	}

	public String getValor(String nome, String valor) {
		return Rotinas.juntar(valor, '-', true);
	}

	public String getClasse(String classe, char separador) {
		return super.getClasse(classe, separador);
	}

	public String getTipoStore(boolean tab, String idLoja, String tipoLoja,
			String nomeColuna) {
		return "typeof(" + tipoLoja + ")";
	}

	public String getDeclaraColunaStore(boolean tab, String idLoja,
			String tipoLoja, String nomeColuna) {
		return "";
	}

	public String getValorColunaStore(boolean tab, boolean bPrimeiro,
			ColunaStore coluna, String objStore, int numeroColuna,
			String nomeColuna) {
		return "";
	}

	public String getEventoRetorno(String nome) {
		return "";
	}

	public String getEventoComRetorno(String nome) {
		return "";
	}

	public String getEventoParametros(String nome, boolean bClasseParam) {
		return "";
	}

	public String getEventoConteudo(String nome) {
		return "";
	}

	public String getInicializaConstruirFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto pai, Objeto filho) {
		String txt = super.getInicializaConstruirFilho(ret, tab, classeForm, objForm, pai, filho);
		
		if (filho.getClasse().equals(Gtk.GtkMenuItem.toString()) 
				|| filho.getClasse().equals(Gtk.GtkCheckMenuItem.toString()))
		{				
			String label = filho.getPropriedades().pegaPorNome("label").getValorLinguagem(this);				
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "("+label+")");
		} 
		else if (filho.getClasse().equals(Gtk.GtkToolButton.toString())) 
		{
			String label = filho.getPropriedades().pegaPorNome("label").getValorLinguagem(this);				
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "(null, "+label+")");
		} 
		else if (filho.getClasse().equals(Gtk.GtkAlignment.toString())) 
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "(0F, 0F, 1F, 1F)");
		} 
		else if (filho.getClasse().equals(Gtk.GtkTable.toString())) 
		{
			String n_rows = filho.getPropriedades().pegaPorNome("n_rows").getValorLinguagem(this);
			String n_columns = filho.getPropriedades().pegaPorNome("n_columns").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "("+n_rows+", "+n_columns+", true)");
		} 
		else if (filho.getClasse().equals(Gtk.GtkTreeViewColumn.toString())) 
		{
			String sTitulo = filho.getPropriedades().pegaPorNome("title").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "("+sTitulo+", new Gtk.CellRendererText(), \"text\", "+filho.getOrdemTreeViewColumn()+")");
		}
		else {
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "()");
		}
		
		return getTab(ret, tab, txt);
	}

	public String propriedadeObjetoValor(Objeto filho, Propriedade prop, String nome) {		
		String txt = "";
		
		if (prop.getNome().equals(nome)){
			txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.CSharp);
			
			if (nome.equals("model")){
				txt = txt.replaceAll("<propValor>", prop.getValor());
			} else {			
				txt = txt.replaceAll("<propValor>", prop.getValorLinguagem(this));
			}
			
			return txt;	
		} else {
			//Propriedades Compostas
			if (nome.equals("alignment_padding") && 
					(prop.getNome().equals("top_padding") || prop.getNome().equals("bottom_padding") ||
					prop.getNome().equals("left_padding") || prop.getNome().equals("right_padding"))
					){
				txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.CSharp);				
				
				txt = txt.replaceAll("<propValor>", prop.getValorLinguagem(this));

				return txt;
			}
			
			return "<propValor>";
		}
		
	}
	
	public String formataPropriedadeValores(Objeto filho, Propriedade prop, String txt) {
		if (prop.getNome().equals("model") || prop.getNome().equals("sizing") || prop.getNome().equals("shadow_type") || 
				prop.getNome().equals("window_position") || prop.getNome().equals("type_hint") || prop.getNome().equals("layout_style") || 
				prop.getNome().equals("top_padding") || prop.getNome().equals("bottom_padding") ||
				prop.getNome().equals("left_padding") || prop.getNome().equals("right_padding") ||
				prop.getNome().equals("hscrollbar_policy") || prop.getNome().equals("vscrollbar_policy") ||
				prop.getNome().equals("width_request") || prop.getNome().equals("height_request") ||
				prop.getNome().equals("default_width") || prop.getNome().equals("default_height") ||
				prop.getNome().equals("label_xalign") || prop.getNome().equals("label_yalign") ||
				prop.getNome().equals("primary_icon_activatable") || prop.getNome().equals("secondary_icon_activatable") ||
				prop.getNome().equals("primary_icon_sensitive") || prop.getNome().equals("secondary_icon_sensitive") ||
				prop.getNome().equals("sort_column_id")
			){

			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "model"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "sizing"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "shadow_type"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "window_position"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "type_hint"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "layout_style"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "hscrollbar_policy"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "width_request"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "default_width"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "label_xalign"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "sort_column_id"));

			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "top_padding"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "bottom_padding"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "left_padding"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "right_padding"));
			
			if (prop.getNome().equals("vscrollbar_policy")){
				txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "vscrollbar_policy"));
			}
			if (prop.getNome().equals("height_request")){
				txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "height_request"));
			}			
			if (prop.getNome().equals("default_height")){
				txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "default_height"));
			}
			if (prop.getNome().equals("label_yalign")){
				txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "label_yalign"));
			}
				
		} else {
			txt = txt.replaceAll("<propValor>", prop.getValorLinguagem(this));
		}
		return txt;
	}

	public String getInicializaPropriedadeFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, Propriedade prop,
			boolean bFormataValor) {
		FormataPropNome fpn;
		String txt = super.getInicializaPropriedadeFilho(ret, tab, classeForm, objForm, filho, prop, bFormataValor);

		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		
		if (!bFormataValor) {
			txt = txt.replaceAll("<propNome>", prop.getNomeLinguagem(this));
			txt = txt.replaceAll("<propValor>", prop.getValor());
		} else {
		
			fpn = formataPropriedadeNomes(ret, filho, prop, txt);
			if (fpn.retorno){
				return ret;
			} else {
				txt = fpn.txt;
			}
					
			txt = formataPropriedadeValores(filho, prop, txt);
		}
	
		return getTab(ret, tab, txt);
	}

	public FormataPropNome formataPropriedadeNomes(String ret, Objeto filho,
			Propriedade prop, String txt) {
		FormataPropNome fpn = super.formataPropriedadeNomes(ret, filho, prop, txt);
		
		if (prop.getNome().equals("position_set") ||
				prop.getNome().equals("primary_icon_activatable") || prop.getNome().equals("secondary_icon_activatable") ||
				prop.getNome().equals("primary_icon_sensitive") || prop.getNome().equals("secondary_icon_sensitive")){
			fpn.retorno = true;
			fpn.txt = "";
			return fpn;			
		}			
		if ((filho.getClasse().equals(Gtk.GtkMenuItem.toString()) || filho.getClasse().equals(Gtk.GtkCheckMenuItem.toString()))
				&& (prop.getNome().equals("label") || prop.getNome().equals("use_underline"))){
			fpn.retorno = true;
			fpn.txt = "";
			return fpn;			
		}			
		
		if (filho.getClasse().equals(Gtk.GtkLabel.toString()) && prop.getNome().equals("label")){
			txt = txt.replaceAll("<propNome>", "Text");				
		} else {
			txt = txt.replaceAll("<propNome>", prop.getNomeLinguagem(this));
		}

		fpn.retorno = false;
		fpn.txt = txt;
		return fpn;		
	}

	public String getInicializaAddFilhoAoPai(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, Objeto subFilho,
			boolean bUltimoSubFilho) {
		if (filho.getClasse().equals(Gtk.GtkMenuItem.toString())){
			return ret;
		}				
		
		if (filho.getClasse().equals(Gtk.GtkTable.toString())){
			return ret;
		}

		String txt = "";
		txt = Rotinas.getRecurso("InicializaAddFilhoAoPai", Tipo.CSharp);
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		txt = txt.replaceAll("<subFilhoId>", subFilho.getId());
		
		if (filho.getClasse().equals(Gtk.GtkTreeView.toString())){
			txt = txt.replaceAll("<adiciona>", "AppendColumn");
		} else if (filho.getClasse().equals(Gtk.GtkMenu.toString()) || filho.getClasse().equals(Gtk.GtkMenuBar.toString())){
			txt = txt.replaceAll("<adiciona>", "Append");
		} else {
			txt = txt.replaceAll("<adiciona>", "Add");
		}
		
		return getTab(ret, tab, txt);

	}

	public String getInicializaPropriedadeFormulario(String ret, boolean tab,
			String classeForm, Objeto formulario, Propriedade prop, boolean bFormataValor) {
		FormataPropNome fpn;
		String txt = super.getInicializaPropriedadeFormulario(ret, tab, classeForm, formulario, prop, bFormataValor);
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<formularioClasse>",
				formulario.getClasseLinguagem(this));		
		txt = txt.replaceAll("<formularioNome>", formulario.getNome());
		
		fpn = formataPropriedadeNomes(ret, formulario, prop, txt);
		if (fpn.retorno){
			return ret;
		} else {
			txt = fpn.txt;
		}
		
		txt = formataPropriedadeValores(formulario, prop, txt);
		
		return getTab(ret, tab, txt);
	}
	
	public String getInicializaAddFilhoAoFormulario(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho,
			boolean bDialogClasse) {
		String txt = super.getInicializaAddFilhoAoFormulario(ret, tab, classeForm, objForm, filho, bDialogClasse);
		
		if (bDialogClasse){
			txt = txt.replaceAll("<contentArea>","VBox.");
		} else {
			txt = txt.replaceAll("<contentArea>", "");
		}
		
		return getTab(ret, tab, txt);
	}
	
	public String getConectaEventoFormulario(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Evento evento) {
		String txt = super.getConectaEventoFormulario(ret, tab, classeForm, formulario, evento);

		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		
		return getTab(ret, tab, txt);
	}	

	public String getConectaEventoFilho(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Objeto filho, Evento evento) {	
		String txt = super.getConectaEventoFilho(ret, tab, classeForm, formulario, filho, evento);
		
		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		
		return getTab(ret, tab, txt);
	}
	
	public String getDeclaraObjeto(String ret, boolean tab, String classeForm,
			ObjetoBasico basico) {
		String txt = super.getDeclaraObjeto(ret, tab, classeForm, basico);
		txt = txt.replaceAll("<basicoClasse>", basico.getClasseLinguagem(this));
		return getTab(ret, tab, txt);
	}

	public String getStoreAddValores(String ret, boolean tab, String objStore,
			String qtdColunas, String valoresStore) {
		String txt = super.getStoreAddValores(ret, tab, objStore, qtdColunas, valoresStore);
		txt = txt.replaceAll("<variaveis>", "");
		return getTab(ret, tab, txt);
	}

}
