package hfsgladegerador;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.comum.StringList;
/*
import hfsgladegerador.linguagens.LinguagemAda;
import hfsgladegerador.linguagens.LinguagemAdaGtk2;
import hfsgladegerador.linguagens.LinguagemC;
import hfsgladegerador.linguagens.LinguagemCPP;
import hfsgladegerador.linguagens.LinguagemCSharp;
import hfsgladegerador.linguagens.LinguagemJava;
*/
import hfsgladegerador.linguagens.LinguagemPython;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class HFSGladeGerador {
	
	public static void main(String[] args) {
		File dirCorrente = new File (".");
		try {
			String classePrincipal = "FrmPrincipal";
			String dir = dirCorrente.getCanonicalPath()+File.separator;
			Projeto proj = new Projeto(dir, "HFSGuardaDiretorio", classePrincipal);
			
			String arquivoGlade = "C:/andarDIR/HFSGuardaDiretorio_Glade.glade";
			ArquivoGlade arqg = new ArquivoGlade(arquivoGlade);
			if (arqg.executa(classePrincipal)) {
				ObjetoLista objetos = arqg.getObjetos();
				ArrayList<ObjetoBasico> formularios = arqg.getFormularios();
				StringList formsFilhos = arqg.getFormsFilhos(classePrincipal);
				ArrayList<Store> lojas = arqg.getLojas();
				
//				arqg.imprime(false, false);
							
				proj.limparDiretorio();
				
				ArrayList<Linguagem> linguaLista = new ArrayList<Linguagem>();
				//linguaLista.add(new LinguagemC());
				//linguaLista.add(new LinguagemCPP());
				//linguaLista.add(new LinguagemCSharp());
				//linguaLista.add(new LinguagemJava());				
				//linguaLista.add(new LinguagemAda());
				//linguaLista.add(new LinguagemAdaGtk2());
				linguaLista.add(new LinguagemPython());

				for (Linguagem lingua : linguaLista) {
					proj.executa(formularios, formsFilhos, objetos, lojas, lingua);	
				}

			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}

}
