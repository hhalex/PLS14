import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class IUConnexion extends InterfaceGraphiqueUtilisateur {

	private JPanel panel_machine;
	private JPanel panel_login;
	private JPanel panel_mdp;
	private JPanel panel_bouton;
	private JScrollPane panel_jta;

	static private JTextArea jta_connexion;

	private JLabel label_machine;
	private static JTextField jtf_machine;
	private JLabel label_login;
	private static JTextField jtf_login;
	private JLabel label_mdp;
	private static JTextField jtf_mdp;

	private JButton bouton_connect;

	public IUConnexion() {

		//Création d'une fenêtre de titre "Connexion..."

		this.frame = new JFrame("Connexion MF Supélec");
		this.frame.setSize(500,130);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Création des éléments de la fenêtre de connexion à ghome

		bouton_connect = new JButton ("Connexion");

		this.label_machine = new JLabel("Adresse machine : ");
		jtf_machine = new JTextField("ghome.metz.supelec.fr");

		this.label_login = new JLabel("Login : ");
		jtf_login = new JTextField("");

		this.label_mdp = new JLabel("Password : ");
		jtf_mdp = new JPasswordField("");

		this.jta_connexion = new JTextArea(3,0);

		//Création de la structure de la boîte pour placer les éléments

		this.panel_machine = new JPanel();
		this.panel_login = new JPanel();
		this.panel_mdp = new JPanel();
		this.panel_bouton = new JPanel();
		this.panel_jta = new JScrollPane();
		this.panelConteneur = new JPanel((new GridLayout(0,2)));

		//Placement des composants dans la fenêtre préparée

		this.panel_machine.setLayout(new BoxLayout(panel_machine, BoxLayout.LINE_AXIS));
		this.panel_machine.add(label_machine);
		this.panel_machine.add(jtf_machine);

		this.panel_login.setLayout(new BoxLayout(panel_login, BoxLayout.LINE_AXIS));
		this.panel_login.add(label_login);
		this.panel_login.add(jtf_login);

		this.panel_mdp.setLayout(new BoxLayout(panel_mdp, BoxLayout.LINE_AXIS));
		this.panel_mdp.add(label_mdp);
		this.panel_mdp.add(jtf_mdp);

		this.panel_bouton.setLayout(new BoxLayout(panel_bouton, BoxLayout.LINE_AXIS));
		this.panel_bouton.add(bouton_connect);

		this.panel_jta.add(jta_connexion);

		this.panelConteneur.setLayout(new BoxLayout(this.panelConteneur, BoxLayout.PAGE_AXIS));

		this.panelConteneur.add(panel_machine);
		this.panelConteneur.add(panel_login);
		this.panelConteneur.add(panel_mdp);
		this.panelConteneur.add(panel_bouton);
		this.panelConteneur.add(jta_connexion);

		this.frame.setContentPane(this.panelConteneur);               
		this.frame.setVisible(true);
		this.frame.pack();

		// Bouton de connexion : listener click

		this.bouton_connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent eventBouton) {
				callbackBoutonConnexionValidationGhome();}
		});

		// Bouton de connexion : listener touche

		this.bouton_connect.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					callbackBoutonConnexionValidationGhome();
				}
			}
		});
	}

	private void callbackBoutonConnexionValidationGhome(){

		System.out.println(
				"Connexion à la machine frontale " + jtf_machine.getText() + " via l'utilisateur " + 
						jtf_login.getText() + " ...");

		SSH ssh_ghome = null;

		ssh_ghome = new SSH(jtf_machine.getText(), jtf_login.getText(), jtf_mdp.getText());
		ssh_ghome.connect();

		if (!ssh_ghome.sessionActive()) {
			System.out.println("Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
			System.out.println("--------------------");
		}else{
			this.ssh = ssh_ghome;
			callbackBoutonConnexionValidationTerm2();};
	}

	private void callbackBoutonConnexionValidationTerm2(){

		SSH_IUAllocation sshOAR = new SSH_IUAllocation("term2.grid.metz.supelec.fr", jtf_login.getText(), 
				jtf_mdp.getText());
		sshOAR.connectFromSSH(this.ssh);

		if (!sshOAR.sessionActive()) {
			System.out.println("Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
			System.out.println("--------------------");
		}else{		
			this.ssh = sshOAR;
			this.closeGUI();
			sshOAR.openChannel("shell");
			passerelleIU();};
	}

	private void passerelleIU() {

		System.out.println("Bonjour " + jtf_login.getText() + " !");

		IUAllocation oarNoeuds = new IUAllocation((SSH_IUAllocation) this.ssh);

		/*try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}*/
	}

	public void closeGUI() {
		this.frame.setVisible(false);
	}

	public static void main (String args[]) {

		IUConnexion boite = new IUConnexion();
	}
}
