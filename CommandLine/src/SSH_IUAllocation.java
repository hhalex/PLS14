import java.io.IOException;

import com.jcraft.jsch.JSch;


public class SSH_IUAllocation extends SSH {

    public SSH_IUAllocation (String host, String user, String password) {

	super(host, user, password);
    }

    public void connectFromSSH (SSH ssh){

	try{
	    JSch jsch = new JSch();
	    java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");

	    // Réglages du port de la machine actuellement connectée afin de se connecter via ce serveur à term2

	    int assigned_port = ssh.getAssignedPort(0, this.host, 22);

	    /*
	      Connection à la machine frontale term2 via le serveur ghome
	      Port de term2.grid.metz.supelec.fr : 59473
	    */

	    this.session = jsch.getSession(this.user, "127.0.0.1", assigned_port);
	    this.session.setConfig(config);
	    this.session.setPassword(this.password);
	    this.session.setServerAliveInterval(3600000);
	    this.session.connect();
	    System.out.println("Connecté à " + this.host);

	}catch(Exception e){
	    /*
	     * e.printStackTrace();
	     * System.out.println("Problème. Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
	     * System.out.println("--------------------");
	     */

	}
    }

    public void sendCommandOARSUB(int nbNoeuds, int tpsAllocation) throws Exception {
	    
	    
	String command = "oarsub -l nodes=" + nbNoeuds + ",walltime=" + getTemps(tpsAllocation) + " -I";
	this.sendCommand(command);
	this.sendCommand("cat $OAR_NODE_FILE | uniq -c");

    }

    public static String getTemps(int tps) {

	String temps = null;
	String heure;
	String minutes;

	heure = Integer.toString(tps/60) + ":";
	minutes = Integer.toString(tps%60) + ":";

	if (tps/60<10) {
	    temps = "0" + heure + minutes + "00" ;
	}else{ temps = heure + minutes + "00";};

	return temps;
    }
	
	
    public void killCurrentJob() {
	String command = "oardel -j $OAR_JOB_ID";
	this.sendCommand(command);
    }

    public void infosCurrentJob() {
	String command = "oarstat -fj $OAR_JOB_ID";
	this.sendCommand(command);
    }
}
