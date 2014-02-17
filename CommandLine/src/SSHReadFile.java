import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;

public class SSHReadFile {
	public static void main(String args[]) {
		String user = "munozperez_jua";
		String password = "14071991";
		String host = "term2.grid.metz.supelec.fr";
		int port=22;

		//String remoteFile="/users/JMPerez/Desktop/test.txt";

		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			System.out.println("Establishing Connection...");
			session.connect();
			System.out.println("Connection established.");
			System.out.println(session.getHost());
			//System.out.println("Crating SFTP Channel.");
			//ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			//sftpChannel.connect();
			//System.out.println("SFTP Channel created.");

			Runtime rt = Runtime.getRuntime();
			String commande = "oarstat";
			//nodes=4,walltime=00:05:00 -I
			System.out.println("<INPUT> : " + commande);
			Process pr = rt.exec(commande); // creates a new process for that command
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream())); // transform String command into an executable command

			String line = null;

			System.out.println("<OUTPUT>");
			while ((line = input.readLine()) != null) { // read output sent thanks to input
				System.out.println(line);
			}

			//int exitVal = pr.waitFor();
			//System.out.println("Exited with error code " + exitVal);

		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

			/*InputStream out= null;
			out= sftpChannel.get(remoteFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(out));
			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);
			br.close();*/
		}
		//catch(Exception e){System.err.print(e);}
	}
