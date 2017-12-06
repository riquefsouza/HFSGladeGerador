package hfsgladegerador.linguagens;

import hfsgladegerador.comum.Gtk;
import hfsgladegerador.comum.ILinguagem;
import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.comum.StringList;
import hfsgladegerador.objetos.ColunaStore;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;

public class LinguagemAda extends Linguagem implements ILinguagem {

	public LinguagemAda() {
		super(Tipo.Ada, Versao.Gtk3, ".ads", ".adb", "Gtk_", "GType_String", true, false, true, false);
	}
	
	public String getConstrutorRender(){
		return " := Gtk_Cell_Renderer_Text_New";
	}

	public String getDeclaracaoForms(StringList formsFilhos) {
		String txt = "";
		for (String filho : formsFilhos) {
			txt += "with "+filho+"; use "+filho+";" + Rotinas.SEPARADOR_LINHA;
		}
		
		return txt;
	}
	
	public String getParametrosForm(String classe) {
		return super.getParametrosForm(classe);
	}

	public String getConstrutorPaiForm(Objeto formulario) {
		return super.getConstrutorPaiForm(formulario);
	}

	public String getBooleano(String valor) {
		return Rotinas.capitalize(valor);
	}

	public String getPropriedadeNome(String nome, char separador) {
		return Rotinas.capitalize(nome, separador);
	}

	public String getEventoNome(String nome, char separador) {
		return Rotinas.capitalize(super.getEventoNome(nome, separador), '_');
	}

	public String getValor(String nome, String valor) {
		return Rotinas.capitalize(super.getValor(nome, valor), '_');
	}

	public String getClasse(String classe, char separador) {
		return Rotinas.separar(classe, separador, true);
	}

	public String getTipoStore(boolean tab, String idLoja, String tipoLoja,
			String nomeColuna) {
		return super.getTipoStore(tab, idLoja, tipoLoja, nomeColuna);
	}

	public String getDeclaraColunaStore(boolean tab, String idLoja,
			String tipoLoja, String nomeColuna) {
		return "";
	}

	public String getValorColunaStore(boolean tab, boolean bPrimeiro,
			ColunaStore coluna, String objStore, int numeroColuna,
			String nomeColuna) {
		String txt = super.getValorColunaStore(tab, bPrimeiro, coluna, objStore, numeroColuna, nomeColuna);		
		return "\t\t" + objStore + ".Set(iter, "+numeroColuna+", "+ txt +");" + Rotinas.SEPARADOR_LINHA;
	}

	public String getCastStore(String idLoja){
		return "+" + idLoja;
	}
	
	public String getMetodoTipo(String nome){
		String txt = "procedure";
		
		if (nome.equals("button-release-event") || nome.equals("delete-event")){
			txt = "function";
		}
		 
		return txt;
	}
	
	public String getEventoRetorno(String nome) {
		String txt = "";
		
		if (nome.equals("button-release-event") || nome.equals("delete-event")){
			txt = " Boolean";
		} 
		
		return txt;
	}

	public String getEventoComRetorno(String nome) {
		String txt = "";
		
		if (nome.equals("button-release-event") || nome.equals("delete-event")){
			txt = " return";
		}
		 
		return txt;
	}

	public String getEventoConteudo(String nome) {
		String txt = "null;";
		
		if (nome.equals("button-release-event")){
			txt = "return False;";
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
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_New("+n_rows+", "+n_columns+", true)");
		}
		else if (filho.getClasse().equals(Gtk.GtkHBox.toString()) || filho.getClasse().equals(Gtk.GtkVBox.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_New(False, 0)");
		}
		else if (filho.getClasse().equals(Gtk.GtkButton.toString()) || filho.getClasse().equals(Gtk.GtkCheckMenuItem.toString()))
		{
			String label = filho.getPropriedades().pegaPorNome("label").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_New_With_Label("+label+")");
		}
		else if (filho.getClasse().equals(Gtk.GtkAlignment.toString())) 
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_New(0.0, 0.0, 1.0, 1.0)");
		} 		
		else {
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_New");
		}			
		
		return getTab(ret, tab, txt);
	}

	public String propriedadeObjetoValor(Objeto filho, Propriedade prop, String nome) {
		Propriedade wr, hr, dw, dh, lx, ly, tp, bp, lp, rp;
		String txt = "";
		
		if (prop.getNome().equals(nome)){
			txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.Ada);
			
			if (nome.equals("model")){
				txt = txt.replaceAll("<propValor>", "modelo_" + prop.getValor());
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
					txt = txt.replaceAll("<propValor1>", (lx.getValor().equals("-1") ? "0":lx.getValor()) + ".0");
					txt = txt.replaceAll("<propValor2>", (ly.getValor().equals("-1") ? "0":ly.getValor()) + ".0");
				} else {
					txt = txt.replaceAll("<propValor>", prop.getValorLinguagem(this));
				}
			}
			
			return txt;	
		} else {
			//Propriedades Compostas
			if (nome.equals("alignment_padding") && 
					(prop.getNome().equals("top_padding") || prop.getNome().equals("bottom_padding") ||
					prop.getNome().equals("left_padding") || prop.getNome().equals("right_padding"))
					){
				txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.Ada);				
				
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
			txt = txt.replaceAll("<propValor>", objForm+"."+prop.getValor());
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
			txt = txt.replaceAll("<propNome>", "Policy");
		} else if  (prop.getNome().equals("vscrollbar_policy") || 
				prop.getNome().equals("height_request") || prop.getNome().equals("default_height") ||
				prop.getNome().equals("n_rows") || prop.getNome().equals("n_columns")
				|| prop.getNome().equals("position_set") || prop.getNome().equals("has_resize_grip")){
			fpn.retorno = true;
			fpn.txt = "";
			return fpn;
		} else if  (prop.getNome().equals("window_position")){
			txt = txt.replaceAll("<propNome>", "Position");
		} else if  (prop.getNome().equals("layout_style")){
			txt = txt.replaceAll("<propNome>", "Layout");
		} else if  (prop.getNome().equals("width_request")){
			txt = txt.replaceAll("<propNome>", "Size_Request");
		} else if  (prop.getNome().equals("top_padding") || prop.getNome().equals("bottom_padding") ||
				prop.getNome().equals("left_padding") || prop.getNome().equals("right_padding")){
			txt = txt.replaceAll("<propNome>", "Padding");				
		} else if  (prop.getNome().equals("default_width")){
			txt = txt.replaceAll("<propNome>", "Default_Size");
		} else if  (prop.getNome().equals("label_xalign")){
			txt = txt.replaceAll("<propNome>", "Label_Align");
		} else if  (prop.getNome().equals("primary_icon_activatable") || 
						prop.getNome().equals("secondary_icon_activatable")){
			txt = txt.replaceAll("<propNome>", "Icon_Activatable");
		} else if  (prop.getNome().equals("primary_icon_sensitive") ||
						prop.getNome().equals("secondary_icon_sensitive")){
			txt = txt.replaceAll("<propNome>", "Icon_Sensitive");			
		} else if (filho.getClasse().equals(Gtk.GtkDialog.toString()) && prop.getNome().equals("type_hint")){
			fpn.retorno = true;
			fpn.txt = "";
			return fpn;			
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
		txt = Rotinas.getRecurso("InicializaAddFilhoAoPai", Tipo.Ada);
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		txt = txt.replaceAll("<subFilhoId>", subFilho.getId());
		
		if (filho.getClasse().equals(Gtk.GtkTreeView.toString())){
			txt = txt.replaceAll("<atribui>", "Num := ");
			txt = txt.replaceAll("<adiciona>", "Append_Column");
		    txt += Rotinas.SEPARADOR_LINHA + "\t\t" + objForm + "." + subFilho.getId() + ".Pack_Start(Text_Render, True);";
		    txt += Rotinas.SEPARADOR_LINHA + "\t\t" + objForm + "." + subFilho.getId() + ".Add_Attribute(Text_Render, \"text\", "+subFilho.getOrdemTreeViewColumn()+");";
		} else if (filho.getClasse().equals(Gtk.GtkMenu.toString()) || filho.getClasse().equals(Gtk.GtkMenuBar.toString())){
			txt = txt.replaceAll("<atribui>", "");
			txt = txt.replaceAll("<adiciona>", "Append");				
		} else if (filho.getClasse().equals(Gtk.GtkFrame.toString()) 
				&& subFilho.getAtributoFilho().getTipo().equals("label")){
			txt = txt.replaceAll("<atribui>", "");
			txt = txt.replaceAll("<adiciona>", "Set_Label_Widget");				
		} else if (!filho.getClasse().equals(Gtk.GtkBox.toString()) || 
				!filho.getClasse().equals(Gtk.GtkVBox.toString()) || !filho.getClasse().equals(Gtk.GtkHBox.toString()) ||
				!filho.getClasse().equals(Gtk.GtkVButtonBox.toString()) || !filho.getClasse().equals(Gtk.GtkHButtonBox.toString())){
			txt = txt.replaceAll("<atribui>", "");
			txt = txt.replaceAll("<adiciona>", "Add");
		} else {
			txt = txt.replaceAll("<atribui>", "");
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
			txt = txt.replaceAll("<contentArea>", objForm+".Get_Content_Area.Pack_Start("+objForm+"."+filho.getId()+", True, True, 0);");
		} else {
			txt = txt.replaceAll("<contentArea>", objForm+".Add("+objForm+"."+filho.getId()+");");	
		}
		
		return getTab(ret, tab, txt);
	}

	public String getConectaEventoFormulario(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Evento evento) {
		String txt = super.getConectaEventoFormulario(ret, tab, classeForm, formulario, evento);

		if (evento.getComRetornoLinguagem(this).isEmpty())
			txt = txt.replaceAll("<classePai>", formulario.getClasse().substring(3));
		else
			txt = txt.replaceAll("<classePai>", formulario.getClasse().substring(3) + "_Return");	
		
		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		
		return getTab(ret, tab, txt);
	}
	
	public String getConectaEventoFilho(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Objeto filho, Evento evento) {	
		String txt = "";
		
		if (filho.getClasse().equals(Gtk.GtkNotebook.toString())) {
			txt = Rotinas.getRecurso("ConectaEventoFilho", Versao.Gtk2, Tipo.Ada);
			txt = txt.replaceAll("<classeForm>", classeForm);
			txt = txt.replaceAll("<formularioId>", formulario.getId());
			txt = txt.replaceAll("<formularioNome>", formulario.getNome());
			txt = txt.replaceAll("<filhoId>", filho.getId());
			txt = txt.replaceAll("<eventoNome>", evento.getNomeLinguagem(this).toLowerCase());					
			txt = txt.replaceAll("<eventoManipulador>", evento.getManipulador());
		} else {		
			txt = super.getConectaEventoFilho(ret, tab, classeForm, formulario, filho, evento);
			txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
			txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		}

		if (evento.getComRetornoLinguagem(this).isEmpty())
			txt = txt.replaceAll("<classePai>", filho.getClasse().substring(3));
		else
			txt = txt.replaceAll("<classePai>", filho.getClasse().substring(3) + "_Return");
		
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
