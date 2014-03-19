import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSHConnexion {

    String host;
    String user;
    String password;
    static Session session_final;


    public SSHConnexion (String host, String user, String password) {

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

            // Réglages du port de term2 afin de se connecter via ce serveur à au cluster
            //int assinged_port_cluster = session_term2.setPortForwardingL(0, host_cluster, 22);

            /*
             * Connection au cluster via la machine frontale term2
             */

            /*
            session_cluster = jsch.getSession(user, "127.0.0.1", assinged_port_cluster);
            session_cluster.setConfig(config);
            session_cluster.setPassword(password);
            System.out.println("Connexion à " + host_cluster + " ...");
            session_cluster.connect();
            System.out.println("Connecté à " + host_cluster);
             */

            session_final = session_term2;

            //System.out.println("Est-ce que sesseion_final est connectée ? " + String.valueOf(session_final.isConnected()));

            //System.out.println("Est-ce que sesseion_ghome est connectée ? " + String.valueOf(session_ghome.isConnected()));
            //System.out.println("Est-ce que sesseion_term2 est connectée ? " + String.valueOf(session_term2.isConnected()));
            //System.out.println(String.valueOf(session_cluster.isConnected()));

            //executerCommande(command);
            //executerCommande(cat);

            //System.out.println("Est-ce que sesseion_ghome est connectée ? " + String.valueOf(session_ghome.isConnected()));
            //System.out.println("Est-ce que sesseion_term2 est connectée ? " + String.valueOf(session_term2.isConnected()));

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

	    while (true) {  

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
		if (channel.isClosed()) {
		    //System.out.println("[debug] breaking at isClosed");
		    in.close();
		    break;
		}
	    }

            //}

            /*
             * Feed-back de la console sous forme de byte[]
             * Implémenter espace où l'utilisateur suit l'évolution de la connexion
             */

        //byte[] tmp=new byte[1024];
	//
        //while(true){
        //    while(in.available()>0){
        //        int i=in.read(tmp, 0, 1024);
        //        if(i<0)break;
        //        System.out.print(new String(tmp, 0, i));
        //    }
        //    if(channel.isClosed()){
        //        System.out.println("exit-status: " + channel.getExitStatus());
        //        break;
        //    }
        //    try{Thread.sleep(1000);}catch(Exception ee){}
        //}

            /*
            channel.disconnect();
            session_term2.disconnect();
            System.out.println("Déconnexion de " + host_term2 + " réussie");
            session_ghome.disconnect();
            System.out.println("Déconnexion de " + host + " réussie");
            System.out.println("--------------------");
             */

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

    private static void executerCommande(String cmd) throws Exception {

        Channel channel = SSHConnexion.session_final.openChannel("exec");
        ( (ChannelExec) channel).setCommand(cmd);
        channel.setInputStream(null);
        ( (ChannelExec) channel).setErrStream(System.err);
        /*
         * in est un thread qui est créé pour lire les bytes de sortie du terminal
         * Il est détruit après lecture
         */
        InputStream in = channel.getInputStream();

        System.out.println("Est-ce que le canal est actif ? " + String.valueOf(channel.isConnected()));
        System.out.println("Connexion du canal...");
        channel.connect();
        System.out.println("Canal connecté");
        System.out.println("Est-ce que le canal est actif ? " + String.valueOf(channel.isConnected()));

        terminalPrintState(in);

        System.out.println(String.valueOf(channel.isClosed()));
        System.out.println("Processus terminé");

        /*
        byte[] tmp=new byte[1024];

        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
            }
            if(channel.isClosed()){
                // System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }
         */
    }

    private static void terminalPrintState(InputStream in) throws IOException, InterruptedException {

        byte[] tmp=new byte[1024];

        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i==0){System.out.println("On a i<0 et on sort de la boucle");};
                System.out.println("---------------------");
                System.out.print(new String(tmp, 0, i));
            }
            /*
            if(channel.isClosed()){
            System.out.println("exit-status: " + channel.getExitStatus());
            break;
            }
             */
            try{Thread.sleep(1000);}catch(Exception ee){}
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
