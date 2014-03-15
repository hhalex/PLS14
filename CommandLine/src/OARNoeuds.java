import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.*;

public class OARNoeuds {

    private JButton soummettre_job;

    private JLabel nb_noeuds_jlabel;
    private JTextField nb_noeuds_jtf;

    private JLabel tp_alloc_jlabel;
    private JTextField temps_allocation_jtf;

    private JScrollPane scrollVisualisation;

    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;

    private JTextArea visualisation_noeuds;


    public OARNoeuds() {
        initComponents();
    }

    private void initComponents() {

        jSeparator1 = new JSeparator();
        jSeparator3 = new JSeparator();
        jSeparator2 = new JSeparator();

        nb_noeuds_jlabel = new JLabel();
        nb_noeuds_jtf = new JTextField();

        tp_alloc_jlabel = new JLabel();
        temps_allocation_jtf = new JTextField();

        scrollVisualisation = new JScrollPane();

        visualisation_noeuds = new JTextArea();

        soummettre_job = new JButton();

        nb_noeuds_jlabel.setText("Nombre de noeuds :");
        //nb_noeuds_jlabel.setToolTipText("");

        /*temps_allocation_jtf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                temps_allocation_jtfActionPerformed(evt);
            }
        });*/

        tp_alloc_jlabel.setText("Temps d'allocation (en min) :");

        // Création de la fenêtre contenant la liste des noeuds alloués
        visualisation_noeuds.setColumns(20);
        visualisation_noeuds.setRows(5);

        scrollVisualisation.setViewportView(visualisation_noeuds);

        soummettre_job.setText("Soummettre JOB");
        soummettre_job.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                soummettre_jobActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(jSeparator1)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(nb_noeuds_jlabel)
                                                        .addGap(73, 73, 73)
                                                        .addComponent(nb_noeuds_jtf, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jSeparator3)
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(tp_alloc_jlabel)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(temps_allocation_jtf, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(scrollVisualisation)))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(85, 85, 85)
                                                                        .addComponent(soummettre_job)))
                                                                        .addContainerGap(25, Short.MAX_VALUE))
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nb_noeuds_jlabel)
                                .addComponent(nb_noeuds_jtf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tp_alloc_jlabel)
                                        .addComponent(temps_allocation_jtf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5)
                                        .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(soummettre_job)
                                        .addGap(5, 5, 5)
                                        .addComponent(scrollVisualisation, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(25, Short.MAX_VALUE))
                );

        pack();
    }// </editor-fold>                        

    private void soummettre_jobActionPerformed(ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OARNoeuds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OARNoeuds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OARNoeuds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OARNoeuds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        OARNoeuds noeuds = new OARNoeuds();

        }
    }                  
