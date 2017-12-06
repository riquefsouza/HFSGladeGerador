package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;

public class DeclaracaoMetodos {

	private static DeclaracaoMetodos instancia;

	private DeclaracaoMetodos() {
		super();
	}

	public static DeclaracaoMetodos getInstancia() {
		if (instancia == null) {
			instancia = new DeclaracaoMetodos();
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

				// declara metodos do filho
				for (Evento evento : filho.getEventos()) {
					ret = linguagem.getDeclaraMetodoFilho(ret, tab, 
							evento, true);
					tab = true;
				}
			}

			// declara metodos do formulario
			for (Evento evento : formulario.getEventos()) {
				ret = linguagem.getDeclaraMetodoFormulario(ret,
						tab, evento, true);
			}

		}

		return ret;
	}

}
