import java.net.*;
import java.io.*;

public class MultiThread extends Thread
{
    private Socket socket = null;
    private String CSPreturnMsg;

    public MultiThread(Socket socket)
    {
        super("MultiThread");
        this.socket = socket;
    }

    public void CSPMsgCheck(String cspmsg){
        String[] CSPValidation = cspmsg.split("\\s+");
        if(!CSPValidation[CSPValidation.length-1].equals("\\n") ||
                CSPValidation.length != 6 ||
                !CSPValidation[0].equals("s")||
                !CSPValidation[1].equals("rtt")||
                !isInt(CSPValidation[2])||
                !isInt(CSPValidation[3])||
                !isInt(CSPValidation[4])
        ){
            CSPreturnMsg = "404 Error";
        }else{
            CSPreturnMsg = "200 OK: Ready";
        }
    }

    public boolean isInt(String s){
        try{
            Integer.parseInt(s);
        }catch (NumberFormatException e){
            return false;
        }catch(NullPointerException e){
            return false;
        }
        return true;
    }

    public void run()
    {
        try
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine;
//            inputLine = in.readLine();

            while((inputLine = in.readLine()) != null)
            {
//                System.out.println("Receive a message from the client: " + inputLine);
//                outputLine = inputLine;
//                out.println(outputLine);
//                System.out.println("---------- The same message is sent back ----------");
                System.out.println("----Client sents "+inputLine+" ----");
                CSPMsgCheck(inputLine);
                out.println(CSPreturnMsg);
                if(){
                    mp()
                }

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