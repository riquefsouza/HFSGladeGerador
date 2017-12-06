package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;

public class Metodos {

	private static Metodos instancia;

	private Metodos() {
		super();
	}

	public static Metodos getInstancia() {
		if (instancia == null) {
			instancia = new Metodos();
		}
		return instancia;
	}

	public String toString(Linguagem linguagem, ObjetoLista objetos,
			Objeto formulario) {
		String ret = "";
		boolean tab = false;
		Objeto filho;

		if (formulario != null) {
			for (ObjetoBasico basico : formulario.getTodosFilhos()) {
				filho = objetos.get(basico);

				// metodos do filho
				for (Evento evento : filho.getEventos()) {
					ret = linguagem.getMetodosFilho(ret, tab,
							formulario.getId(), evento, "");
					tab = true;
				}
			}

			// metodos do formulario
			for (Evento evento : formulario.getEventos()) {
				ret = linguagem.getMetodosFormulario(ret, tab,
						formulario.getId(), evento, "");
			}

		}

		return ret;
	}

}
