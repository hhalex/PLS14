import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

public class BoiteDialogueIdentification {

    public static void main (String args[]) {

	// GUI construction
	BoiteDialogueIdentification boite = new BoiteDialogueIdentification();

    }

    private JFrame frame;

    //private JPanel panel_nom_boite;
    private JPanel panel_machine;
    private JPanel panel_login;
    private JPanel panel_mdp;
    private JPanel panel_bouton;

    private JPanel panelGlobal;

    //private JLabel nom_boite;
    private JLabel label_machine;
    private static JTextField jtf_machine;
    private JLabel label_login;
    private static JTextField jtf_login;
    private JLabel label_mdp;
    private JPasswordField jpf_mdp;

    private JButton bouton;

    public BoiteDialogueIdentification() {

	//String host="term2.grid.metz.supelec.fr";

	this.frame = new JFrame("Connexion à la Machine Frontale Supélec");
	this.frame.setSize(350,130);
	this.frame.setVisible(true);
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	bouton = new JButton ("Se connecter");

	this.label_machine = new JLabel("Adresse machine : ");
	this.jtf_machine = new JTextField("@");

	this.label_mdp = new JLabel("Password : ");
	jpf_mdp = new JPasswordField("password");

	this.label_login = new JLabel("Login : ");
	jtf_login = new JTextField("login");

	//this.nom_boite = new JLabel("Connexion à "+host);

	//this.panel_nom_boite = new JPanel();
	this.panel_machine = new JPanel();
	this.panel_login = new JPanel();
	this.panel_mdp = new JPanel();
	this.panel_bouton = new JPanel();
	this.panelGlobal = new JPanel();

	Font police = new Font("Arial", Font.BOLD, 13);
	Dimension dimension = new Dimension(150, 20);

	jtf_login.setFont(police);
	jtf_login.setPreferredSize(dimension);
	jtf_login.setForeground(Color.BLACK);

	jpf_mdp.setFont(police);
	jpf_mdp.setPreferredSize(dimension);
	jpf_mdp.setForeground(Color.BLACK);

	this.jtf_machine.setFont(police);
	this.jtf_machine.setPreferredSize(dimension);
	this.jtf_machine.setForeground(Color.BLACK);


	// Evenement clic sur le bouton ok
	this.bouton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    System.out.println("Connexion à la machine frontale " + jtf_machine.getText());
		    System.out.println("Login utilisé : " + jtf_login.getText()); 
		    SSHCommandExecutor SSH = new SSHCommandExecutor(jtf_login.getText(), jtf_machine.getText(), jpf_mdp.getText());
				
		}
	    });

	//this.panel_nom_boite.setLayout(new BoxLayout(panel_nom_boite, BoxLayout.LINE_AXIS));
	//this.panel_nom_boite.add(nom_boite);

	this.panel_machine.setLayout(new BoxLayout(panel_machine, BoxLayout.LINE_AXIS));
	this.panel_machine.add(label_machine);
	this.panel_machine.add(jtf_machine);

	this.panel_login.setLayout(new BoxLayout(panel_login, BoxLayout.LINE_AXIS));
	this.panel_login.add(label_login);
	this.panel_login.add(jtf_login);

	this.panel_mdp.setLayout(new BoxLayout(panel_mdp, BoxLayout.LINE_AXIS));
	this.panel_mdp.add(label_mdp);
	this.panel_mdp.add(jpf_mdp);

	this.panel_bouton.setLayout(new BoxLayout(panel_bouton, BoxLayout.LINE_AXIS));
	this.panel_bouton.add(bouton);

	this.panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.PAGE_AXIS));
	//panelGlobal.add(panel_nom_boite);
	panelGlobal.add(panel_machine);
	panelGlobal.add(panel_login);
	panelGlobal.add(panel_mdp);
	panelGlobal.add(panel_bouton);

	this.frame.setContentPane(panelGlobal);               
	this.frame.setVisible(true);

    }
}
