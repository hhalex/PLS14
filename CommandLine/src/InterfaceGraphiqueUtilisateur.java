import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jcraft.jsch.Session;


/**
 * 
 * @author JMPerez
 * 
 * La classe InterfaceGraphiqueUtilisateur est la classe mère des IU sous-jacentes.
 */


public abstract class InterfaceGraphiqueUtilisateur {

    protected JPanel panelConteneur;
    protected JFrame frame;
    protected Session sessionCourante;

    public InterfaceGraphiqueUtilisateur() {

        /**
         * Définir la constructeur dans chaque classe fille
         */
    }

    public abstract void fermerInterface();

    public static void main(String[] args) {

        InterfaceUtilisateur connexionClusterIU = new InterfaceUtilisateur();
    }
}
