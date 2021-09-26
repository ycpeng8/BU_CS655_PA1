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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            // User write, send and receive messages
//            String userInput = stdIn.readLine();
//            out.println(userInput);
//            System.out.println("The server sends back the message: " + in.readLine());
            System.out.println("input csp message");
            BufferedReader cspInput = new BufferedReader(new InputStreamReader(System.in));
            String cspmsg = cspInput.readLine();
            System.out.println("client send new msg "+cspmsg);
            out.println(cspmsg);
            System.out.println("The server sends back the message: " + in.readLine());

            // Shutdown stream
//            stdIn.close();
            cspInput.close();
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
