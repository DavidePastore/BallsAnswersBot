/**
 * 
 */
package org.altervista.ballsanswers.utils;

import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author <a reef="https://github.com/DavidePastore">DavidePastore</a>
 *
 */
public class Utils {
	
	private static final String categorie[] = {
		"Auto_e_trasporti",
		"Casa_e_giardino",
		"Cucina",
		"Elettronica",
		"Giochi_e_passatempi",
		"Matematica_e_scienze",
		"Musica",
		"Programmazione",
		"Altro"
	};
	
	public static String generaTitolo(){
		return generateRandomString(30);
	}
	
	
	public static String generaTesto(){
		return generateRandomString(1000);
	}
	
	public static String generaCategoria(){
		Random random = new Random();
		return categorie[random.nextInt(categorie.length)];
	}
	
	/**
	 * Genera una stringa random.
	 * @param lunghezza della stringa da generare.
	 * @return Restituisce una stringa random.
	 */
	public static String generateRandomString(int lunghezza){
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < lunghezza; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}


	/**
	 * Leggi un titolo
	 * @return il titolo letto
	 */
	public static String leggiTitolo() {
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(leggiCategoria());
			HttpResponse httpResponse = httpClient.execute(httpGet);
	        String stringResponse = EntityUtils.toString(httpResponse.getEntity());
	        
	        Document doc = Jsoup.parse(stringResponse);
	        doc.setBaseUri("http://ballsanswers.altervista.org/index.php");
	        Elements elements = doc.select("div#wrapper div#page div#content div.title div a");
	        Random random = new Random();
	        Element element = elements.get(random.nextInt(elements.size()));
	        String href =  element.attr("href");
	        
	        int position = href.indexOf("=");
	        href = href.substring(position+1);
	        //System.out.println("Il titolo trovato è: "+href);
	        return href;
		} catch(Exception ex){
			System.out.println("Errore nella lettura del titolo: "+ex);
		}
		
		return null;
	}
	
	/**
	 * Legge una categoria.
	 * @return ritorna una categoria
	 */
	private static String leggiCategoria(){
		try{
			//Leggo le categorie
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://ballsanswers.altervista.org/index.php?titolo=categorie");
			HttpResponse httpResponse = httpClient.execute(httpGet);
	        String stringResponse = EntityUtils.toString(httpResponse.getEntity());
	        
	        Document doc = Jsoup.parse(stringResponse);
	        Elements elements = doc.select("div#wrapper div#page div#content div.title a");
	        Random random = new Random();
	        Element element = elements.get(random.nextInt(elements.size()));
	        return "http://ballsanswers.altervista.org/index.php"+element.attr("href");
		} catch(Exception ex){
			System.out.println("Errore nella lettura della categoria: "+ex);
		}
		
		return null;
	}
	
	
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}
	
	
	/**
	   * Gets the String that is nested in between two Strings.
	   * Only the first match is returned.
	   *
	   * A <code>null</code> input String returns <code>null</code>.
	   * A <code>null</code> open/close returns <code>null</code> (no match).
	   * An empty ("") open and close returns an empty string.
	   *
	   * <pre>
	   * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
	   * StringUtils.substringBetween(null, *, *)          = null
	   * StringUtils.substringBetween(*, null, *)          = null
	   * StringUtils.substringBetween(*, *, null)          = null
	   * StringUtils.substringBetween("", "", "")          = ""
	   * StringUtils.substringBetween("", "", "]")         = null
	   * StringUtils.substringBetween("", "[", "]")        = null
	   * StringUtils.substringBetween("yabcz", "", "")     = ""
	   * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
	   * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
	   * </pre>
	   *
	   * @param str  the String containing the substring, may be null
	   * @param open  the String before the substring, may be null
	   * @param close  the String after the substring, may be null
	   * @return the substring, <code>null</code> if no match
	   * @since 2.0
	   */
	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
      	}
      	int start = str.indexOf(open);
      	if (start != -1) {
          	int end = str.indexOf(close, start + open.length());
          	if (end != -1) {
        	  	return str.substring(start + open.length(), end);
          	}
      	}
      	return null;
	}

}
