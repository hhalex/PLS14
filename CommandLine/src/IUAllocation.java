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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class IUAllocation extends InterfaceGraphiqueUtilisateur {

	private JPanel panel_nbNoeuds;
	private JPanel panel_tpsAllocation;
	private JPanel panel_bouton_killjob;
	private JPanel panel_bouton_allouer;
	private JPanel panel_bouton_refresh;
	private JScrollPane panel_jta;

	static private JTextArea jta_connexion;

	private JLabel label_nbNoeuds;
	private static JTextField jtf_nbNoeuds;
	private JLabel label_tpsAllocation;
	private static JTextField jtf_tpsAllocation;

	private JButton bouton_killjob;
	private JButton bouton_allouer;
	private JButton bouton_refresh;

	public IUAllocation(SSH_IUAllocation sshoar) {

		this.ssh = sshoar;

		//Création d'une fenêtre de titre "Connexion..."

		this.frame = new JFrame("Allocation de noeuds de calcul");
		this.frame.setSize(500,130);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Création des éléments de la fenêtre pour l'allocation des noeuds

		bouton_killjob = new JButton("Tuer Job");
		bouton_allouer = new JButton("Allouer");
		bouton_refresh = new JButton("Rafraîchir");

		this.label_nbNoeuds = new JLabel("Nombre de noeuds :");
		jtf_nbNoeuds = new JTextField("");
		jtf_nbNoeuds.setColumns(10);

		this.label_tpsAllocation = new JLabel("Temps d'allocation (en min) :");
		jtf_tpsAllocation = new JTextField("");
		jtf_tpsAllocation.setColumns(10);

		this.jta_connexion = new JTextArea(3,0);

		//Création de la structure de la boîte pour placer les éléments

		this.panel_nbNoeuds = new JPanel();
		this.panel_tpsAllocation = new JPanel();
		this.panel_bouton_killjob = new JPanel();
		this.panel_bouton_allouer = new JPanel();
		this.panel_bouton_refresh = new JPanel();
		this.panel_jta = new JScrollPane();
		this.panelConteneur = new JPanel(new GridLayout(0,2));

		//Placement des composants dans la fenêtre préparée

		this.panel_nbNoeuds.setLayout(new BoxLayout(panel_nbNoeuds, BoxLayout.LINE_AXIS));
		this.panel_nbNoeuds.add(label_nbNoeuds);
		this.panel_nbNoeuds.add(jtf_nbNoeuds);

		this.panel_tpsAllocation.setLayout(new BoxLayout(panel_tpsAllocation, BoxLayout.LINE_AXIS));
		this.panel_tpsAllocation.add(label_tpsAllocation);
		this.panel_tpsAllocation.add(jtf_tpsAllocation);

		this.panel_bouton_killjob.setLayout(new BoxLayout(panel_bouton_killjob, BoxLayout.LINE_AXIS));
		this.panel_bouton_killjob.add(bouton_killjob);

		this.panel_bouton_allouer.setLayout(new BoxLayout(panel_bouton_allouer, BoxLayout.LINE_AXIS));
		this.panel_bouton_allouer.add(bouton_allouer);

		this.panel_bouton_refresh.setLayout(new BoxLayout(panel_bouton_refresh, BoxLayout.LINE_AXIS));
		this.panel_bouton_refresh.add(bouton_refresh);

		this.panel_jta.add(jta_connexion);

		this.panelConteneur.setLayout(new BoxLayout(this.panelConteneur, BoxLayout.PAGE_AXIS));

		this.panelConteneur.add(panel_nbNoeuds);
		this.panelConteneur.add(panel_tpsAllocation);
		this.panelConteneur.add(panel_bouton_killjob);
		this.panelConteneur.add(panel_bouton_allouer);
		this.panelConteneur.add(panel_bouton_refresh);
		this.panelConteneur.add(jta_connexion);

		this.frame.setContentPane(this.panelConteneur);
		this.frame.setVisible(true);
		this.frame.pack();

		/*
		 * Tue le job actuel lorsqu'on click sur le bouton
		 */

		bouton_killjob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					/*
					 * A COMPLETER
					 * SSH_IUAllocation.killJob();
					 */
				} catch (NumberFormatException e1) {	
				} catch (Exception e1) {}
			}
		});

		/*
		 * Tue le job actuel lorsqu'on appui sur "Enter"
		 */

		bouton_killjob.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					try {
						/*
						 * A COMPLETER
						 * SSH_IUAllocation.killJob();
						 */
					} catch (NumberFormatException e1) {
					} catch (Exception e1) {}
				}
			}
		});

		/*
		 * Lance la méthode oarsub lorsqu'on click sur le bouton
		 */

		bouton_allouer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String commande = SSH_IUAllocation.buildCommandOARSUB(Integer.parseInt(jtf_nbNoeuds.getText()), Integer.parseInt(jtf_tpsAllocation.getText()));
					SSH_IUAllocation.allocateNodes(commande);
				} catch (NumberFormatException e1) {	
				} catch (Exception e1) {}
			}
		});

		/*
		 * Lance la méthode oarsub lorsqu'on appuie sur "Enter"
		 */

		bouton_allouer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					try {
						String commande = SSH_IUAllocation.buildCommandOARSUB(Integer.parseInt(jtf_nbNoeuds.getText()),Integer.parseInt(jtf_tpsAllocation.getText()));
						SSH_IUAllocation.allocateNodes(commande);
					} catch (NumberFormatException e1) {
					} catch (Exception e1) {}
				}
			}
		});

		/*
		 * Download les données présentes sur le channel provenant de term2 lorsqu'on click sur le bouton
		 */

		bouton_refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SSH.readReceivedMessage();
				} catch (NumberFormatException e1) {	
				} catch (Exception e1) {}
			}
		});

		/*
		 * Download les données présentes sur le channel provenant de term2 lorsqu'on appui sur "Enter"
		 */

		bouton_refresh.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					try {
						SSH.readReceivedMessage();
					} catch (NumberFormatException e1) {
					} catch (Exception e1) {}
				}
			}
		});
	}

	public void displayReceivedMessage(){
		/*
		 * A COMPLETER
		 * 
		 * String msg = this.ssh.readReceivedMessage();
		 * System.out.print(msg);
		 * affiche msg dans la jtetarea
		 * 
		 * */
	}

	public void closeGUI() {
		this.frame.setVisible(false);
	}
}