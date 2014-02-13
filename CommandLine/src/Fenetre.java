import javax.swing.*;

public class Fenetre {

	public static void main (String args[]) {
		System.out.println("début main");
		JFrame fen = new JFrame();
		fen.setSize(300, 300);
		fen.setTitle("Ma première fenêtre");
		System.out.println("avant affichage fenêtre");
		fen.setVisible(true);
		System.out.println("fin main");
	}

}