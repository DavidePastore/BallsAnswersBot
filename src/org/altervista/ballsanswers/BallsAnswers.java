/**
 * 
 */
package org.altervista.ballsanswers;

/**
 * L'applicazione inizia qui.
 * @author <a reef="https://github.com/DavidePastore">DavidePastore</a>
 *
 */
public class BallsAnswers {
	
	private static String username = "palla";
	private static String password = "ek3841@auoie.com";
	private static Utente utente;
	private static int numeroCiclo = 1000;

	/**
	 * Main dell'applicazione.
	 * @param args
	 */
	public static void main(String[] args) {
		utente = new Utente(username, password);
		utente.login();
		for(int i = 0; i < numeroCiclo; i++){
			System.out.println("Ciclo "+ (i+1));
			utente.chiedi();
			utente.rispondi();
			utente.vota();
		}
	}

}
