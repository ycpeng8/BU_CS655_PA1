import java.net.*;
import java.io.*;

public class MultiThread extends Thread
{
    private Socket socket = null;
    private String CSPreturnMsg;

    private boolean termination = false;

    public MultiThread(Socket socket)
    {
        super("MultiThread");
        this.socket = socket;
    }
    public void MPMSGCheck(String  MPmsg){

    }
    public void CSPMsgCheck(String cspmsg, PrintStream out){
        String[] CSPValidation = cspmsg.split("\\s+");
        if(!CSPValidation[CSPValidation.length-1].equals("\\n") ||
                CSPValidation.length != 6 ||
                !CSPValidation[0].equals("s")||
                (!CSPValidation[1].equals("rtt") && !CSPValidation[1].equals("tput"))||
                !isInt(CSPValidation[2])||
                !isInt(CSPValidation[3])||
                !isInt(CSPValidation[4])
        ){
            CSPreturnMsg = "404 Error";
            out.println(CSPreturnMsg);
        }else{
            CSPreturnMsg = "200 OK: Ready";
            out.println(CSPreturnMsg);
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
            PrintStream out = new PrintStream(socket.getOutputStream(), true);
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
                CSPMsgCheck(inputLine,out);

//                System.out.println("test");
//                if(!termination){
//                    inputLine = in.readLine();
//                    System.out.println("test");
//                    System.out.println(inputLine);
//                }
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