import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSHConnexion {

    String host;
    String user;
    String password;


    public SSHConnexion (String host, String user, String password) {

        this.host = host;
        this.user = user;
        this.password = password;

        connectionSSH();
    }   

    public void connectionSSH() {

        JSch jsch = null;

        Session session_ghome = null;
        Session session_term2 = null;

        String command = "oarsub -l nodes=1,walltime=00:01:00 -I";
        String host_term2 = "term2.grid.metz.supelec.fr";

        try{

            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");

            jsch = new JSch();
            session_ghome = jsch.getSession(user, host, 22);
            session_ghome.setPassword(password);
            session_ghome.setConfig(config);
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
            session_term2.connect();
            System.out.println("Connecté à " + host_term2);

            Channel channel = session_term2.openChannel("exec");
            ( (ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ( (ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp=new byte[1024];

            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }

            channel.disconnect();
            session_term2.disconnect();
            System.out.println("Déconnexion de " + host_term2 + " réussie");
            session_ghome.disconnect();
            System.out.println("Déconnexion de " + host + " réussie");
            System.out.println("--------------------");

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
            System.out.println("--------------------");
        }
    }
}
