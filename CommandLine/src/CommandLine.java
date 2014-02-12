import java.io.*;

public class CommandLine
{
   public static void main(String args[])
   {
      try
      {
         Runtime rt = Runtime.getRuntime();
         String commande = "ls -lisa";

      
         System.out.println("<INPUT> : " + commande);
         Process pr = rt.exec(commande);

         BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

         String line = null;

         System.out.println("<OUTPUT>");
         while ((line = input.readLine()) != null) {
        	 System.out.println(line);
         }

         int exitVal = pr.waitFor();
         //System.out.println("Exited with error code " + exitVal);

      }
      catch (Exception e) {
         System.out.println(e.toString());
         e.printStackTrace();
      }
   }
}