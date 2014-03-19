import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class OARNoeuds extends JFrame {

    private JFrame noeuds;
    private JPanel contentPane;
    private JTextField jtf_nbNoeuds;
    private JTextField jtf_tpsAllocation;
    private int nbNoeuds;
    private int tpsAllocation;

    /**
     * Launch the application.
     */

    /*public static void main (String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OARNoeuds frame = new OARNoeuds();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    /**
     * Create the frame.
     */

    public OARNoeuds() {
        
        noeuds = new JFrame();
        noeuds.setVisible(true);
        noeuds.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        noeuds.setBounds(100, 100, 450, 300);
        noeuds.setSize(450,200);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        noeuds.setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel bouton_allouer = new JPanel();
        contentPane.add(bouton_allouer);

        JLabel nb_noeuds = new JLabel("Nombre noeuds :");

        JLabel tps_allocation = new JLabel("Temps d'allocation (en min) :");

        jtf_nbNoeuds = new JTextField();
        jtf_nbNoeuds.setColumns(10);

        jtf_tpsAllocation = new JTextField();
        jtf_tpsAllocation.setColumns(10);

        JButton btn_Allouer = new JButton("Allouer");

        /*
         * Lance la méthode oarsub lorsqu'on appuie la touche "Enter"
         */

        btn_Allouer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    //Lancer le oarsub
                }
            }
        });

        /*
         * Lance la méthode oarsub lorsque l'on click sur le bouton "Allouer"
         */

        btn_Allouer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    SSHConnexion.commandeOARSUB(Integer.parseInt(jtf_nbNoeuds.getText()), Integer.parseInt(jtf_tpsAllocation.getText()));
                } catch (NumberFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        GroupLayout gl_bouton_allouer = new GroupLayout(bouton_allouer);
        gl_bouton_allouer.setHorizontalGroup(
                gl_bouton_allouer.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_bouton_allouer.createSequentialGroup()
                        .addGroup(gl_bouton_allouer.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_bouton_allouer.createSequentialGroup()
                                        .addGap(31)
                                        .addGroup(gl_bouton_allouer.createParallelGroup(Alignment.TRAILING)
                                                .addGroup(gl_bouton_allouer.createSequentialGroup()
                                                        .addComponent(nb_noeuds)
                                                        .addGap(92))
                                                        .addGroup(gl_bouton_allouer.createSequentialGroup()
                                                                .addComponent(tps_allocation)
                                                                .addGap(18)))
                                                                .addGroup(gl_bouton_allouer.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(jtf_nbNoeuds, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jtf_tpsAllocation, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(gl_bouton_allouer.createSequentialGroup()
                                                                                .addGap(175)
                                                                                .addComponent(btn_Allouer)))
                                                                                .addContainerGap(41, Short.MAX_VALUE))
                );
        gl_bouton_allouer.setVerticalGroup(
                gl_bouton_allouer.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_bouton_allouer.createSequentialGroup()
                        .addGap(32)
                        .addGroup(gl_bouton_allouer.createParallelGroup(Alignment.BASELINE)
                                .addComponent(nb_noeuds)
                                .addComponent(jtf_nbNoeuds, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_bouton_allouer.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(jtf_tpsAllocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tps_allocation))
                                        .addPreferredGap(ComponentPlacement.UNRELATED)
                                        .addComponent(btn_Allouer)
                                        .addGap(27))
                );
        bouton_allouer.setLayout(gl_bouton_allouer);
    }
}
