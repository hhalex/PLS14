import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;

public class InterfaceUtilisateur {

    private JFrame frame;
    
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

        this.frame = new JFrame("Connexion MF Supélec");
        this.frame.setSize(350,130);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bouton_connect = new JButton ("Connexion");
        bouton_disconnect = new JButton ("Déconnexion");

        this.label_machine = new JLabel("Adresse machine : ");
        jtf_machine = new JTextField("ghome.metz.supelec.fr");

        this.label_login = new JLabel("Login : ");
        jtf_login = new JTextField("");

        this.label_mdp = new JLabel("Password : ");
        jtf_mdp = new JPasswordField("");

        this.panel_machine = new JPanel();
        this.panel_login = new JPanel();
        this.panel_mdp = new JPanel();
        this.panel_bouton = new JPanel();
        this.panelGlobal = new JPanel((new GridLayout(0,2)));

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

        this.frame.setContentPane(panelGlobal);               
        this.frame.setVisible(true);

        this.bouton_connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eventBouton) {
                System.out.println("Connexion à la machine frontale " + jtf_machine.getText() + " via l'utilisateur " + jtf_login.getText() + " ...");
                SSHCommandExecutor ssh= new SSHCommandExecutor(jtf_machine.getText(), jtf_login.getText(), jtf_mdp.getText());
            }
        });

        this.bouton_disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eventBouton) {
                /* 
                 * Il faut implémenter ce bouton de façon à lancer :
                 * 
                 * channel.disconnect();
                 * session_term2.disconnect();
                 * session_ghome.disconnect();
                */
            }
        });
    }

    public static void main (String args[]) {

        InterfaceUtilisateur boite = new InterfaceUtilisateur();

    }
}