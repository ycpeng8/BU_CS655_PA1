import java.net.*;
import java.io.*;

public class Client
{
    public static String wp =" ";
    public static void send_CSP(){

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
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            // Set the configuraiton of the test
            System.out.print("Please enter the size(bytes) of every message sent: ");
            int mSize = Integer.valueOf(stdIn.readLine());
            System.out.print("Please enter the number of probe messages sent: ");
            int noProbe = Integer.valueOf(stdIn.readLine());
            System.out.print("Please enter the measure type(rtt or tput): ");
            String mType = stdIn.readLine();
            System.out.print("Please enter the expected server delay(seconds): ");
            int sDelay = Integer.valueOf(stdIn.readLine());

            if(CSP(mSize, noProbe, mType, sDelay, out, in))
            {
                System.out.println("next...");
            }
            
            stdIn.close();
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

    // Connection Setup Phase
    public static boolean CSP(int mSize, int noProbe, String mType, int sDelay, 
                        DataOutputStream out, BufferedReader in)
    {
        try
        {
            String CSPmessage = "s " + mType + " " + Integer.toString(noProbe) + " "
                                + Integer.toString(mSize) + " " + Integer.toString(sDelay);
            System.out.println("CSP(send): " + CSPmessage);
            out.writeBytes(CSPmessage + '\n');
            out.flush();
            String inputLine = in.readLine();
            System.out.println("CSP(receive): " + inputLine);
            if(inputLine.equals("200 OK: Ready"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return false;
        }
    }
}
