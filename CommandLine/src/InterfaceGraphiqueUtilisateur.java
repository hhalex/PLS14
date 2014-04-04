import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * 
 * @author thidiff & hhalex
 * 
 * La classe InterfaceGraphiqueUtilisateur est la classe mère des IU sous-jacentes.
 */


public abstract class InterfaceGraphiqueUtilisateur {

    protected JPanel panelConteneur;
    protected JFrame frame;
    static protected SSH ssh;

    public InterfaceGraphiqueUtilisateur() {

        /**
         * Définir la constructeur dans chaque classe fille
         */
    }

    public static void main(String[] args) {

        IUConnexion connexionClusterIU = new IUConnexion();
    }
}
