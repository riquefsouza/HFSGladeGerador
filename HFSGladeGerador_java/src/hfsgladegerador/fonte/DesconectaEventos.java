package hfsgladegerador.fonte;


public class DesconectaEventos {

	private static DesconectaEventos instancia;

	private DesconectaEventos() {
		super();
	}

	public static DesconectaEventos getInstancia() {
		if (instancia == null) {
			instancia = new DesconectaEventos();
		}
		return instancia;
	}
/*
	public String toString(Linguagem linguagem, ObjetoLista objetos,
			Objeto formulario) {
		String ret = "";
		boolean tab = false;
		Objeto filho;

		if (formulario != null) {
			for (ObjetoBasico basico : formulario.getTodosFilhos()) {
				filho = objetos.get(basico);

				// eventos do filho
				for (Evento evento : filho.getEventos()) {
					ret = linguagem.getDesconectaEventoFilho(ret, tab, filho,
							evento);
					tab = true;
				}
			}

			// eventos do formulario
			for (Evento evento : formulario.getEventos()) {
				ret = linguagem.getDesconectaEventoFormulario(ret,
						tab, evento);
			}

		}

		return ret;
	}
	*/
}
