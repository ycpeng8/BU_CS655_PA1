import java.net.*;
import java.io.*;

public class Client
{
    public static String wp =" ";
    public static void Send_CSP(){

    }
    public static void Send_MP(String PROBE_SEQUENCE_NUMBER, String MESSAGE_SIZE, PrintStream ps){
	    String ws=" ";
	    String MP_MSG = "s"+ws+PROBE_SEQUENCE_NUMBER+ws+MESSAGE_SIZE+ws+"\\n";
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
<<<<<<< HEAD
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
            
=======
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
            String SReturnCSPMSG = in.readLine();
            System.out.println("The server sends back the message: " + SReturnCSPMSG);
//            System.out.println("output byte array");
            if(SReturnCSPMSG.equals("200 OK: Ready")){
                String[] CSPParams = cspmsg.split("\\s+");
                String PROBE_SEQUENCE_NUMBER = CSPParams[2];
                String MESSAGE_SIZE = CSPParams[3];
                String MPMSG = "m"+wp+PROBE_SEQUENCE_NUMBER+wp+MESSAGE_SIZE;
                System.out.println("client send mpmsg "+MPMSG);
                out.println(MPMSG);
            }

            // Shutdown stream
//            stdIn.close();
            cspInput.close();
>>>>>>> f12da4362b932b2ddf6550bd90d62ff2089b1e35
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
