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
import org.apache.commons.lang3.StringEscapeUtils;

public class LinguagemPython extends Linguagem implements ILinguagem {

	public LinguagemPython() {
		super(Tipo.Python, Versao.Gtk2, ".py", ".py", "gtk.", "string", true, false, false, false);
	}

	public String getParametrosForm(String classe) {
		return super.getParametrosForm(classe);
	}

	public String getConstrutorPaiForm(Objeto formulario) {
		String ret = super.getConstrutorPaiForm(formulario);

		ret = ret.replaceAll("<formularioNome>", "form");
		
		if (ret.isEmpty()) {
			if (formulario.getClasse().equals(Gtk.GtkDialog.toString())) {
				return "self.form = gtk.Dialog()";
			}
			return "";			
		} else
			return ret;
	}

	public String getBooleano(String valor) {		
		return Rotinas.capitalize(valor);
	}

	public String getPropriedadeNome(String nome, char separador) {
		return super.getPropriedadeNome(nome, separador);
	}

	public String getEventoNome(String nome, char separador) {
		return super.getEventoNome(nome, separador);
	}
	
	public String getValor(String nome, String valor) {
		if (valor.equals("True") || valor.equals("False")) {
			return super.getValor(nome, valor);
		} else {
			if (Rotinas.possuiAcentos(valor)){
				valor = StringEscapeUtils.escapeJava(valor);
				
				valor = valor.replaceAll("-", "_");
				valor = valor.replaceAll("\\\\", "\\\\\\\\");

				if (nome.equals("label") || nome.equals("title")) {
					if (this.isAspasDuplo()) {
						valor = "u\"" + valor + "\"";
					} else
						valor = "u'" + valor + "'";
				}
				return valor;				
			}
			return super.getValor(nome, valor);
		}
	}

	public String getClasse(String classe, char separador) {
		return super.getClasse(classe, separador);
	}

	public String getTipoStore(boolean tab, String idLoja, String tipoLoja,
			String nomeColuna) {
		String ret = "";
		/*
		if (tab) 
			ret = "";
		else
			ret = "";
			*/
		ret += "str";
		
		return ret;
	}

	public String getDeclaraColunaStore(boolean tab, String idLoja,
			String tipoLoja, String nomeColuna) {
		return "";
	}

	public String getValorColunaStore(boolean tab, boolean bPrimeiro,
			ColunaStore coluna, String objStore, int numeroColuna,
			String nomeColuna) {
		String txt = super.getValorColunaStore(tab, bPrimeiro, coluna, objStore, numeroColuna, nomeColuna);
		
		return txt;
	}

	public String getEventoRetorno(String nome) {
		String txt = "";

		if (nome.equals("button-release-event") || nome.equals("delete-event")){
			txt = "bool";
		} else {
			txt = "void";
		}

		return txt;
	}

	public String getEventoComRetorno(String nome) {
		return "";
	}

	public String getEventoConteudo(String nome) {
		String txt = "";

		if (nome.equals("button-release-event")){
			txt = "return False";
		} else {
			txt = "pass";
		}
		
		return txt;
	}

	public String getInicializaConstruirFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto pai, Objeto filho) {
		String txt = super.getInicializaConstruirFilho(ret, tab, classeForm, objForm, pai, filho);
		
		if (filho.getClasse().equals(Gtk.GtkTable.toString())) 
		{
			String n_rows = filho.getPropriedades().pegaPorNome("n_rows").getValorLinguagem(this);
			String n_columns = filho.getPropriedades().pegaPorNome("n_columns").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "("+n_rows+", "+n_columns+", True)");
		}
		else if (filho.getClasse().equals(Gtk.GtkTreeViewColumn.toString())) 
		{
			String sTitulo = filho.getPropriedades().pegaPorNome("title").getValorLinguagem(this);
			//String sModelo = pai.getPropriedades().pegaPorNome("model").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "("+sTitulo+")"); 				
		}
		else if (filho.getClasse().equals(Gtk.GtkHBox.toString()) || filho.getClasse().equals(Gtk.GtkVBox.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "(False, 0)");
		}
		else {
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "()");
		}			
		
		return getTab(ret, tab, txt);
	}

	public String propriedadeObjetoValor(Objeto filho, Propriedade prop, String nome) {
		Propriedade wr, hr, dw, dh, lx, ly, tp, bp, lp, rp;
		String txt = "";
		
		if (prop.getNome().equals(nome)){
			txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.Python);
			
			if (nome.equals("model")){
				txt = txt.replaceAll("<propValor>", prop.getValor());
			} else {			
				if (nome.equals("width_request")){
					wr = filho.getPropriedades().pegaPorNome("width_request");
					hr = filho.getPropriedades().pegaPorNome("height_request");		
					txt = txt.replaceAll("<propValor1>", wr.getValor());
					txt = txt.replaceAll("<propValor2>", hr.getValor());				
				} else if (nome.equals("default_width")){
					dw = filho.getPropriedades().pegaPorNome("default_width");
					dh = filho.getPropriedades().pegaPorNome("default_height");	
					txt = txt.replaceAll("<propValor1>", dw.getValor());
					txt = txt.replaceAll("<propValor2>", dh.getValor());
				} else if (nome.equals("label_xalign")){
					lx = filho.getPropriedades().pegaPorNome("label_xalign");
					ly = filho.getPropriedades().pegaPorNome("label_yalign");	
					txt = txt.replaceAll("<propValor1>", (lx.getValor().equals("-1") ? "0":lx.getValor()));
					txt = txt.replaceAll("<propValor2>", (ly.getValor().equals("-1") ? "0":ly.getValor()));
				} else {
					
					if (prop.getValor().equals("True") || prop.getValor().equals("False"))
						txt = txt.replaceAll("<propValor>", prop.getValorLinguagem(this));
					else
						txt = txt.replaceAll("<propValor>", prop.getValorLinguagem(this).toUpperCase());
				}
			}
			
			return txt;	
		} else {
			//Propriedades Compostas
			if (nome.equals("alignment_padding") && 
					(prop.getNome().equals("top_padding") || prop.getNome().equals("bottom_padding") ||
					prop.getNome().equals("left_padding") || prop.getNome().equals("right_padding"))
					){
				txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.Python);				
				
				tp = filho.getPropriedades().pegaPorNome("top_padding");
				bp = filho.getPropriedades().pegaPorNome("bottom_padding");	
				lp = filho.getPropriedades().pegaPorNome("left_padding");
				rp = filho.getPropriedades().pegaPorNome("right_padding");	
				txt = txt.replaceAll("<propValor1>", (tp.getValor().equals("-1") ? "0":tp.getValor()));
				txt = txt.replaceAll("<propValor2>", (bp.getValor().equals("-1") ? "0":bp.getValor()));
				txt = txt.replaceAll("<propValor3>", (lp.getValor().equals("-1") ? "0":lp.getValor()));
				txt = txt.replaceAll("<propValor4>", (rp.getValor().equals("-1") ? "0":rp.getValor()));				
				
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

			//top_padding, bottom_padding, left_padding, right_padding
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "alignment_padding"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "primary_icon_activatable"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "secondary_icon_activatable"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "primary_icon_sensitive"));
			txt = txt.replaceAll("<propValor>", propriedadeObjetoValor(filho, prop, "secondary_icon_sensitive"));
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
			txt = txt.replaceAll("<propValor>", "self." + prop.getValor());
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
		
		if (prop.getNome().equals("hscrollbar_policy")) {
			txt = txt.replaceAll("<propNome>", "policy");
		} else if  (prop.getNome().equals("vscrollbar_policy") || 
				prop.getNome().equals("height_request") || prop.getNome().equals("default_height") ||
				prop.getNome().equals("n_rows") || prop.getNome().equals("n_columns")
				|| prop.getNome().equals("position_set")){
			fpn.retorno = true;
			fpn.txt = "";
			return fpn;
		} else if  (prop.getNome().equals("window_position")){
			txt = txt.replaceAll("<propNome>", "position");
		} else if  (prop.getNome().equals("layout_style")){
			txt = txt.replaceAll("<propNome>", "layout");
		} else if  (prop.getNome().equals("width_request")){
			txt = txt.replaceAll("<propNome>", "size_request");
		} else if  (prop.getNome().equals("top_padding") || prop.getNome().equals("bottom_padding") ||
				prop.getNome().equals("left_padding") || prop.getNome().equals("right_padding")){
			txt = txt.replaceAll("<propNome>", "padding");				
		} else if  (prop.getNome().equals("default_width")){
			txt = txt.replaceAll("<propNome>", "default_size");
		} else if  (prop.getNome().equals("label_xalign")){
			txt = txt.replaceAll("<propNome>", "label_align");
		} else if  (prop.getNome().equals("primary_icon_activatable") || 
						prop.getNome().equals("secondary_icon_activatable")){
			txt = txt.replaceAll("<propNome>", "icon_activatable");
		} else if  (prop.getNome().equals("primary_icon_sensitive") ||
						prop.getNome().equals("secondary_icon_sensitive")){
			txt = txt.replaceAll("<propNome>", "icon_sensitive");			
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
		if (subFilho.getPacotes().size() > 0){
			if (filho.getClasse().equals(Gtk.GtkBox.toString()) || 
				filho.getClasse().equals(Gtk.GtkVBox.toString()) || filho.getClasse().equals(Gtk.GtkHBox.toString()) ||
				filho.getClasse().equals(Gtk.GtkVButtonBox.toString()) || filho.getClasse().equals(Gtk.GtkHButtonBox.toString())){
				return ret;
			}							
		}

		String txt = "";
		txt = Rotinas.getRecurso("InicializaAddFilhoAoPai", Tipo.Python);
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		txt = txt.replaceAll("<subFilhoId>", subFilho.getId());
		
		if (filho.getClasse().equals(Gtk.GtkTreeView.toString())){
			txt = txt.replaceAll("<adiciona>", "append_column");
		} else if (filho.getClasse().equals(Gtk.GtkMenu.toString()) || filho.getClasse().equals(Gtk.GtkMenuBar.toString())){
			txt = txt.replaceAll("<adiciona>", "append");				
		} else if (filho.getClasse().equals(Gtk.GtkFrame.toString()) 
				&& subFilho.getAtributoFilho().getTipo().equals("label")){
			txt = txt.replaceAll("<adiciona>", "set_label_widget");				
		} else if (!filho.getClasse().equals(Gtk.GtkBox.toString()) || 
				!filho.getClasse().equals(Gtk.GtkVBox.toString()) || !filho.getClasse().equals(Gtk.GtkHBox.toString()) ||
				!filho.getClasse().equals(Gtk.GtkVButtonBox.toString()) || !filho.getClasse().equals(Gtk.GtkHButtonBox.toString())){
			txt = txt.replaceAll("<adiciona>", "add");
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
			txt = txt.replaceAll("<contentArea>","action_area.");
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
		
		if (!tab) {
			txt = txt.replaceAll("<variaveis>", "");			
		} else {
			txt = txt.replaceAll("<variaveis>", "");
		}
		
		return getTab(ret, tab, txt);
	}

}
