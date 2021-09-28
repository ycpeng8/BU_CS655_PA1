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

    public MultiThread(Socket socket)
    {
        super("MultiThread");
        this.socket = socket;
    }
    public void CTPMsgCheck(String[] CTPmsg, DataOutputStream out ) throws IOException {
        if(CTPmsg.length != 1){
            out.writeBytes("404 Error: Invalid Connection Termination Message" + '\n');
            out.flush();
            socket.close();
        }else{
            out.writeBytes("200 OK: Closing Connection" + '\n');
            out.flush();
            socket.close();
        }
    }
    public void terminate(DataOutputStream out) throws IOException {
        out.writeBytes("404 Error" + '\n');
        out.flush();
        socket.close();
    }
    public void MPMsgCheck(String[] mpmsg ,DataOutputStream out) throws IOException{
        if(!mpmsg[0].equals("m") ||
                (SEQ_PROBE_NUMBER<=PROBE_NUMBER && !isInt(mpmsg[1]) && Integer.parseInt(mpmsg[1]) != SEQ_PROBE_NUMBER)
                || mpmsg.length == MEG_SIZE){
            terminate(out);
        }else{
            MPreturnMsg = mpmsg[0]+" "+SEQ_PROBE_NUMBER+" "+mpmsg[2];
            out.writeBytes(MPreturnMsg+"\n");
            out.flush();
            SEQ_PROBE_NUMBER++;

        }
    }
    public void CSPMsgCheck(String[] cspmsg,DataOutputStream out) throws IOException {
        String[] CSPValidation = cspmsg;
        if(     !CSP || CSPValidation.length != 5 ||
                (!CSPValidation[1].equals("rtt") && !CSPValidation[1].equals("tput"))||
                !isInt(CSPValidation[2])||
                !isInt(CSPValidation[3])||
                !isInt(CSPValidation[4])
        ){
            terminate(out);
//            CSPreturnMsg = "404 Error";
//            out.writeBytes(CSPreturnMsg + '\n');
//            out.flush();
        }else{
            CSPreturnMsg = "200 OK: Ready";
            PROBE_NUMBER = Integer.parseInt(CSPValidation[2]);
            MEG_SIZE = Integer.parseInt(CSPValidation[3]);
            out.writeBytes(CSPreturnMsg + '\n');
            out.flush();
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
                String[] inputMSG = inputLine.split("\\s+");
                System.out.println("----Client sents "+inputLine+" ----");
                String PhaseIndicator = inputMSG[0];
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
            System.out.println("---------- One client closes ----------");
        }
    }
}