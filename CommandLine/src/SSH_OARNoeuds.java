import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSH_OARNoeuds extends SSH {


    public SSH_OARNoeuds (String host, String user, String password) {
	super(host, user, password);
    }

    public void connectFromSSH (SSH ssh){
	
	try{
	    JSch jsch = new JSch();
	    java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
	    
	    // Réglages du port de la machine actuellement connectée
	    // afin de se connecter via ce serveur à term2
            int assigned_port = ssh.getAssignedPort(0, this.host, 22);

            /*
            Connection à la machine frontale term2 via le serveur ghome
            Port de term2.grid.metz.supelec.fr : 59473
             */

            this.session = jsch.getSession(this.user, "127.0.0.1", assigned_port);
            this.session.setConfig(config);
            this.session.setPassword(this.password);
            System.out.println("Connexion à " + this.host + " ...");
            this.session.setServerAliveInterval(3600000);
            this.session.connect();
            System.out.println("Connecté à " + this.host);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Problème. Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
            System.out.println("--------------------");

        }
    }


    public void commandeOARSUB(int nbNoeuds, int tpsAllocation) throws Exception {

        String command = null;
        command = "oarsub -l nodes=" + nbNoeuds + ",walltime=" + getTemps(tpsAllocation) + " -I";
    }

    public String getTemps(int tps) {

        String temps = null;
        if(tps<=59){
            temps = "00:" + tps + ":00";
        } 
        if(tps==60){
            temps = "01:00:00";
        }
        if(tps>60 && tps<119){
            int tpsIntermediaire = tps - 60;
            temps = "01:" + tpsIntermediaire + ":00";
        }
        if(tps==120){
            temps = "02:00:00";
        }
        if(tps>121 && tps<179){
            int tpsIntermediaire = tps - 60;
            temps = "02:" + tpsIntermediaire + ":00";
        }
        if(tps==180){
            temps = "03:00:00";
        }else{System.out.println("Veuillez saisir un temps d'allocation inférieur à 3 heures. Merci.");}
        return temps;
    }

}
