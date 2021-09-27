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

    public void CSPMsgCheck(String cspmsg){
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
            termination = true;
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
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine = null;
            while((inputLine = in.readLine()) != null)
            {
                System.out.println("----Client sents "+inputLine+" ----");
                CSPMsgCheck(inputLine);
                out.writeBytes(CSPreturnMsg + '\n');
                out.flush();
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