package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.comum.PropriedadeLista;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.comum.StringList;
import hfsgladegerador.objetos.Propriedade;

import java.io.IOException;

public class Principal {

	private Linguagem linguagem;
	private String nomeProjeto;
	private String classePrincipal;
	private String objPrincipal;

	public Principal(Linguagem linguagem, String nomeProjeto,
			String classePrincipal, String objPrincipal) {
		this.linguagem = linguagem;
		this.nomeProjeto = nomeProjeto;
		this.classePrincipal = classePrincipal;
		this.objPrincipal = objPrincipal;
	}

	private void copiaArquivo(String diretorio, ObjetoLista objetos,
			String arquivo) throws IOException {
		montaArquivo(diretorio, objetos, arquivo, arquivo, false);
	}

	private void montaArquivoPorExtensao(String diretorio, ObjetoLista objetos, 
			String extensaoResource, String extensao) throws IOException {
		montaArquivo(diretorio, objetos, "Main" + extensaoResource, nomeProjeto + extensao, true);
	}
	
	private void montaArquivo(String diretorio, ObjetoLista objetos, 
			String resource, String arquivo, boolean bSubstituir) throws IOException {
		StringList sl = new StringList();
		String recurso = Rotinas.RECURSOS_CAMINHO +	linguagem.getTipo().toString().toLowerCase() 
				+ "/" + resource;
		sl.lerTextoDentroJar(this, recurso);

		if (bSubstituir) {
			PropriedadeLista lista = new PropriedadeLista();
			lista.add(new Propriedade("<nomeProjeto>", nomeProjeto));
			lista.add(new Propriedade("<pacoteProjeto>", nomeProjeto.toLowerCase()));
			lista.add(new Propriedade("<classePrincipal>", classePrincipal));
			lista.add(new Propriedade("<objPrincipal>", objPrincipal));				
			sl.substituir(lista);
		}
		
		sl.SaveToFile(diretorio + arquivo);
	}
	
	public void executa(String diretorio, ObjetoLista objetos) {

		try {

			if (linguagem.isPossuiArquivoResource()){
				copiaArquivo(diretorio, objetos, "resource.h");
			}
			if (linguagem.isPossuiArquivoHeader()){
				montaArquivoPorExtensao(diretorio, objetos, linguagem.getHeaderExtensao(), linguagem.getHeaderExtensao());				
			}
			
			montaArquivoPorExtensao(diretorio, objetos, linguagem.getRecursoExtensao(), linguagem.getExtensao());
			
		} catch (IOException e) {
			System.err.println(getClass().getName() + " - Salvar Arquivo - "
					+ e.getMessage());
		}
	}

	public String getClassePrincipal() {
		return classePrincipal;
	}

	public String getObjPrincipal() {
		return objPrincipal;
	}
}
