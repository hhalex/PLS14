import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSH {

    String host;
    String user;
    String password;
    Session session;

    static Channel channel;
    static InputStream in;
    static OutputStream out;

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

	}catch (Exception e) {
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
	    channel = this.session.openChannel("shell");

	    in = channel.getInputStream();
	    out = channel.getOutputStream();

	    ((ChannelShell) channel).setPtyType("vt102");
	    channel.connect();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    public void closeChannel(){
	try {
	    channel.disconnect();
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }

    public void sendCommand(String command){
	try {
	    out.write(command.getBytes());
	    out.write("\n".getBytes());
	    out.flush();
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }

    public boolean hasMessage () {
	boolean flag = false;
	try {
	    flag = in.available()>0;
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}

	return flag;
    }

    public String readReceivedMessage (){

	String msg="";
	
	try {

	    byte[] tmp=new byte[1024];
	    String tmp_str="";
			
	    while(in.available() > 0){
		int i= in.read(tmp, 0, 1024);
		tmp_str = new String(tmp, 0, i);
		msg+=tmp_str;
		if(channel.isClosed()){
		    System.out.println("exit-status: "+channel.getExitStatus());
		    break;
		}
	    }
	    System.out.print(msg);

	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return msg;
    }

    public Session getSession() {
        return this.session;
    }
}
