package hfsgladegerador.comum;

import hfsgladegerador.objetos.Propriedade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe que representa uma lista de strings em memoria.
 *
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 01/10/2007
 */
public class StringList extends ArrayList<String> {

    private static final long serialVersionUID = 7733886264192899676L;

    private static final String separador = System.getProperty("line.separator");
    
    public StringList() {
        super();        
    }

    public StringList(int capacidadeInicial) {
        super(capacidadeInicial);
    }

    public StringList(String str, char separador) {
        super();

        if (str.indexOf(separador) > 0) {
            char[] partes = str.toCharArray();
            String pedaco = "";
            for (int i = 0; i < partes.length; i++) {
                pedaco += partes[i];
                if (partes[i] == separador) {
                    super.add(pedaco.substring(0, pedaco.length() - 1));
                    pedaco = "";
                }
            }
            super.add(pedaco);
        } else {
        	super.add(str);
        }
    }
    
    public StringList(String texto) {
        super();
        
        String[] pedacos = texto.split(separador);
        super.addAll(Arrays.asList(pedacos));
    }
    
    public String toString() {
		StringBuffer ret = new StringBuffer();		
		
        for (int i = 0; i < super.size(); i++) {
            ret.append(this.get(i)+separador);
        }

        return ret.toString();
    }

    public String[] toStringArray() {
        return (String[]) super.toArray(new String[super.size()]);
    }

    public void LoadFromFile(String arquivo) throws IOException {
        BufferedReader in;
        String str;

        in = new BufferedReader(new FileReader(arquivo));
        while ((str = in.readLine()) != null) {
            this.add(str);
        }
        in.close();
    }

    public void SaveToFile(String arquivo)
            throws IOException {
        BufferedWriter out;

        out = new BufferedWriter(new FileWriter(arquivo, true));
        for (String linha : this) {
            out.write(linha);
            out.newLine();
            out.flush();
        }
        out.close();
    }

    private InputStream getTextoDentroJar(Object obj, String str) {
        return obj.getClass().getResourceAsStream(str);
    }
    
    public void lerTextoDentroJar(Object obj, String str) throws IOException {
        String thisLine;
        InputStream is = getTextoDentroJar(obj, str);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((thisLine = br.readLine()) != null) {
            this.add(thisLine);
        }
    }

    public void substituir(PropriedadeLista lista){
    	String texto = this.toString(); 
    	
    	for (Propriedade prop : lista) {
			texto = texto.replaceAll(prop.getNome(), prop.getValor());
		}
    	
        String[] pedacos = texto.split(separador);
        clear();
        addAll(Arrays.asList(pedacos));
    }

    public void substituir(Propriedade prop){
    	String texto = this.toString(); 
    	
    	texto = texto.replaceAll(prop.getNome(), prop.getValor());
    	
        String[] pedacos = texto.split(separador);
        clear();
        addAll(Arrays.asList(pedacos));
    }
}
