package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;

public class ConectaEventos {

	private static ConectaEventos instancia;

	private ConectaEventos() {
		super();
	}

	public static ConectaEventos getInstancia() {
		if (instancia == null) {
			instancia = new ConectaEventos();
		}
		return instancia;
	}

	public String toString(Linguagem linguagem, String classeForm, ObjetoLista objetos,
			Objeto formulario) {
		String ret = "";
		boolean tab = false;
		Objeto filho;

		if (formulario != null) {
			for (ObjetoBasico basico : formulario.getTodosFilhos()) {
				filho = objetos.get(basico);

				// eventos do filho
				for (Evento evento : filho.getEventos()) {
					ret = linguagem.getConectaEventoFilho(ret, tab, 
							classeForm, formulario, filho, evento);
					tab = true;
				}
			}

			// eventos do formulario
			for (Evento evento : formulario.getEventos()) {
				ret = linguagem.getConectaEventoFormulario(ret,
						tab, classeForm, formulario, evento);
			}

		}

		return ret;
	}

}
