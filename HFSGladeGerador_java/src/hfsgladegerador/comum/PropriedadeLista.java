package hfsgladegerador.comum;

import hfsgladegerador.objetos.Propriedade;

import java.util.ArrayList;

public class PropriedadeLista extends ArrayList<Propriedade> {

	private static final long serialVersionUID = 2270481497021722983L;

	public PropriedadeLista() {
		super();
	}

	public Propriedade pegaPorNome(String nome) {
		for (Propriedade prop : this) {
			if (prop.getNome().equals(nome)) {
				return prop;
			}
		}

		return new Propriedade(nome, "-1");
	}

}
