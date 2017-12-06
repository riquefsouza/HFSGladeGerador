package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.comum.PropriedadeLista;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.comum.StringList;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;

import java.io.IOException;

public class Form {

	private Linguagem linguagem;
	private String nomeProjeto;
	private String classeForm;
	//private StringList formsFilhos;

	public Form(Linguagem linguagem, String nomeProjeto, String classeForm, StringList formsFilhos) {
		this.linguagem = linguagem;
		this.nomeProjeto = nomeProjeto;
		this.classeForm = classeForm;
		//this.formsFilhos = formsFilhos;
	}

	private void montaArquivoPorExtensao(String diretorio, ObjetoBasico janela, 
			ObjetoLista objetos, String extensaoResource, String extensao) throws IOException {		
		montaArquivo(diretorio, janela, objetos, "Form" + extensaoResource,
				classeForm + extensao, true);
	}

	private void montaArquivo(String diretorio, ObjetoBasico janela, 
			ObjetoLista objetos) throws IOException {
		String extensao = linguagem.getRecursoExtensao();
		montaArquivo(diretorio, janela, objetos, "FormHeader" + extensao,
				classeForm + "Gui" + extensao, true);
	}

	private void montaArquivo(String diretorio, ObjetoBasico janela,
			ObjetoLista objetos, String resource, String arquivo, boolean bSubstituir)
			throws IOException {
		StringList sl = new StringList();
		String recurso = Rotinas.RECURSOS_CAMINHO + linguagem.getTipo().toString().toLowerCase()
				+ "/" + resource;
		sl.lerTextoDentroJar(this, recurso);

		if (bSubstituir) {
			Objeto formulario = objetos.get(janela);
			
			PropriedadeLista lista = new PropriedadeLista();
			lista.add(new Propriedade("<nomeProjeto>", nomeProjeto));
			lista.add(new Propriedade("<pacoteProjeto>", nomeProjeto.toLowerCase()));
			lista.add(new Propriedade("<classeForm>", classeForm));
			lista.add(new Propriedade("<objForm>", formulario.getNome()));
			
			lista.add(new Propriedade("<classePai>", formulario.getClasse().substring(3)));
			lista.add(new Propriedade("<construtorRender>", linguagem.getConstrutorRender()));
			
			lista.add(new Propriedade("<classePaiForm>", formulario.getClasseLinguagem(linguagem)));
			lista.add(new Propriedade("<parametrosForm>", linguagem.getParametrosForm(formulario.getClasse())));
			lista.add(new Propriedade("<construtorPaiForm>", linguagem.getConstrutorPaiForm(formulario)));
			/*
			lista.add(new Propriedade("<declaracaoObjetos>", DeclaracaoObjetos.getInstancia()
					.toString(linguagem, objetos, formulario)));
			lista.add(new Propriedade("<declaracaoMetodos>", DeclaracaoMetodos.getInstancia()
					.toString(linguagem, objetos, formulario)));
			if (formulario.isPrincipal()) {
				lista.add(new Propriedade("<declaracaoForms>", linguagem.getDeclaracaoForms(formsFilhos)));	
			} else {
				lista.add(new Propriedade("<declaracaoForms>", ""));
			}
			*/
			lista.add(new Propriedade("<inicializa>", Inicializa.getInstancia()
					.toString(linguagem, classeForm, objetos, formulario)));
			lista.add(new Propriedade("<conectaEventos>", ConectaEventos.getInstancia()
					.toString(linguagem, classeForm, objetos, formulario)));
			//lista.add(new Propriedade("<desconectaEventos>", DesconectaEventos.getInstancia()
			//		.toString(linguagem, objetos, formulario)));
			lista.add(new Propriedade("<metodos>", Metodos.getInstancia()
					.toString(linguagem, objetos, formulario)));			
			lista.add(new Propriedade("<conteudoMostrar>", 
					linguagem.getConteudoMetodoMostrar(classeForm, formulario)));					
			sl.substituir(lista);
		}

		sl.SaveToFile(diretorio + arquivo);
	}

	public void executa(String diretorio, ObjetoBasico janela, ObjetoLista objetos) {
		
		try {

			if (linguagem.isPossuiArquivoHeader()){
				montaArquivoPorExtensao(diretorio, janela, objetos, linguagem.getHeaderExtensao(), linguagem.getHeaderExtensao());				
			}
			if (linguagem.isPossuiFormHeader()){
				montaArquivo(diretorio, janela, objetos);				
			}			

			montaArquivoPorExtensao(diretorio, janela, objetos, linguagem.getRecursoExtensao(), linguagem.getExtensao());
			
		} catch (IOException e) {
			System.err.println(getClass().getName() + " - Salvar Arquivo - "
					+ e.getMessage());
		}
	}
}
