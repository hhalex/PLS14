import java.io.*;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class CommandLine {
	public static void main(String args[]) {
		try {
			Runtime rt = Runtime.getRuntime();
			String commande = "";

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
	}
}
