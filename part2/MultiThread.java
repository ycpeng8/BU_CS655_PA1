import java.net.*;
import java.io.*;

public class MultiThread extends Thread
{
    private Socket socket = null;
    private String CSPreturnMsg;
    private String MPreturnMsg;
    private int SEQ_PROBE_NUMBER=1;
    private int PROBE_NUMBER;
    private int MEG_SIZE;
    private boolean CSP = true;
    private boolean MP = true;
    private boolean terminate = false;
    private long ServerDelay = 0;
    private String Con_Close = "---------- One client closes ----------";

    public MultiThread(Socket socket)
    {
        super("MultiThread");
        this.socket = socket;
    }
    // Validate CTP message
    public void CTPMsgCheck(String[] CTPmsg, DataOutputStream out ) throws IOException {
        if(CTPmsg.length != 1){
            out.writeBytes("404 Error: Invalid Connection Termination Message" + '\n');
            out.flush();

        }else{
            out.writeBytes("200 OK: Closing Connection" + '\n');
            out.flush();

        }
        terminate = true;

    }
    // if client sends invalid message, Server will terminate the connection
    public void terminate(DataOutputStream out) throws IOException {
        out.writeBytes("404 Error" + '\n');
        out.flush();
        terminate = true;
        Con_Close = "---------- Server closes ----------";


    }
    // Validate MP message
    public void MPMsgCheck(String[] mpmsg ,DataOutputStream out) throws IOException, InterruptedException{
        if(!mpmsg[0].equals("m") ||
                (SEQ_PROBE_NUMBER<=PROBE_NUMBER && !isInt(mpmsg[1]) && Integer.parseInt(mpmsg[1]) != SEQ_PROBE_NUMBER)
                || mpmsg.length == MEG_SIZE){
            terminate(out);
        }else{
            // if MP message is valid, then server will echo back the message to client
            sleep(ServerDelay);
            MPreturnMsg = mpmsg[0]+" "+SEQ_PROBE_NUMBER+" "+mpmsg[2];
            out.writeBytes(MPreturnMsg+"\n");
            out.flush();
            SEQ_PROBE_NUMBER++;

        }
    }
    // Validate CSP message
    public void CSPMsgCheck(String[] cspmsg,DataOutputStream out) throws IOException {
        String[] CSPValidation = cspmsg;
        if(     !CSP || CSPValidation.length != 5 ||
                (!CSPValidation[1].equals("rtt") && !CSPValidation[1].equals("tput"))||
                !isInt(CSPValidation[2])||
                !isInt(CSPValidation[3])||
                !isInt(CSPValidation[4])
        ){
            // if csp message is invalid, Server terminates the connection
            terminate(out);

        }else{
            // if CSP message is valid, then server will send back a message to client and set probe parameter
            ServerDelay = Long.parseLong(cspmsg[4]);
            CSPreturnMsg = "200 OK: Ready";
            PROBE_NUMBER = Integer.parseInt(CSPValidation[2]);
            MEG_SIZE = Integer.parseInt(CSPValidation[3]);
            out.writeBytes(CSPreturnMsg + '\n');
            out.flush();
        }
    }
    // check if the string can be converted to integer
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
    // Server will keep reading message from client and decide which phase will be executed
    public void run()
    {
        try
        {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine = null;
            while((inputLine = in.readLine()) != null && !terminate)
            {
                String[] inputMSG = inputLine.split("\\s+");
                System.out.println("----Client sents "+inputLine+" ----");
                String PhaseIndicator = inputMSG[0];
                // Check the phase indicator
                if(PhaseIndicator.equals("s")){
                    CSPMsgCheck(inputMSG,out);
                }else if(PhaseIndicator.equals("m")){
                    MPMsgCheck(inputMSG,out);
                }else if(PhaseIndicator.equals("t")){
                    CTPMsgCheck(inputMSG, out);
                }else {
                    terminate(out);
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
            System.out.println(Con_Close);
        }
    }
}