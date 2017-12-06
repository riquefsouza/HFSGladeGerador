package hfsgladegerador;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.comum.StringList;
import hfsgladegerador.fonte.Form;
import hfsgladegerador.fonte.LojaFonte;
import hfsgladegerador.fonte.Principal;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class Projeto {
	
	private String nome;
	private String dirRaiz;
	private File dirProjeto;
	private String classePrincipal;
	private String objPrincipal;	

	public Projeto(String dirRaiz, String nome,
			String classePrincipal) {
		this.dirRaiz = dirRaiz;
		this.nome = nome;
		this.dirProjeto = new File(dirRaiz + "projetos");
		this.classePrincipal = classePrincipal;
		this.objPrincipal = Rotinas.uncapitalize(classePrincipal);	
	}

	public void limparDiretorio() throws IOException {
		if (dirProjeto.exists()) {
			FileUtils.cleanDirectory(dirProjeto);
			dirProjeto.delete();
		}		
	}
	
	public void executa(ArrayList<ObjetoBasico> formularios, StringList formsFilhos, 
			ObjetoLista objetos, ArrayList<Store> lojas, Linguagem linguagem) {
		Principal principal;
		Form form;
		LojaFonte lojaf;
		File dirLinguagem = new File(dirRaiz + "projetos" + File.separator
				+ linguagem.getTipo().toString() + "_" + linguagem.getVersao().toString());		
		
		dirLinguagem.mkdirs();

		if (dirLinguagem.exists()) {
			principal = new Principal(linguagem, nome, classePrincipal, objPrincipal);
			principal.executa(dirLinguagem.getPath() + File.separator, objetos);

			for (ObjetoBasico janela : formularios) {								
				if (janela.isJanela() || janela.isPopupMenu()){
					form = new Form(linguagem, nome, janela.getId(), formsFilhos);
					form.executa(dirLinguagem.getPath() + File.separator, janela, objetos);
				}
			}
			
			lojaf = new LojaFonte(linguagem, nome, classePrincipal, objPrincipal);
			lojaf.executa(dirLinguagem.getPath() + File.separator, lojas);
		}
	}
}
