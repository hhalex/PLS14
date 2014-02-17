import java.io.*;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

public class BoiteDialogueIdentification {
    
    private JFrame frame;

    private JPanel pan;
    private JPanel pan2;
    private JPanel pan3;
    private JPanel pan4;
    private JPanel pan5;

    private JPanel panGlobal;

    private JLabel nom_boite;
    private JLabel label_machine;
    private JTextField jtf_machine;
    private JLabel label_login;
    private JTextField jtf_login;
    private JLabel label_mdp;
    private JTextField jtf_mdp;
    private JButton bouton;

    public BoiteDialogueIdentification(){

	String host="term2.grid.metz.supelec.fr";
	String user="munozperez_jua";
	String password="77036909";
	String command="oarsub nodes=4,walltime=00:10:00 -I";
	String oarStatCommand="oarstat";
	java.util.Properties config = new java.util.Properties(); 
	config.put("StrictHostKeyChecking", "no");

        this.frame = new JFrame("Connexion");
	this.frame.setSize(300, 150);
	this.frame.setVisible(true);
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.bouton = new JButton ("Se connecter");
	this.jtf_machine = new JTextField("@");
	this.label_machine = new JLabel("Adresse machine : ");
	this.jtf_mdp = new JTextField("password");
	this.label_mdp = new JLabel("Password : ");
	this.jtf_login = new JTextField("login");
	this.label_login = new JLabel("Login : ");
	this.nom_boite = new JLabel("Connexion à "+host);
	this.pan = new JPanel();
	this.pan2 = new JPanel();
	this.pan3 = new JPanel();
	this.pan4 = new JPanel();
	this.pan5 = new JPanel();
	this.panGlobal = new JPanel();

	Font police = new Font("Arial", Font.BOLD, 13);
	Dimension dimension = new Dimension(150, 20);

	this.jtf_login.setFont(police);
	this.jtf_login.setPreferredSize(dimension);
	this.jtf_login.setForeground(Color.BLACK);

	this.jtf_mdp.setFont(police);
	this.jtf_mdp.setPreferredSize(dimension);
	this.jtf_mdp.setForeground(Color.BLACK);

	this.jtf_machine.setFont(police);
	this.jtf_machine.setPreferredSize(dimension);
	this.jtf_machine.setForeground(Color.BLACK);

	this.bouton.addActionListener(new BoutonListener());

	this.pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
	this.pan.add(nom_boite);

	this.pan2.setLayout(new BoxLayout(pan2, BoxLayout.LINE_AXIS));
	this.pan2.add(label_machine);
	this.pan2.add(jtf_machine);

	this.pan3.setLayout(new BoxLayout(pan3, BoxLayout.LINE_AXIS));
	this.pan3.add(label_login);
	this.pan3.add(jtf_login);

	this.pan4.setLayout(new BoxLayout(pan4, BoxLayout.LINE_AXIS));
	this.pan4.add(label_mdp);
	this.pan4.add(jtf_mdp);

	this.pan5.setLayout(new BoxLayout(pan5, BoxLayout.LINE_AXIS));
	this.pan5.add(bouton);

	this.panGlobal.setLayout(new BoxLayout(panGlobal, BoxLayout.PAGE_AXIS));
	panGlobal.add(pan);
	panGlobal.add(pan2);
	panGlobal.add(pan3);
	panGlobal.add(pan4);

	this.frame.setContentPane(panGlobal);               
	this.frame.setVisible(true);
    }

    public static void main (String args[]) {
	try{
	    //Configuration de la connexion ssh
	    String host="term2.grid.metz.supelec.fr";
	    String user="munozperez_jua";
	    String password="77036909";
	    String command="oarsub nodes=4,walltime=00:10:00 -I";
	    String oarStatCommand="oarstat";
	    java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");

	    // Boite de dialogue de configuration de la connexion
	    
	    BoiteDialogueIdentification boite = new BoiteDialogueIdentification();
	    
	    // Connexion
	    //JSch jsch = new JSch();
	    //Session session=jsch.getSession(user, host, 22);
	    //session.setPassword(password);
	    //session.setConfig(config);
	    //session.connect();
	    //System.out.println("Connected to " + session.getHost());
	    //
	    //Channel channel=session.openChannel("exec");
	    //((ChannelExec)channel).setCommand(command);
	    //channel.setInputStream(null);
	    //((ChannelExec)channel).setErrStream(System.err);
	    //
	    //Channel channelStat=session.openChannel("exec");
	    //((ChannelExec)channelStat).setCommand(oarStatCommand);
	    //channelStat.setInputStream(null);
	    //((ChannelExec)channelStat).setErrStream(System.err);
	    //
	    //InputStream in=channel.getInputStream();
	    //channel.connect();
	    //byte[] tmp=new byte[1024];
	    //while(true){
	    //	while(in.available()>0){
	    //	    int i=in.read(tmp, 0, 1024);
	    //	    if(i<0)break;
	    //	    System.out.print(new String(tmp, 0, i));
	    //	}
	    //	if(channel.isClosed()){
	    //	    System.out.println("exit-status: "+channel.getExitStatus());
	    //	    break;
	    //	}
	    //	try{Thread.sleep(1000);}catch(Exception ee){}
	    //}
	    //channel.disconnect();
	    //session.disconnect();
	    //System.out.println("DONE");
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    class BoutonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	    System.out.println("TEXT : jtf " + jtf_login.getText());
	    System.out.println("TEXT : jtf2 " + jtf_mdp.getText());
	}
    }
}
