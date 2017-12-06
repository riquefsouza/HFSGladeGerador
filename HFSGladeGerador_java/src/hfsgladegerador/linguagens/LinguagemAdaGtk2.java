package hfsgladegerador.linguagens;

import hfsgladegerador.comum.Gtk;
import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;


public class LinguagemAdaGtk2 extends LinguagemAda {

	private Linguagem linguagem;
	
	public LinguagemAdaGtk2() {
		super();
		setVersao(Versao.Gtk2);
		linguagem = new Linguagem(Tipo.Ada, Versao.Gtk2);
	}
	
	public String getConstrutorRender(){
		return "";
	}
	
	public String getCastStore(String idLoja){
		return "Gtk_Tree_Model(" + idLoja + ")";
	}
	
	public String getEventoParametros(String nome, boolean bClasseParam){
		return Rotinas.getEvento(Tipo.Ada, Versao.Gtk3, nome, bClasseParam);		
	}
	
	private boolean isEventoWidget(String nome){
		return (nome.equals("button-release-event") || nome.equals("delete-event"));
	}
		
	public String getInicializaConstruirFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto pai, Objeto filho) {
		String txt = "";
		
		if (!tab){
			txt += "Cell_Renderer_Text.Gtk_New(Text_Render);" + 
					Rotinas.SEPARADOR_LINHA + Rotinas.SEPARADOR_LINHA + "\t\t";
		}
		
		txt += linguagem.getInicializaConstruirFilho(ret, tab, classeForm, objForm, pai, filho);
		
		if (filho.getClasse().equals(Gtk.GtkTable.toString())) 
		{
			String n_rows = filho.getPropriedades().pegaPorNome("n_rows").getValorLinguagem(this);
			String n_columns = filho.getPropriedades().pegaPorNome("n_columns").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this).substring(4));
			txt = txt.replaceAll("<params>", ", "+n_rows+", "+n_columns+", true");
			txt = txt.replaceAll("<filhoClasse2>", "");
		}
		else if (filho.getClasse().equals(Gtk.GtkHBox.toString()) || filho.getClasse().equals(Gtk.GtkVBox.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", "Box");
			txt = txt.replaceAll("<params>", ", False, 0");
			txt = txt.replaceAll("<filhoClasse2>", "_" + filho.getClasseLinguagem(this).substring(4));
		}
		else if (filho.getClasse().equals(Gtk.GtkHPaned.toString()) || filho.getClasse().equals(Gtk.GtkVPaned.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", "Paned");
			txt = txt.replaceAll("<params>", "");
			txt = txt.replaceAll("<filhoClasse2>", "_" + filho.getClasseLinguagem(this).substring(4));
		}
		else if (filho.getClasse().equals(Gtk.GtkButton.toString()) || filho.getClasse().equals(Gtk.GtkCheckMenuItem.toString()))
		{
			String label = filho.getPropriedades().pegaPorNome("label").getValorLinguagem(this);
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this).substring(4));
			txt = txt.replaceAll("<params>", ", "+label);
			txt = txt.replaceAll("<filhoClasse2>", "");
		}
		else if (filho.getClasse().equals(Gtk.GtkAlignment.toString())) 
		{
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this).substring(4));
			txt = txt.replaceAll("<params>", ", 0.0, 0.0, 1.0, 1.0");
			txt = txt.replaceAll("<filhoClasse2>", "");
		} 		
		else if (filho.getClasse().equals(Gtk.GtkEntry.toString()))
		{
			txt = txt.replaceAll("<filhoClasse>", "G" + filho.getClasseLinguagem(this).substring(4));
			txt = txt.replaceAll("<params>", "");
			txt = txt.replaceAll("<filhoClasse2>", "");
		}
		else {
			txt = txt.replaceAll("<filhoClasse>", filho.getClasseLinguagem(this).substring(4));
			txt = txt.replaceAll("<params>", "");
			txt = txt.replaceAll("<filhoClasse2>", "");
		}			
		
		return getTab(ret, tab, txt);
	}
	
	public String getConectaEventoFormulario(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Evento evento) {
		String sClasse;
		String txt = linguagem.getConectaEventoFormulario(ret, tab, classeForm, formulario, evento);
		
		txt = txt.replaceAll("<eventoNome>", evento.getNomeLinguagem(this).toLowerCase());	
		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));
		
		if (isEventoWidget(evento.getNome()))
			sClasse = "Widget";
		else
			sClasse = formulario.getClasse().substring(3);
			
		if (evento.getComRetornoLinguagem(this).isEmpty())
			txt = txt.replaceAll("<classePai>", sClasse);
		else
			txt = txt.replaceAll("<classePai>", sClasse + "_Return");	
		
		return getTab(ret, tab, txt);
	}	
	
	public String getConectaEventoFilho(String ret, boolean tab,
			String classeForm, ObjetoBasico formulario, Objeto filho, Evento evento) {
		String sClasse;
		String txt = linguagem.getConectaEventoFilho(ret, tab, classeForm, formulario, filho, evento);
		
		txt = txt.replaceAll("<eventoNome>", evento.getNomeLinguagem(this).toLowerCase());	
		txt = txt.replaceAll("<eventoPrefixo>", evento.getPrefixoLinguagem(this));
		txt = txt.replaceAll("<comRetorno>", evento.getComRetornoLinguagem(this));

		if (isEventoWidget(evento.getNome()))
			sClasse = "Widget";
		else
			sClasse = filho.getClasse().substring(3);
			
		if (evento.getComRetornoLinguagem(this).isEmpty())
			txt = txt.replaceAll("<classePai>", sClasse);
		else
			txt = txt.replaceAll("<classePai>", sClasse + "_Return");
		
		return getTab(ret, tab, txt);
	}
	
	public String getInicializaPropriedadeFilho(String ret, boolean tab,
			String classeForm, String objForm, Objeto filho, Propriedade prop,
			boolean bFormataValor) {
		FormataPropNome fpn;
		String txt = linguagem.getInicializaPropriedadeFilho(ret, tab, classeForm, objForm, filho, prop, bFormataValor);
		
		txt = txt.replaceAll("<classeForm>", classeForm);
		txt = txt.replaceAll("<objForm>", objForm);
		txt = txt.replaceAll("<filhoId>", filho.getId());
		
		if (!bFormataValor) {
			txt = txt.replaceAll("<propNome>", prop.getNomeLinguagem(this));
			txt = txt.replaceAll("<propValor>", objForm+"."+prop.getValor());
		} else {		
			fpn = this.formataPropriedadeNomes(ret, filho, prop, txt);
			if (fpn.retorno){
				return ret;
			} else {
				txt = fpn.txt;
			}
					
			if  (prop.getNome().equals("layout_style"))
				txt = txt.replaceAll("<propValor>", "Buttonbox_Default_Style");
			else
				txt = formataPropriedadeValores(filho, prop, txt);
		}
	
		return getTab(ret, tab, txt);
	}

	public FormataPropNome formataPropriedadeNomes(String ret, Objeto filho,
			Propriedade prop, String txt) {
		FormataPropNome fpn = linguagem.formataPropriedadeNomes(ret, filho, prop, txt);
		
		if (prop.getNome().equals("hscrollbar_policy")) {
			txt = txt.replaceAll("<propNome>", "Policy");
		} else if  (prop.getNome().equals("vscrollbar_policy") || 
				prop.getNome().equals("height_request") || prop.getNome().equals("default_height") ||
				prop.getNome().equals("n_rows") || prop.getNome().equals("n_columns")
				|| prop.getNome().equals("position_set") || prop.getNome().equals("receives_default")
				|| prop.getNome().equals("can_focus") || prop.getNome().equals("visible")){
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
	
}
