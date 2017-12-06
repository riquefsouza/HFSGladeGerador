package hfsgladegerador.comum;

import hfsgladegerador.comum.ILinguagem.Tipo;
import hfsgladegerador.comum.ILinguagem.Versao;

import java.text.Normalizer;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Rotinas {

	public static final String SEPARADOR_LINHA = System
			.getProperty("line.separator");

	public static final String RECURSOS_CAMINHO = "/hfsgladegerador/recursos/";

	public static String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1).toLowerCase();
	}
	
	public static String capitalize(String nome, char separador) {
		String ret = "";
		if (nome.trim().length() > 0) {
			StringList lista = new StringList(nome, separador);
			for (String item : lista) {
				ret += Character.toString(separador) + Rotinas.capitalize(item);
			}
			ret = ret.substring(1);
		}
		return ret;
	}

	public static String uncapitalize(final String line) {
		return Character.toLowerCase(line.charAt(0)) + line.substring(1);
	}

	public static String juntar(String nome, char separador, boolean bCapitalize) {
		String ret = "";
		StringList lista = new StringList(nome, separador);
		for (String item : lista) {
			if (bCapitalize)
				ret += Rotinas.capitalize(item);
			else
				ret += item;
		}
		return ret;
	}

	public static String getRecurso(String chave, Versao versao, Tipo tipo) {
		Locale localidade = new Locale("pt", "BR");
		return ResourceBundle.getBundle("hfsgladegerador/recursos/linguagem",
				localidade).getString(chave + "." + versao.toString().toLowerCase() 
						+ "." + tipo.toString().toLowerCase());
	}

	public static String getRecurso(String chave, String nomePropriedade, Tipo tipo) {
		Locale localidade = new Locale("pt", "BR");
		return ResourceBundle.getBundle("hfsgladegerador/recursos/linguagem",
				localidade).getString(chave + "." + nomePropriedade 
						+ "." + tipo.toString().toLowerCase());
	}

	public static String getRecurso(String chave, Tipo tipo) {
		Locale localidade = new Locale("pt", "BR");
		return ResourceBundle.getBundle("hfsgladegerador/recursos/linguagem",
				localidade).getString(chave + "." + tipo.toString().toLowerCase());
	}
	
	public static String getPropriedadeClasse(String propriedade, String filhoClasse) {
		String classes = getPropriedadeClasses(propriedade);
		if (!classes.isEmpty()){
			if (classes.indexOf("|") > 0){
				StringList lista = new StringList(classes, '|'); 				
				if (lista.size() > 0){
					for (String propClasse : lista) {
						if (propClasse.equals(filhoClasse)){
							return propClasse;
						}
					}
				} else {
					return classes;
				}
			} else {
				return classes;
			}
		}
		return "";
	}

	private static String getPropriedadeClasses(String propriedade) {
		Locale localidade = new Locale("pt", "BR");
		try {
			return ResourceBundle.getBundle(
					"hfsgladegerador/recursos/PropriedadeClasse", localidade)
					.getString(propriedade);			
		} catch (MissingResourceException e) {
			return "";
		}
	}

	public static String separar(String nome, char separador, boolean bMaiusculo) {
		boolean bCondicao;
		char[] partes;
		String pedaco = "";
		
		if (nome.trim().length() > 0) {
			partes = nome.toCharArray();
			
			for (int i = 0; i < partes.length; i++) {
				if (bMaiusculo) {
					bCondicao = Character.isUpperCase(partes[i]);
				} else {
					bCondicao = Character.isLowerCase(partes[i]);
				}
				
				if (bCondicao) {
					pedaco += Character.toString(separador) + partes[i];
				} else {
					pedaco += partes[i];
				}
			}
			pedaco = pedaco.substring(1);
		}
		return pedaco;
	}
	
	public static String getEvento(Tipo tipo, Versao versao, 
			String eventoNome, boolean bClasseParam) {
		String sEvento = getEventos(tipo, versao, eventoNome);
		if (!sEvento.isEmpty()){
			if (sEvento.indexOf("|") > 0){
				StringList lista = new StringList(sEvento, '|'); 				
				if (lista.size() > 0){
					if (bClasseParam) {
						return lista.get(0);
					} else {
						return lista.get(1);
					}
				} else {
					return sEvento;
				}
			} else {
				return sEvento;
			}
		}
		return "";
	}

	private static String getEventos(Tipo tipo, Versao versao, String evento) {
		Locale localidade = new Locale("pt", "BR");
		try {
			return ResourceBundle.getBundle(
					"hfsgladegerador/recursos/linguagemEventos", localidade)
					.getString(tipo.toString().toLowerCase() + "." + 
					versao.toString().toLowerCase() + "." + evento);			
		} catch (MissingResourceException e) {
			return "";
		}
	}
	
	public static boolean possuiAcentos(String str) {
		  str = Normalizer.normalize(str, Normalizer.Form.NFD);
		  //str = str.replaceAll("[^\\p{ASCII}]", "");
		  return Pattern.compile("[^\\p{ASCII}]").matcher(str).find();	 
	}

}
