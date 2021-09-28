import java.net.*;
import java.io.*;

public class MultiThread extends Thread
{
    private Socket socket = null;

    public MultiThread(Socket socket)
    {
        super("MultiThread");
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            // Establish input and output streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine;
            while((inputLine = in.readLine()) != null)  // Receive the message from client
            {
                System.out.println("Receive a message from the client: " + inputLine);
                outputLine = inputLine;
                // Send the message to client
                out.println(outputLine);
                System.out.println("---------- The same message is sent back ----------");
            }
            in.close();
            out.close();
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("---------- One client closes ----------");
        }
    }
}