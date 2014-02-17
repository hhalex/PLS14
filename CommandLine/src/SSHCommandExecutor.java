import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSHCommandExecutor {

    public static void main(String[] args) {
	String host="term2.grid.metz.supelec.fr";
	String user="munozperez_jua";
	String password="77036909";
	String command="oarsub nodes=4,walltime=00:10:00 -I";
	String oarStatCommand="oarstat";
	try{

	    java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");
	    JSch jsch = new JSch();
	    Session session=jsch.getSession(user, host, 22);
	    session.setPassword(password);
	    session.setConfig(config);
	    session.connect();
	    System.out.println("Connected to " + session.getHost());

	    Channel channel=session.openChannel("exec");
	    ((ChannelExec)channel).setCommand(command);
	    channel.setInputStream(null);
	    ((ChannelExec)channel).setErrStream(System.err);

	    Channel channelStat=session.openChannel("exec");
	    ((ChannelExec)channelStat).setCommand(oarStatCommand);
	    channelStat.setInputStream(null);
	    ((ChannelExec)channelStat).setErrStream(System.err);

	    InputStream in=channel.getInputStream();
	    channel.connect();
	    byte[] tmp=new byte[1024];
	    while(true){
		while(in.available()>0){
		    int i=in.read(tmp, 0, 1024);
		    if(i<0)break;
		    System.out.print(new String(tmp, 0, i));
//static void main(String[] args) {
//String host="term2.grid.metz.supelec.fr";
//String user="munozperez_jua";
//String password=;
//String command="oarsub nodes=4,walltime=00:10:00 -I";
//String oarStatCommand="oarstat";
//try{
//
//	java.util.Properties config = new java.util.Properties(); 
//	config.put("StrictHostKeyChecking", "no");
//	JSch jsch = new JSch();
//	Session session=jsch.getSession(user, host, 22);
//	session.setPassword(password);
//	session.setConfig(config);
//	session.connect();
//	System.out.println("Connected to " + session.getHost());
//
//	Channel channel=session.openChannel("exec");
//	((ChannelExec)channel).setCommand(command);
//	channel.setInputStream(null);
//	((ChannelExec)channel).setErrStream(System.err);
//
//	Channel channelStat=session.openChannel("exec");
//	((ChannelExec)channelStat).setCommand(oarStatCommand);
//	channelStat.setInputStream(null);
//	((ChannelExec)channelStat).setErrStream(System.err);
//
//	InputStream in=channel.getInputStream();
//	channel.connect();
//	byte[] tmp=new byte[1024];
//	while(true){
//		while(in.available()>0){
//			int i=in.read(tmp, 0, 1024);
//			if(i<0)break;
//			System.out.print(new String(tmp, 0, i));
//		}
//		if(channel.isClosed()){
//			System.out.println("exit-status: "+channel.getExitStatus());
//			break;
//		}
//		try{Thread.sleep(1000);}catch(Exception ee){}
//	}
//	channel.disconnect();
//	session.disconnect();
//	System.out.println("DONE");
//}catch(Exception e){
			e.printStackTrace();
		}
		if(channel.isClosed()){
		    System.out.println("exit-status: "+channel.getExitStatus());
		    break;
		}
		try{Thread.sleep(1000);}catch(Exception ee){}
	    }
	    channel.disconnect();
	    session.disconnect();
	    System.out.println("DONE");
	}catch(Exception e){
	    e.printStackTrace();
	}

    }
}
