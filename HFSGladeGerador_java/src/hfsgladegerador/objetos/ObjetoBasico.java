package hfsgladegerador.objetos;

import hfsgladegerador.comum.Gtk;
import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.Rotinas;

public class ObjetoBasico {

	private boolean primeiroNivel;
	
	private boolean principal;

	private int ordem;

	private String classe;

	private String id;	

	public ObjetoBasico() {
		super();		
		this.primeiroNivel = false;
		this.principal = false;
		this.ordem = 0;
		this.classe = "";
		this.id = "";
	}	
	
	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}	

	public boolean isPrimeiroNivel() {
		return primeiroNivel;
	}

	public void setPrimeiroNivel(boolean primeiroNivel) {
		this.primeiroNivel = primeiroNivel;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.replaceAll("-", "_");
	}

	public String getClasseLinguagem(Linguagem linguagem) {
		Gtk gtk = Gtk.valueOf(this.classe);
		return gtk.toString(linguagem);
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getNome() {		
		return Rotinas.uncapitalize(this.id);
	}

	public boolean isJanela() {
		return Gtk.isJanela(this.getClasse());
	}

	public boolean isPopupMenu() {
		return (this.getClasse().equals(Gtk.GtkMenu.toString()) && this
				.isPrimeiroNivel());
	}
	
	public boolean isWidget() {
		return (!(
					classe.equals(Gtk.GtkToolItem.toString()) ||
					classe.equals(Gtk.GtkTreeViewColumn.toString())
				));
	}

	@Override
	public String toString() {
		return "ObjetoBasico [primeiroNivel=" + primeiroNivel + ", ordem="
				+ ordem + ", classe=" + classe + ", id=" + id + ", nome="
				+ getNome() + "]";
	}

}
