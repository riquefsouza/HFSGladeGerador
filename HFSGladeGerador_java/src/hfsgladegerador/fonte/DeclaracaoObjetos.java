package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;

public class DeclaracaoObjetos {

	private static DeclaracaoObjetos instancia;

	private DeclaracaoObjetos() {
		super();
	}

	public static DeclaracaoObjetos getInstancia() {
		if (instancia == null) {
			instancia = new DeclaracaoObjetos();
		}
		return instancia;
	}

	public String toString(Linguagem linguagem, ObjetoLista objetos,
			Objeto formulario) {
		String ret = "";
		boolean tab = false;

		if (formulario != null) {
			for (ObjetoBasico basico : formulario.getTodosFilhos()) {
				ret = linguagem.getDeclaraObjeto(ret, tab,
						formulario.getId(), basico);
				tab = true;
			}
		}

		return ret;
	}

}
