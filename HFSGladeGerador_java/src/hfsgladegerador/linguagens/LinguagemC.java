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

public class LinguagemC extends Linguagem implements ILinguagem {

	public LinguagemC() {
		super(Tipo.C, Versao.Gtk2, ".h", ".c", "gtk_", "string", true, true, true, false);
	}

	public String getParametrosForm(String classe) {
		return super.getParametrosForm(classe);
	}

	public String getConstrutorPaiForm(Objeto formulario) {
		String ret = super.getConstrutorPaiForm(formulario);
		
		if (ret.isEmpty())
			return "_new()";
		else
			return ret;
	}

	public String getBooleano(String valor) {
		return valor.toUpperCase();
	}

	public String getPropriedadeNome(String nome, char separador) {
		return super.getPropriedadeNome(nome, separador);
	}

	public String getEventoNome(String nome, char separador) {
		return super.getEventoNome(nome, separador);
	}

	public String getValor(String nome, String valor) {
		return super.getValor(nome, valor);
	}

	public String getClasse(String classe, char separador) {
		return Rotinas.separar(classe, separador, true).toLowerCase();
	}

	public String getTipoStore(boolean tab, String idLoja, String tipoLoja,
			String nomeColuna) {
		return "G_TYPE_" + tipoLoja.toUpperCase();
	}

	public String getDeclaraColunaStore(boolean tab, String idLoja,
			String tipoLoja, String nomeColuna) {
		return super.getDeclaraColunaStore(tab, idLoja, tipoLoja, nomeColuna);
	}

	public String getValorColunaStore(boolean tab, boolean bPrimeiro,
			ColunaStore coluna, String objStore, int numeroColuna,
			String nomeColuna) {
		String txt = super.getValorColunaStore(tab, bPrimeiro, coluna, objStore, numeroColuna, nomeColuna);		
		return numeroColuna + ", " + txt;
	}

	public String getEventoRetorno(String nome) {
		String txt = "";
		
		if (nome.equals("button-release-event") || nome.equals("delete-event")){
			txt = "gboolean";
		} else {
			txt = "void";
		}		
		return txt;
	}

	public String getEventoComRetorno(String nome) {
		return "";
	}

	public String getEventoParametros(String nome, boolean bClasseParam) {
		String txt = "";

		if (nome.equals("delete-event") || nome.equals("button-release-event")){
			txt = "GtkWidget *widget, GdkEvent *event, gpointer user_data";	
		} else if (nome.equals("switch-page")){
			txt = "GtkNotebook *notebook, gpointer arg1, guint arg2, gpointer user_data";
		} else if (nome.equals("row-collapsed") || nome.equals("row-expanded")){
			txt = "GtkTreeView *tree_view, GtkTreeIter *iter, GtkTreePath *path, gpointer user_data";
		} else if (nome.equals("row-activated")){
			txt = "GtkTreeView *tree_view, GtkTreePath *path, GtkTreeViewColumn *column, gpointer user_data";
		} else {
			txt = "GtkObject *object, gpointer user_data";
		}
		
		return txt;
	}

	public String getEventoConteudo(String nome) {
		String txt = "";
		
		if (nome.equals("button-release-event")){
			txt = "return FALSE;";
		} else {
			txt = "";
		}
		
		return txt;
	}

	public String getInicializaConstruirFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto pai, Objeto filho) {
		String txt = super.getInicializaConstruirFilho(ret, tab, classeForm, objForm, pai, filho);
		
		if (filho.getClasse().equals(Gtk.GtkTreeViewColumn.toString())) 
		{
			String sTitulo = filho.getPropriedades().pegaPorNome("title").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + 
					"_new_with_attributes("+sTitulo+", gtk_cell_renderer_text_new(), \"text\", "+filho.getOrdemTreeViewColumn()+", NULL)");
		}
		else if (filho.getClasse().equals(Gtk.GtkLabel.toString()))
		{
			String sLabel = filho.getPropriedades().pegaPorNome("label").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new("+ sLabel +")");
		}
		else if (filho.getClasse().equals(Gtk.GtkHBox.toString()) || filho.getClasse().equals(Gtk.GtkVBox.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new(FALSE, 0)");
		}
		else if (filho.getClasse().equals(Gtk.GtkScrolledWindow.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new(NULL, NULL)");
		}
		else if (filho.getClasse().equals(Gtk.GtkAlignment.toString())) 
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new(0.0, 0.0, 1.0, 1.0)");
		} 
		else if (filho.getClasse().equals(Gtk.GtkFrame.toString())) 
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new(\"\")");
		} 
		else if (filho.getClasse().equals(Gtk.GtkTable.toString())) 
		{
			String n_rows = filho.getPropriedades().pegaPorNome("n_rows").getValorLinguagem(this);
			String n_columns = filho.getPropriedades().pegaPorNome("n_columns").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new("+n_rows+", "+n_columns+", TRUE)");
		}			
		else {			
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this) + "_new()");
		}
		
		return getTab(ret, tab, txt);
	}

	public String propriedadeObjetoValor(Objeto filho, Propriedade prop, String nome) {
		Propriedade wr, hr, dw, dh, lx, ly, tp, bp, lp, rp;
		String txt = "";
		
		if (prop.getNome().equals(nome)){
			txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.C);
			
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
				txt = Rotinas.getRecurso("PropriedadeFilho", nome, Tipo.C);				
				
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

	private String formataPropriedadeNomesExcecoesC(boolean bFilho, Objeto filho,
			Propriedade prop, boolean bFormataValor, String txt, String mascaraClasse) {
		if (!prop.getClasse(filho.getClasse()).isEmpty()){
			if ( 
		    	(filho.isWidget() && (!prop.getClasse(Gtk.GtkWidget.toString()).equals(Gtk.GtkWidget.toString()))) 
		    	){		
			    	txt = getInicializaMacroPropriedade(bFilho, prop.getClasseLinguagem(this, filho.getClasse()), 
							prop.getClasseLinguagem(this, filho.getClasse()).toUpperCase());		    	
	    	
		    } else if ( 
		    		(filho.isWidget() && prop.getClasse(filho.getClasse()).equals(Gtk.GtkWidget.toString())) ||
		    		(filho.getClasse().equals(prop.getClasse(filho.getClasse())))			    		
		    		){		
					txt = txt.replaceAll(mascaraClasse,
							prop.getClasseLinguagem(this, filho.getClasse()));
		    } else {			 		    	
					txt = getInicializaMacroPropriedade(bFilho, prop.getClasseLinguagem(this, filho.getClasse()), 
							prop.getClasseLinguagem(this, filho.getClasse()).toUpperCase());			
			}
		} else if ((filho.getClasse().equals(Gtk.GtkMenuItem.toString()) || filho.getClasse().equals(Gtk.GtkCheckMenuItem.toString()))
					&& (prop.getNome().equals("label") || prop.getNome().equals("use_underline"))){
			Gtk gtk = Gtk.valueOf(Gtk.GtkMenuItem.toString());
			txt = getInicializaMacroPropriedade(bFilho, gtk.toString(this), gtk.toString(this).toUpperCase());
		} else if ((filho.getClasse().equals(Gtk.GtkHPaned.toString()) || filho.getClasse().equals(Gtk.GtkVPaned.toString()))
				&& (prop.getNome().equals("position"))){
			Gtk gtk = Gtk.valueOf(Gtk.GtkPaned.toString());
			txt = getInicializaMacroPropriedade(bFilho, gtk.toString(this), gtk.toString(this).toUpperCase());
		} else if (filho.getClasse().equals(Gtk.GtkEntry.toString()) && 
					(prop.getNome().equals("primary_icon_activatable") || prop.getNome().equals("secondary_icon_activatable") ||
					prop.getNome().equals("primary_icon_sensitive") || prop.getNome().equals("secondary_icon_sensitive"))){
			Gtk gtk = Gtk.valueOf(Gtk.GtkEntry.toString());
			txt = getInicializaMacroPropriedade(bFilho, gtk.toString(this), gtk.toString(this).toUpperCase());						
		} else if (filho.getClasse().equals(Gtk.GtkDialog.toString()) && 
				(prop.getNome().equals("title") || prop.getNome().equals("resizable") || 
				prop.getNome().equals("type_hint"))){
			Gtk gtk = Gtk.valueOf(Gtk.GtkWindow.toString());
			txt = getInicializaMacroPropriedade(bFilho, gtk.toString(this), gtk.toString(this).toUpperCase());			
		} else {
				txt = txt.replaceAll(mascaraClasse,	filho.getClasseLinguagem(this));
		}
		
		
		if (!bFormataValor) {
			txt = getInicializaMacroPropriedade(bFilho, filho.getClasseLinguagem(this), 
					filho.getClasseLinguagem(this).toUpperCase());				
		}
		return txt;
	}
	
	public String getInicializaPropriedadeFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, Propriedade prop,
			boolean bFormataValor) {
		FormataPropNome fpn;
		String txt = super.getInicializaPropriedadeFilho(ret, tab, classeForm, objForm, filho, prop, bFormataValor);
		
		if (prop.getNome().equals("model")){
			txt = "";
			return getTab(ret, tab, txt);
		}			
			
		txt = formataPropriedadeNomesExcecoesC(true, filho, prop, bFormataValor, txt, "<filhoClasse>");
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		
		if (!bFormataValor) {
			txt = txt.replaceAll("<propNome>", prop.getNomeLinguagem(this));
			txt = txt.replaceAll("<propValor>", classeForm + "." + prop.getValor());
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

		String txt = "";
		if (subFilho.isWidget()) {
			txt = Rotinas.getRecurso("InicializaAddFilhoAoPai", Tipo.C);
		} else { 
			if (filho.getClasse().equals(Gtk.GtkTreeView.toString())){
				txt = Rotinas.getRecurso("InicializaAddFilhoAoPai", Tipo.C);			
			} else {
				txt = Rotinas.getRecurso("InicializaMacroAddFilhoAoPai", Tipo.C);
			}
		}
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		txt = txt.replaceAll("<subFilhoId>", subFilho.getId());
		
		if (filho.getClasse().equals(Gtk.GtkTreeView.toString())){
			txt = txt.replaceAll("<adiciona>", "gtk_tree_view_append_column(GTK_TREE_VIEW");
			
			if (bUltimoSubFilho){
				Propriedade propModel = filho.getPropriedades().pegaPorNome("model");
				txt += Rotinas.SEPARADOR_LINHA + 
						"\t" + propriedadeObjetoValor(filho, propModel, "model") + "(GTK_TREE_VIEW("+classeForm+"."+filho.getId()+"));";
			}
			
		} else if (filho.getClasse().equals(Gtk.GtkMenu.toString())){
			txt = txt.replaceAll("<adiciona>", "gtk_menu_append(GTK_MENU");
		} else if (filho.getClasse().equals(Gtk.GtkMenuBar.toString())){
			txt = txt.replaceAll("<adiciona>", "gtk_menu_bar_append(GTK_MENU_BAR");
		} else if (filho.getClasse().equals(Gtk.GtkFrame.toString()) 
				&& subFilho.getAtributoFilho().getTipo().equals("label")){
			txt = txt.replaceAll("<adiciona>", "gtk_frame_set_label_widget(GTK_FRAME");				
		} else if (!filho.getClasse().equals(Gtk.GtkBox.toString()) || 
				!filho.getClasse().equals(Gtk.GtkVBox.toString()) || !filho.getClasse().equals(Gtk.GtkHBox.toString())){
			txt = txt.replaceAll("<adiciona>", "gtk_container_add(GTK_CONTAINER");
		}
		
		return getTab(ret, tab, txt);
	}

	public String getInicializaPropriedadeFormulario(String ret, boolean tab,
			String classeForm, Objeto formulario, Propriedade prop, boolean bFormataValor) {
		FormataPropNome fpn;
		String txt = super.getInicializaPropriedadeFormulario(ret, tab, classeForm, formulario, prop, bFormataValor);
		
		txt = formataPropriedadeNomesExcecoesC(false, formulario, prop, bFormataValor, txt, "<formularioClasse>");
		
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
			txt = txt.replaceAll("<contentArea>", 
					"GtkWidget *content_area = gtk_dialog_get_content_area(GTK_DIALOG("+classeForm+"."+objForm+"));"+
					Rotinas.SEPARADOR_LINHA + "\t");
			txt = txt.replaceAll("<classeForm_objForm>", "content_area");
		} else {
			txt = txt.replaceAll("<contentArea>", "");
			txt = txt.replaceAll("<classeForm_objForm>", classeForm+"."+objForm);			
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

		if (basico.isWidget()){
			txt = txt.replaceAll("<basicoClasse>", "GtkWidget");					
		} else {
			txt = txt.replaceAll("<basicoClasse>", basico.getClasse());
		}

		return getTab(ret, tab, txt);
	}

	public String getStoreAddValores(String ret, boolean tab, String objStore,
			String qtdColunas, String valoresStore) {
		String txt = super.getStoreAddValores(ret, tab, objStore, qtdColunas, valoresStore);
		
		if (!tab) {
			txt = txt.replaceAll("<variaveis>", "GtkTreeIter iter;"+
						Rotinas.SEPARADOR_LINHA+Rotinas.SEPARADOR_LINHA);			
		} else {
			txt = txt.replaceAll("<variaveis>", "");
		}
		
		return getTab(ret, tab, txt);
	}

}
