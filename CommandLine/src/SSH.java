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
    Session session;

    Channel channel;
    InputStream in;
    OutputStream out;

    public SSH (String host, String user, String password) {

        this.host = host;
        this.user = user;
        this.password = password;
	this.session = null;
    }

    public void connect() {

	try{

            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();
            this.session = jsch.getSession(user, host, 22);
            this.session.setPassword(password);
            this.session.setConfig(config);
            this.session.setServerAliveInterval(3600000);
            this.session.connect();
            System.out.println("Connecté à " + session.getHost() + " sous le port " + session.getPort());

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Votre identifiant ou mot de passe ou l'adresse de la MF est erroné");
            System.out.println("--------------------");

        }
    }

    protected int getAssignedPort(int i, String host, int port){
	int assigned_port = 22;
	try {
	    // Réglages du port de ghome afin de se connecter via ce serveur à term2
	    assigned_port = this.session.setPortForwardingL(i, host, port);
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return assigned_port;
    }

    public void deconnect(){
	this.session.disconnect();
	this.session = null;
    }

    public boolean sessionActive(){
	return this.session != null && this.session.isConnected();
    }

    public void openChannel(String mode){
	try{
	    this.channel = this.session.openChannel("shell");
	     
	    this.in = channel.getInputStream();
	    this.out = channel.getOutputStream();
	     
	    ((ChannelShell) channel).setPtyType("vt102");
	    this.channel.connect();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    public void closeChannel(){
	try {
	    this.channel.disconnect();
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }

    public void read(){
	try {
	    byte[] tmp=new byte[1024];

	    while(true){
		int i= this.in.read(tmp, 0, 1024);
		System.out.print(new String(tmp, 0, i));
		if(channel.isClosed()){
		    System.out.println("exit-status: "+channel.getExitStatus());
		    break;
		}
	    }
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}

    }

    public Session getSession() {
        return session;
    }
}
