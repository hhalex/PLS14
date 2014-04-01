import javax.swing.*;

import com.jcraft.jsch.Session;

import java.awt.GridLayout;
import java.awt.event.*;

public class InterfaceUtilisateur {

    static JFrame frame;

    private JPanel panel_machine;
    private JPanel panel_login;
    private JPanel panel_mdp;
    private JPanel panel_bouton;

    private JPanel panelGlobal;

    private JLabel label_machine;
    private static JTextField jtf_machine;
    private JLabel label_login;
    private static JTextField jtf_login;
    private JLabel label_mdp;
    private static JTextField jtf_mdp;

    private JButton bouton_connect;
    private JButton bouton_disconnect;

    public InterfaceUtilisateur() {

	//Création d'une fenêtre de titre "Connexion..."

        frame = new JFrame("Connexion MF Supélec");
        frame.setSize(350,130);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Création des éléments de la fenêtre de connexion à ghome

        bouton_connect = new JButton ("Connexion");
        bouton_disconnect = new JButton ("Déconnexion");

        this.label_machine = new JLabel("Adresse machine : ");
        jtf_machine = new JTextField("ghome.metz.supelec.fr");

        this.label_login = new JLabel("Login : ");
        jtf_login = new JTextField("munozperez_jua");

        this.label_mdp = new JLabel("Password : ");
        jtf_mdp = new JPasswordField("");

	//Création de la structure de la boîte pour placer les éléments

        this.panel_machine = new JPanel();
        this.panel_login = new JPanel();
        this.panel_mdp = new JPanel();
        this.panel_bouton = new JPanel();
        this.panelGlobal = new JPanel((new GridLayout(0,2)));

	//Placement des boutons dans la fenêtre préparée

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
        this.panel_bouton.add(bouton_disconnect);
        this.panel_bouton.add(bouton_connect);

        this.panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.PAGE_AXIS));

        panelGlobal.add(panel_machine);
        panelGlobal.add(panel_login);
        panelGlobal.add(panel_mdp);
        panelGlobal.add(panel_bouton);

        frame.setContentPane(panelGlobal);               
        frame.setVisible(true);

	// Bouton de connexion : listener

        this.bouton_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventBouton) {
        
		callbackBoutonConnexion();
		
		System.out.println("Connexion effectuée ! Bonjour " + jtf_login.getText() + " !");
            }
        });

        /*
         * Méthode à implémenter pour lancer la connexion lorsque l'on appuie sur la touche "Enter"
         */

        this.bouton_connect.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    System.out.println("Connexion à la machine frontale " + jtf_machine.getText() + " via l'utilisateur " + jtf_login.getText() + " ...");
                    SSH ssh = new SSH(jtf_machine.getText(), jtf_login.getText(), jtf_mdp.getText());
                }
            }
        });
    }

    private void callbackBoutonConnexion(){
	System.out.println(
	    "Connexion à la machine frontale " + 
	    jtf_machine.getText() + 
	    " via l'utilisateur " + 
	    jtf_login.getText() + 
	    " ..."
	);

	SSH ssh_ghome = null;
	do{
	    ssh_ghome = new SSH(jtf_machine.getText(), jtf_login.getText(), jtf_mdp.getText());
	    ssh_ghome.connect();
	}while(!ssh_ghome.sessionActive());

	SSH_OARNoeuds sshOAR = new SSH_OARNoeuds("term2.grid.metz.supelec.fr", jtf_login.getText(), jtf_mdp.getText());
	sshOAR.connectFromSSH(ssh_ghome);

	this.closeGUI();
	sshOAR.openChannel("shell");

	OARNoeuds oarNoeuds = new OARNoeuds(sshOAR);

	oarNoeuds.read();

	sshOAR.closeChannel();
    }

    public void closeGUI() {
        this.frame.setVisible(false);
    }

    public static void main (String args[]) {

        InterfaceUtilisateur boite = new InterfaceUtilisateur();
    }
}
