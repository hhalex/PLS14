import javax.swing.*;

public class BoiteDialogueIdentification {

	public static void main (String args[]) {

		String txt = null;
		while (true) {
			txt = JOptionPane.showInputDialog(null, "Login");
			if (txt == null) System.out.println ("Veuillez saisir votre login avant de valider.");
			else System.out.println ("Texte saisi : " + txt + " : de longueur " + txt.length());
		}
	}
}