package hfsgladegerador;

import hfsgladegerador.comum.Gtk;
import hfsgladegerador.comum.ObjetoLista;
import hfsgladegerador.comum.PropriedadeLista;
import hfsgladegerador.comum.StringList;
import hfsgladegerador.objetos.Acao;
import hfsgladegerador.objetos.AtributoFilho;
import hfsgladegerador.objetos.ColunaStore;
import hfsgladegerador.objetos.Evento;
import hfsgladegerador.objetos.LinhaStore;
import hfsgladegerador.objetos.Objeto;
import hfsgladegerador.objetos.ObjetoBasico;
import hfsgladegerador.objetos.Propriedade;
import hfsgladegerador.objetos.Requerido;
import hfsgladegerador.objetos.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ArquivoGlade {

	private String arquivo;
	private ArrayList<ObjetoBasico> formularios;
	private ArrayList<Store> lojas;
	private ObjetoLista objetos;
	private Document dom;
	
	public ArquivoGlade(String arquivo){
		this.arquivo = arquivo;
		formularios = new ArrayList<ObjetoBasico>();
		objetos = new ObjetoLista();
		lojas = new ArrayList<Store>();
	}	

	private void parseXmlFile() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(arquivo);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void pegaObjetos(boolean primeiroNivel, String classePrincipal) {
		NodeList nl;
		String nomeTag = "";		
		Element docEle = dom.getDocumentElement();
		int ordemTVC = 0;
		
		if (primeiroNivel)
			nl = docEle.getChildNodes();
		else
			nl = docEle.getElementsByTagName("object");
		
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				nomeTag = nl.item(i).getNodeName();
				
				if (nomeTag.equals("requires")){
					Element el = (Element) nl.item(i);
					
					Requerido req = new Requerido();
					req.setLib(el.getAttribute("lib"));
					req.setVersao(Double.parseDouble(el.getAttribute("version")));
				}
				
				if (nomeTag.equals("object")){								
					Element elPai = (Element) nl.item(i).getParentNode();
					Element el = (Element) nl.item(i);
					
					ordemTVC = montaObjeto(elPai, el, primeiroNivel, classePrincipal, i+1, ordemTVC);
				}
			}
		}
	}

	private int montaObjeto(Element empElPai, Element empEl, boolean primeiroNivel, 
			String classePrincipal, int ordem, int ordemTVC) {
		Node nodePaiAcima = empElPai.getParentNode();
		String nomePaiAcima, nomePai;
		String classe = empEl.getAttribute("class");
		
		if (classe.equals("GtkListStore") || classe.equals("GtkTreeStore")){
			if (primeiroNivel){
				Store loja = new Store();
				loja.setClasse(empEl.getAttribute("class"));
				loja.setId(empEl.getAttribute("id"));
				loja.setTipos(pegaTodosTiposStore(empEl));
				loja.setLinhas(pegaLinhasStore(empEl));
				
				lojas.add(loja);
			}
		} else {
		
			if (primeiroNivel){
				ObjetoBasico basico = pegaObjetoBasico(empEl, primeiroNivel,
						classePrincipal, ordem);
				
				formularios.add(basico);
			} else {
				nomePai = empElPai.getNodeName();
				nomePaiAcima = nodePaiAcima.getNodeName();
				
				Objeto obj = new Objeto();
				obj.setOrdem(ordem);
				obj.setClasse(empEl.getAttribute("class"));
				obj.setId(empEl.getAttribute("id"));
				if (nomePaiAcima.equals("object")){
					Element elPaiAcima = (Element) nodePaiAcima;
					obj.setPai(pegaObjetoBasico(elPaiAcima, primeiroNivel, classePrincipal, ordem));
				}
				obj.setPrimeiroNivel(primeiroNivel);
				obj.setPrincipal(obj.getId().equals(classePrincipal));
				obj.setPropriedades(pegaPropriedades(empEl));
				obj.setEventos(pegaEventos(empEl));				
				
				if (nomePai.equals("child")){
					Element elPai = (Element) empElPai;
					
					AtributoFilho atributoFilho = new AtributoFilho();
					atributoFilho.setTipo(elPai.getAttribute("type"));
					atributoFilho.setInternalChild(elPai.getAttribute("internal-child"));

					obj.setAtributoFilho(atributoFilho);
				}
				obj.setPossuiSubmenu(getSubmenu(empEl));			
				obj.setTodosFilhos(pegaTodosFilhos(empEl, false));
				obj.setFilhos(pegaFilhos(empEl));
				obj.setPacotes(pegaPacotes(empEl));
				obj.setAcoes(pegaAcoes(empEl));
				
				obj.getTodosFilhosInvertidos().addAll(obj.getTodosFilhos());
				Collections.reverse(obj.getTodosFilhosInvertidos());

				
				if (obj.getClasse().equals(Gtk.GtkTreeView.toString())) 
				{
					ordemTVC = 0;
				}				
				else if (obj.getClasse().equals(Gtk.GtkTreeViewColumn.toString()))
				{
					obj.setOrdemTreeViewColumn(ordemTVC);
					ordemTVC++;
				}
				
				objetos.add(obj);
			}
			
		}
		return ordemTVC;
	}

	private ObjetoBasico pegaObjetoBasico(Element empEl, boolean primeiroNivel,
			String classePrincipal, int ordem) {
		ObjetoBasico basico = new ObjetoBasico();
		basico.setOrdem(ordem);
		basico.setClasse(empEl.getAttribute("class"));
		basico.setId(empEl.getAttribute("id"));
		basico.setPrimeiroNivel(primeiroNivel);
		basico.setPrincipal(basico.getId().equals(classePrincipal));
		if (!primeiroNivel){
			basico.setPrimeiroNivel(Gtk.isJanela(basico.getClasse()));
		}
		return basico;
	}

	private boolean getSubmenu(Element ele) {
		boolean submenu = false;
		Element elFilho;
		String valorFilho = "", tipo = "";		
		NodeList nl = ele.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("child")){
					elFilho = (Element) nl.item(i);				
					tipo = elFilho.getAttribute("type");
					if (!tipo.isEmpty()){
						submenu = tipo.equals("submenu");
					}					
				}
			}
		}

		return submenu;
	}
	
	private ArrayList<ObjetoBasico> pegaFilhos(Element el){
		ArrayList<ObjetoBasico> filhos = new ArrayList<ObjetoBasico>();
		Element elFilho;
		String valorFilho = "";		
		NodeList nl = el.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("child")){
					elFilho = (Element) nl.item(i);
					filhos.addAll(pegaTodosFilhos(elFilho, true));
				}
			}
		}
		
		return filhos;
	}
	
	private ArrayList<ObjetoBasico> pegaTodosFilhos(Element el, boolean primeiroNivel){
		ArrayList<ObjetoBasico> filhos = new ArrayList<ObjetoBasico>();
		ObjetoBasico filho;		
		String valorFilho = "";		
		NodeList nl; 
		
		if (primeiroNivel)
			nl = el.getChildNodes();
		else
			nl = el.getElementsByTagName("object");
		
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("object")){
					filho = new ObjetoBasico();
					filho.setClasse(nl.item(i).getAttributes().getNamedItem("class").getNodeValue());
					filho.setId(nl.item(i).getAttributes().getNamedItem("id").getNodeValue());
					
					filhos.add(filho);
				}
			}
		}
		
		return filhos;
	}
	
	private PropriedadeLista pegaPropriedades(Element el) {
		PropriedadeLista props = new PropriedadeLista(); 
		Propriedade propriedade;
		String valorFilho = "";
		NodeList nl = el.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("property")){
					propriedade = new Propriedade();
					
					propriedade.setNome(nl.item(i).getAttributes().getNamedItem("name").getNodeValue());
					propriedade.setValor(nl.item(i).getFirstChild().getNodeValue());
					props.add(propriedade);
				}
			}
		}
		
		return props;
	}

	private ArrayList<Evento> pegaEventos(Element el) {
		ArrayList<Evento> eventos = new ArrayList<Evento>(); 
		Evento evento;
		String valorFilho = "";
		NodeList nl = el.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("signal")){
					evento = new Evento();
					
					evento.setNome(nl.item(i).getAttributes().getNamedItem("name").getNodeValue());
					evento.setManipulador(nl.item(i).getAttributes().getNamedItem("handler").getNodeValue());
					eventos.add(evento);
				}
			}
		}
		
		return eventos;
	}
	
	private PropriedadeLista pegaPacotes(Element el){
		PropriedadeLista pacotes = new PropriedadeLista();
		Element elPacote;
		String valorPacote = "";
		Node n1, n2;
		NodeList nl = el.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				n1 = el.getNextSibling();
				n2 = n1.getNextSibling();
				
				if (n2!=null) {				
					valorPacote = n2.getNodeName();
					if (valorPacote.equals("packing")){
						elPacote = (Element) n2;
						pacotes.addAll(pegaPropriedades(elPacote));
						break;
					}
				}
			}
		}
		
		return pacotes;
	}

	private ArrayList<Acao> pegaTodasAcoes(Element el) {
		ArrayList<Acao> acoes = new ArrayList<Acao>();
		Acao acao;		
		String valorFilho = "";
		NodeList nl = el.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("action-widget")){
					acao = new Acao();
					
					acao.setResponse(nl.item(i).getAttributes().getNamedItem("response").getNodeValue());
					acao.setValor(nl.item(i).getFirstChild().getNodeValue());
					acoes.add(acao);
				}
			}
		}
		
		return acoes;
	}
	
	private ArrayList<Acao> pegaAcoes(Element el){
		ArrayList<Acao> acoes = new ArrayList<Acao>();
		Element acao;		
		String valorFilho = ""; 	
		NodeList nl = el.getChildNodes();		
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("action-widgets")){
					acao = (Element) nl.item(i);
					acoes.addAll(pegaTodasAcoes(acao));					
				}
			}
		}
		
		return acoes;
	}	

	private ArrayList<String> pegaTodosTiposStore(Element el){
		ArrayList<String> tipos = new ArrayList<String>();
		String tipo = "";		
		String valorFilho = "", valorCol = ""; 
		Node type, colunas, col;		
		NodeList nl = el.getChildNodes();		
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				colunas = nl.item(i);
				valorFilho = colunas.getNodeName();
				
				if (valorFilho.equals("columns")){										
					col = nl.item(i);
					NodeList nlCol = col.getChildNodes();

					if (nlCol != null && nlCol.getLength() > 0) {
						for (int j = 0; j < nlCol.getLength(); j++) {
							valorCol = nlCol.item(j).getNodeName();
					
							if (valorCol.equals("column")){
								type = nlCol.item(j).getAttributes().getNamedItem("type");
								if (type!=null){
									tipo = type.getNodeValue();
									tipos.add(tipo);
								}
							}
						}
					}
					
				}
			}
		}
		
		return tipos;
	}	
	
	private ArrayList<LinhaStore> pegaLinhasStore(Element el){
		ArrayList<LinhaStore> linhas = new ArrayList<LinhaStore>();
		Element elFilho;		
		String valorFilho = "";			
		NodeList nl = el.getChildNodes();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				valorFilho = nl.item(i).getNodeName();
				
				if (valorFilho.equals("data")){
					elFilho = (Element) nl.item(i);
					linhas.addAll(pegaTodasLinhasStore(elFilho));
				}
			}
		}
		
		return linhas;
	}	
	
	private ArrayList<LinhaStore> pegaTodasLinhasStore(Element el){
		ArrayList<LinhaStore> linhas = new ArrayList<LinhaStore>();
		LinhaStore linha;
		int numlinha = 0;
		String valorFilho = ""; 		
		Node nlinhas, nlinha;
		NodeList nl = el.getChildNodes();		
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				nlinhas = nl.item(i);
				valorFilho = nlinhas.getNodeName();
				
				if (valorFilho.equals("row")){
					numlinha++;
					
					nlinha = nl.item(i);
					linha = new LinhaStore();
					linha.setLinha(numlinha);
					linha.setColunas(pegaTodasColunasStore(numlinha, nlinha));
					
					linhas.add(linha);
				}				
			}
		}
		
		return linhas;
	}

	public ArrayList<ColunaStore> pegaTodasColunasStore(int numlinha, Node linha) {
		ArrayList<ColunaStore> colunas = new ArrayList<ColunaStore>();
		ColunaStore coluna;
		String valorCol = "";
		Node id;
		Node traduzivel;
		NodeList nlLinha = linha.getChildNodes();					
				
		if (nlLinha != null && nlLinha.getLength() > 0) {
			for (int j = 0; j < nlLinha.getLength(); j++) {
				valorCol = nlLinha.item(j).getNodeName();
				
				if (valorCol.equals("col")){					
		
					coluna = new ColunaStore();
					
					coluna.setLinha(numlinha);
					
					id = nlLinha.item(j).getAttributes().getNamedItem("id");
					if (id!=null){
						coluna.setId(id.getNodeValue());
					}

					traduzivel = nlLinha.item(j).getAttributes().getNamedItem("translatable");
					if (traduzivel!=null){						
						coluna.setTraduzivel(traduzivel.getNodeValue().equals("yes"));
					}
					
					coluna.setValor(nlLinha.item(j).getFirstChild().getNodeValue());

					colunas.add(coluna);
				}
			}
		}
		
		return colunas;
	}	
	
	public void imprime(boolean bFormularios, boolean bLojas) {

		if (bFormularios){
			System.out.println("Formulario Qtd: " + formularios.size());
			
			Iterator<ObjetoBasico> it = formularios.iterator();
			while (it.hasNext()) {
				System.out.println(it.next().toString());
				System.out.println();
			}			
		} else {
			System.out.println("Objeto Qtd: " + objetos.size());
	
			Iterator<Objeto> it = objetos.iterator();
			while (it.hasNext()) {
				System.out.println(it.next().toString());
				System.out.println();
			}
		}
		
		if (bLojas){
			System.out.println("Loja Qtd: " + lojas.size());
			
			Iterator<Store> it2 = lojas.iterator();
			while (it2.hasNext()) {
				System.out.println(it2.next().toString());
				System.out.println();
			}			
		}		
	}
	
	public ObjetoLista getObjetos() {
		return objetos;
	}
	
	public ArrayList<ObjetoBasico> getFormularios() {
		return formularios;
	}

	public StringList getFormsFilhos(String classePrincipal) {
		StringList listaFilhos = new StringList();
		
		for (ObjetoBasico obj : formularios) {
			if (!obj.getId().equals(classePrincipal)){
				listaFilhos.add(obj.getId());
			}
		}
		
		return listaFilhos;
	}
	
	public ArrayList<Store> getLojas() {
		return lojas;
	}

	public boolean executa(String classePrincipal) {
		parseXmlFile();
		pegaObjetos(true, classePrincipal);
		pegaObjetos(false, classePrincipal);
		return (objetos.size() > 0 && formularios.size() > 0);
	}
	
}
