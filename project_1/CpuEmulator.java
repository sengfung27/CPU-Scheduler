import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class CpuEmulator
{
    public static final int port = 6666;
    //public static final ServerSocket ss = null;

    public static void minusCycle(PCB process) {
        if(process.est_remain_time > 200)
        {
            process.est_remain_time -= 200;
        }
        else 
        {
            process.est_remain_time = 0;
        }
    }

    public static void main(String[] args) {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        ServerSocket ss = null;

        try 
        {
            ss = new ServerSocket(6666);
            System.out.println("Connection Established.");
            while(true)
            {   
                socket = ss.accept();
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                PCB process_pcb = (PCB)ois.readObject();
                System.out.println("CPU: Exec "
                    + process_pcb.program_name + ", "
                    + Integer.toString(process_pcb.PID) + ", "
                    + process_pcb.state + ", "
                    + Integer.toString(process_pcb.priority) + ", "
                    + Integer.toString(process_pcb.interruptable) + ", "
                    + Integer.toString(process_pcb.est_run_time) + ", "
                    + Integer.toString(process_pcb.est_remain_time) + " ");
                minusCycle(process_pcb);
                
                oos.writeObject(process_pcb);
                oos.flush();  
            }
            
        }
        
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        finally
        {
            try
            {
                if(ss != null)
                    ss.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}