
import java.awt.event.ActionListener;

public abstract class SSHActionListener implements ActionListener{
    private SSH ssh;

    public SSHActionListener(SSH ssh){
	this.ssh = ssh;
    }

    public SSH getSSH(){
	return this.ssh;
    }
}
