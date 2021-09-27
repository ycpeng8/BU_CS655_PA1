import java.net.*;
import java.io.*;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        // Get portnumber from command Line
        int portNumber = Integer.parseInt(args[0]);

        try
        {
            // Establish server socket
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("---------- Server is ready ----------");

            // Establish connection
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("---------- A client joins in ----------");
                new MultiThread(clientSocket).start();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("---------- Server closes ----------");
        }
    }
}