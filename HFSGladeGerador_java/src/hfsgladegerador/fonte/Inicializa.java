package hfsgladegerador.fonte;

import hfsgladegerador.comum.Gtk;
import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;

public class Inicializa {

	private static Inicializa instancia;

	private Inicializa() {
		super();
	}

	public static Inicializa getInstancia() {
		if (instancia == null) {
			instancia = new Inicializa();
		}
		return instancia;
	}

	public String toString(Linguagem linguagem, String classeForm, ObjetoLista objetos,
			Objeto formulario) {
		String ret = "", sAddActionAreaAoPai = "";
		boolean tab = false, bUltimoSubFilho = false, bDialogClasse = false;
		Objeto pai, filho, subFilho;
		int nConta;

		if (formulario != null) {

			for (ObjetoBasico basico : formulario.getTodosFilhosInvertidos()) {
				filho = objetos.get(basico);

				pai = objetos.get(filho.getPai());
				ret = linguagem.getInicializaConstruirFilho(ret, tab,
						classeForm, formulario.getNome(), pai, filho);				

				tab = true;

				// propriedades do filho
				ret = linguagem.getInicializaPropNomeFilho(ret, tab,
						classeForm, formulario.getNome(), filho);							
				for (Propriedade prop : filho.getPropriedades()) {
					ret = linguagem.getInicializaPropriedadeFilho(ret, tab,
							classeForm, formulario.getNome(), filho, prop, true);
				}

				// Adiciona o Filho ao Pai
				nConta = 1;
				bUltimoSubFilho = false;

				for (ObjetoBasico subBasico : filho.getFilhos()) {
					subFilho = objetos.get(subBasico);
					
					if (filho.isPossuiSubmenu()){
						ret = linguagem.getInicializaPropriedadeFilho(ret, tab,
								classeForm, formulario.getNome(), filho, 
								new Propriedade("submenu", subFilho.getId()), false);					
					}
					
					bUltimoSubFilho = (nConta == filho.getFilhos().size());					
					
					if (subFilho.getAtributoFilho().getInternalChild().equals("vbox") ||
							subFilho.getAtributoFilho().getInternalChild().equals("action_area")){
						sAddActionAreaAoPai = linguagem.getInicializaAddFilhoAoPai("", tab,
								formulario.getId(), formulario.getNome(), filho, subFilho, bUltimoSubFilho);
						
						if (subFilho.getPacotes().size() > 0) {
							sAddActionAreaAoPai = linguagem.getEmpacota(sAddActionAreaAoPai, 
									tab, classeForm, formulario.getNome(), filho, subFilho);
						}
						
					} else {					
						ret = linguagem.getInicializaAddFilhoAoPai(ret, tab,
								formulario.getId(), formulario.getNome(), filho, subFilho, bUltimoSubFilho);						
						
						if (subFilho.getPacotes().size() > 0) {
							ret = linguagem.getEmpacota(ret, tab, classeForm, 
									formulario.getNome(), filho, subFilho);
						}						
					}

					
					nConta++;
				}

				if (tab)
					ret += sAddActionAreaAoPai + "\t\n";
				else
					ret += "\n";

			}

			// propriedades do formulario
			ret = linguagem.getInicializaPropNomeFormulario(ret, tab,
					classeForm, formulario);
			for (Propriedade prop : formulario.getPropriedades()) {
				ret = linguagem.getInicializaPropriedadeFormulario(ret, tab,
						classeForm, formulario, prop, true);
			}

			// Adiciona o Filho ao Formulario
			for (ObjetoBasico basico : formulario.getFilhos()) {
				filho = objetos.get(basico);
				
				bDialogClasse = formulario.getClasse().equals(Gtk.GtkDialog.toString());
				
				ret = linguagem.getInicializaAddFilhoAoFormulario(ret, tab,
						classeForm, formulario.getNome(), filho,
						bDialogClasse);
			}

			ret += "\t\n";

		}

		return ret;
	}

}
