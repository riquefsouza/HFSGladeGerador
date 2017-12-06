package hfsgladegerador.fonte;

import hfsgladegerador.comum.Linguagem;
import hfsgladegerador.comum.PropriedadeLista;
import hfsgladegerador.comum.Rotinas;
import hfsgladegerador.comum.StringList;
import hfsgladegerador.objetos.ColunaStore;
import hfsgladegerador.objetos.LinhaStore;
import hfsgladegerador.objetos.Propriedade;
import hfsgladegerador.objetos.Store;

import java.io.IOException;
import java.util.ArrayList;

public class LojaFonte {

	private Linguagem linguagem;
	private String nomeProjeto;
	private String classePrincipal;
	private String objPrincipal;	

	public LojaFonte(Linguagem linguagem, String nomeProjeto,
			String classePrincipal, String objPrincipal) {
		this.linguagem = linguagem;
		this.nomeProjeto = nomeProjeto;
		this.classePrincipal = classePrincipal;
		this.objPrincipal = objPrincipal;		
	}
	
	private void montaRotinas(String diretorio, ArrayList<Store> lojas, 
			String extensaoResource, String extensao) throws IOException {		
		StringList sl = new StringList();
		String recurso = Rotinas.RECURSOS_CAMINHO
				+ linguagem.getTipo().toString().toLowerCase() + "/Rotinas" + extensaoResource;
		sl.lerTextoDentroJar(this, recurso);

		String texto = "", colunas = "";
		for (Store loja : lojas) {
			texto += montaArquivoLoja(loja, extensaoResource);
			colunas += montaDeclaracaoColunas(loja) + Rotinas.SEPARADOR_LINHA;
		}

		PropriedadeLista lista = new PropriedadeLista();
		lista.add(new Propriedade("<nomeProjeto>", nomeProjeto));
		lista.add(new Propriedade("<pacoteProjeto>", nomeProjeto.toLowerCase()));
		lista.add(new Propriedade("<declaracaoObjetos>", colunas));	
		lista.add(new Propriedade("<classePrincipal>", classePrincipal));
		lista.add(new Propriedade("<objPrincipal>", objPrincipal));
		lista.add(new Propriedade("<MetodosObjetos>", texto));
		sl.substituir(lista);

		sl.SaveToFile(diretorio + "Rotinas" + extensao);
	}	

	private String montaArquivoLoja(Store loja, String extensaoResource) throws IOException {
		
		StringList sl = new StringList();
		String recurso = Rotinas.RECURSOS_CAMINHO
				+ linguagem.getTipo().toString().toLowerCase() + "/Loja" + extensaoResource;
		sl.lerTextoDentroJar(this, recurso);

		PropriedadeLista lista = new PropriedadeLista();
		lista = montaLoja(loja);
		
		sl.substituir(lista);
		
		return sl.toString();
	}
	
	private PropriedadeLista montaLoja(Store loja) 
			throws IOException {
		PropriedadeLista lista = new PropriedadeLista();
		lista.add(new Propriedade("<declaracaoClasseStore>", loja
				.getClasse()));		
		lista.add(new Propriedade("<classeStore>", loja
				.getClasseLinguagem(linguagem)));
		lista.add(new Propriedade("<objStore>", loja.getId()));
		lista.add(new Propriedade("<objStore2>",  linguagem.getCastStore(loja.getId())));
		String sQtdTipos = Integer.toString(loja.getTipos().size());
		lista.add(new Propriedade("<qtdTipos>", sQtdTipos));

		montaTiposLoja(loja, lista);
		montaValoresLoja(loja, lista);
		
		return lista;
	}

	private String montaDeclaracaoColunas(Store loja) {
		boolean bPrimeiro = false;
		int nCols = 0;
		String sNomeColuna = "", declaraColuna = "";
		for (String tipo : loja.getTipos()) {
			sNomeColuna = "coluna" + nCols;
			
			declaraColuna += linguagem.getDeclaraColunaStore(bPrimeiro, loja.getId(), tipo, sNomeColuna);
		
			bPrimeiro = true;
			nCols++;
		}
		return declaraColuna;
	}
	
	
	private void montaTiposLoja(Store loja, PropriedadeLista lista) {
		boolean bPrimeiro = false;
		int nCols = 0;
		String tiposStore = "", addTiposStore = "", sNomeColuna = "";
		for (String tipo : loja.getTipos()) {
			sNomeColuna = "coluna" + nCols;
			
			if (bPrimeiro) {
				if (linguagem.getTipo().equals(Linguagem.Tipo.CPP))
					tiposStore += "\n\t\t";
				else
					tiposStore += ", ";
			}
			tiposStore += linguagem.getTipoStore(bPrimeiro, loja.getId(), tipo, sNomeColuna);
			
			addTiposStore += "add("+sNomeColuna+");";
			if (nCols < loja.getTipos().size()){
				addTiposStore += "\n\t\t\t\t\t";
			}
			
			bPrimeiro = true;
			nCols++;
		}
		lista.add(new Propriedade("<tiposStore>", tiposStore));
		lista.add(new Propriedade("<addTiposStore>", addTiposStore));
	}

	private void montaValoresLoja(Store loja, PropriedadeLista lista) {
		boolean bPrimeiro;
		String sQtdColunas;
		int nCols;
		boolean tab = false;
		String sNomeColuna;
		String StoreAddValores = "";
		for (LinhaStore linha : loja.getLinhas()) {
			
			nCols = 0;
			String valoresStore = "";
			bPrimeiro = false;
			for (ColunaStore col : linha.getColunas()) {
				sNomeColuna = "coluna" + nCols;
				
				if (bPrimeiro) {
					if (!linguagem.getTipo().equals(Linguagem.Tipo.CPP) &&
							!linguagem.getTipo().equals(Linguagem.Tipo.Java) &&
								!linguagem.getTipo().equals(Linguagem.Tipo.Ada))
						valoresStore += ", ";
				}
				valoresStore += linguagem.getValorColunaStore(tab, bPrimeiro, col, loja.getId(), nCols, sNomeColuna);
				bPrimeiro = true;
				nCols++;
			}

			sQtdColunas = Integer.toString(linha.getColunas().size());
			StoreAddValores = linguagem.getStoreAddValores(StoreAddValores, tab, 
					loja.getId(), sQtdColunas, valoresStore);
			tab = true;
		}
		lista.add(new Propriedade("<StoreAddValores>", StoreAddValores));
	}

	public void executa(String diretorio, ArrayList<Store> lojas) {

		try {
			if (linguagem.isPossuiArquivoHeader()){
				montaRotinas(diretorio, lojas, linguagem.getHeaderExtensao(), linguagem.getHeaderExtensao());
			}
			
			montaRotinas(diretorio, lojas, linguagem.getRecursoExtensao(), linguagem.getExtensao());
		} catch (IOException e) {
			System.err.println(getClass().getName() + " - Salvar Arquivo - "
					+ e.getMessage());
		}
	}

}
