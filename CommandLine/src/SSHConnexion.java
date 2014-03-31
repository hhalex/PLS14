import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSH {

    String host;
    String user;
    String password;
    static Session session_final;


    public SSH (String host, String user, String password) {

        this.host = host;
        this.user = user;
        this.password = password;

        connectionSessionSSH();
    }   

    public void connectionSessionSSH() {

        JSch jsch = null;

        Session session_ghome = null;
        Session session_term2 = null;
        //Session session_cluster = null;

        //String cat = "cat $OAR_NODE_FILE";
        String command = "ls -l";
        //String command = "oarsub -l nodes=1,walltime=00:01:00 -I";
        String host_term2 = "term2.grid.metz.supelec.fr";
        //String host_cluster = "sh15.grid.metz.supelec.fr";

        try{

            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");

            jsch = new JSch();
            session_ghome = jsch.getSession(user, host, 22);
            session_ghome.setPassword(password);
            session_ghome.setConfig(config);
            session_ghome.setServerAliveInterval(3600000);
            session_ghome.connect();
            System.out.println("Connecté à " + session_ghome.getHost() + " sous le port " + session_ghome.getPort());

            // Réglages du port de ghome afin de se connecter via ce serveur à term2
            int assinged_port = session_ghome.setPortForwardingL(0, host_term2, 22);

            /*
            Connection à la machine frontale term2 via le serveur ghome
            Port de term2.grid.metz.supelec.fr : 59473
             */

            session_term2 = jsch.getSession(user, "127.0.0.1", assinged_port);
            session_term2.setConfig(config);
            session_term2.setPassword(password);
            System.out.println("Connexion à " + host_term2 + " ...");
            session_term2.setServerAliveInterval(3600000);
            session_term2.connect();
            System.out.println("Connecté à " + host_term2);

            session_final = session_term2;

	    if(session_final.isConnected()) {
                OARNoeuds noeuds = new OARNoeuds();   
                InterfaceUtilisateur.closeGUI();
            }

             Channel channel = session_term2.openChannel("shell");
	     
	     
	     InputStream in = channel.getInputStream();
	     OutputStream out = channel.getOutputStream();
	     
	     ((ChannelShell)channel).setPtyType("vt102");
	     channel.connect();
	     
	     byte[] tmp=new byte[1024];
	     
	     out.write((command + ";hostname").getBytes());
	     out.write(("\n").getBytes());
	     out.flush();

	    

	     
	     	while (in.available() > 0) {
	     	    int i = in.read(tmp, 0, 1024);
	     	    if (i < 0) {
	     		//System.out.println("[debug] breaking at i < 0");
	     		break;
	     	    }
	     	    String buffer = new String(tmp, 0, i);
	     	    System.out.println( buffer);
	     	    if(buffer.contains("REMOTE JSH COMMAND FINISHED")){
	     		System.out.println("[debug] breaking at finished");
	     		break;
	     	    }
	     	}
	     	
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
            System.out.println("--------------------");

            /*
             * Si connexion valide alors on lance la fenêtre d'allocation des noeuds
             * 
             * OARNoeuds jf_allocation = new OARNoeuds();
             * jf_allocation.lancementFenetre();
             * 
             */

        }
    }

    public static Session getSession() {
        return session_final;
    }

    public static void commandeOARSUB(int nbNoeuds, int tpsAllocation) throws Exception {

        String command = null;
        command = "oarsub -l nodes=" + nbNoeuds + ",walltime=" + getTemps(tpsAllocation) + " -I";
        executerCommande(command);
    }

    public static String getTemps(int tps) {

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
