/**
 * 
 */
package org.altervista.ballsanswers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.altervista.ballsanswers.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


/**
 * Classe dell'utente.
 * @author <a reef="https://github.com/DavidePastore">DavidePastore</a>
 *
 */
public class Utente {
	
	private String username;
	private String password;
	
	//Per le richieste
	private HttpClient httpclient = new DefaultHttpClient();
    private HttpContext httpContext = new BasicHttpContext();
    private HttpResponse httpResponse;
    private HttpGet httpGet;
    private HttpPost httpPost;
    private CookieStore cookieStore;
    private String stringResponse;
    
    //Indirizzi
    private static final String INDIRIZZO_LOGIN = "http://ballsanswers.altervista.org/index.php";
    private static final String INDIRIZZO_CHIEDI = "http://ballsanswers.altervista.org/index.php?titolo=chiedi";
    private static final String INDIRIZZO_RISPOSTA = "http://ballsanswers.altervista.org/index.php?testoR=%s&hid_risp1=1&titolo_dom=%s&risposta=Invia";
    private static final String INDIRIZZO_VOTO = "http://ballsanswers.altervista.org/index.php?votato=%s&titolo_dom=%s";
    
    /**
     * Costruttore
     * @param username username
     * @param password password
     */
	public Utente(String username, String password){
		this.username = username;
		this.password = password;
		
		//Inizializzo il cookie store
		cookieStore = new BasicCookieStore();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}
	
	/**
	 * Effettua il login.
	 * @return login effettuato correttamente?
	 */
	public void login(){
		try{
			httpPost = new HttpPost(INDIRIZZO_LOGIN);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	        formparams.add(new BasicNameValuePair("accedi", "true"));
	        formparams.add(new BasicNameValuePair("login", "accedi"));
	        formparams.add(new BasicNameValuePair("password", password));
	        formparams.add(new BasicNameValuePair("username", username));
	        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
	        httpPost.setEntity(entity);
	        httpResponse = httpclient.execute(httpPost, httpContext);
	        stringResponse = EntityUtils.toString(httpResponse.getEntity());
	        
	        if(stringResponse.contains("Username o password errati!")){
				System.out.println("Credenziali errate!");
			}
			else{
				System.out.println("Login corretto!");
			}
	        
		} catch(Exception ex){
			System.out.println("Errore in login: "+ex);
		}
	}
	
	/**
	 * Crea una nuova domanda.
	 */
	public void chiedi(){
		try{
			httpPost = new HttpPost(INDIRIZZO_CHIEDI);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	        formparams.add(new BasicNameValuePair("categoria", Utils.generaCategoria()));
	        formparams.add(new BasicNameValuePair("invia_domanda", "Invia"));
	        formparams.add(new BasicNameValuePair("testo", Utils.generaTesto()));
	        formparams.add(new BasicNameValuePair("titolo", Utils.generaTitolo()));
	        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
	        httpPost.setEntity(entity);
	        httpResponse = httpclient.execute(httpPost, httpContext);
	        stringResponse = EntityUtils.toString(httpResponse.getEntity());
	        
	        if(stringResponse.contains("'Domanda creata con successo!'")){
				System.out.println("Domanda inviata!");
			}
			else{
				System.out.println("Errore invio domanda!");
			}
	        
		} catch(Exception ex){
			System.out.println("Errore in domanda: "+ex);
		}
	}
	
	/**
	 * Invia una risposta ad una domanda.
	 */
	public void rispondi(){
		try{
			String indirizzo = String.format(INDIRIZZO_RISPOSTA, Utils.generaTesto(), Utils.leggiTitolo());
			httpGet = new HttpGet(indirizzo);
	        httpResponse = httpclient.execute(httpGet, httpContext);
	        stringResponse = EntityUtils.toString(httpResponse.getEntity());
	        
	        if(stringResponse.contains("'Risposta data con successo!'")){
				System.out.println("Risposta inviata!");
			}
			else{
				System.out.println("Errore invio risposta!");
			}
	        
		} catch(Exception ex){
			System.out.println("Errore in rispondi: "+ex);
		}
	}
	
	
	/**
	 * Vota una risposta.
	 */
	public void vota(){
		try{
			Random random = new Random();
			String id = Integer.toString(random.nextInt()+1);
			String indirizzo = String.format(INDIRIZZO_VOTO, id, Utils.leggiTitolo());
			httpGet = new HttpGet(indirizzo);
	        httpResponse = httpclient.execute(httpGet, httpContext);
	        stringResponse = EntityUtils.toString(httpResponse.getEntity());
	        
		} catch(Exception ex){
			System.out.println("Errore in voto: "+ex);
		}
	}

}
