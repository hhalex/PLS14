
import java.awt.event.ActionListener;

public abstract class SSHOARActionListener implements ActionListener{
    protected SSH_IUAllocation ssh;

    public SSHOARActionListener(SSH_IUAllocation ssh){
	this.ssh = ssh;
    }

    public SSH_IUAllocation getSSH(){
	return this.ssh;
    }
}
