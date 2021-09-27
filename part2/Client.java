import java.net.*;
import java.io.*;

public class Client
{
    public static void Send_CSP(){

    }
    public static void Send_MP(int PROBE_SEQUENCE_NUMBER, int MESSAGE_SIZE, PrintStream ps){
	    String ws=" ";
	    String MP_MSG = "s"+ws+String.valueOf(PROBE_SEQUENCE_NUMBER)+ws+String.valueOf(MESSAGE_SIZE)+ws+"\\n";
	}
    public static void main(String[] args) throws IOException
    {
        // Get hostname and portnumber from command line
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try
        {
            // Establish socket
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("---------- Connect successfully with the server "+ hostName + ": " + portNumber + " ----------");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine = null;
            int i = 0;
            while(i < 3)
            {
                String cspmsg = "s rtt 2 2 2";
                System.out.println("client send new msg: "+cspmsg);
                out.writeBytes(cspmsg + '\n');
                out.flush();
                inputLine = in.readLine();
                System.out.println("The server sends back the message: " + inputLine);
                i++;
            }
            
            in.close();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("---------- Client closes ----------");
        }
    }
}
