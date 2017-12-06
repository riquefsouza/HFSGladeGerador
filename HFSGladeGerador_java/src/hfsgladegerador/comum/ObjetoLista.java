package hfsgladegerador.comum;

import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;

import java.util.ArrayList;

public class ObjetoLista extends ArrayList<Objeto> {

	private static final long serialVersionUID = 3410848805451734310L;

	public ObjetoLista() {
		super();
	}
	
	public Objeto get(ObjetoBasico basico) {
		Objeto objeto = new Objeto();

		for (Objeto obj : this) {
			if (//obj.getOrdem() == basico.getOrdem() &&
					obj.getId().equals(basico.getId())
					&& obj.getClasse().equals(basico.getClasse())) {
				objeto = obj;
				break;
			}
		}

		return objeto;
	}

	public Objeto pegaPorId(String id) {
		for (Objeto obj : this) {
			if (obj.getId().equals(id)) {
				return obj;
			}
		}

		return null;
	}
}
