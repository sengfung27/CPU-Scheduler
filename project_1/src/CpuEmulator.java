import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class CpuEmulator
{
    public static final int port = 6666;
    private ServerSocket ss = null;

    public void  runServer() throws IOException , ClassNotFoundException{
        ss = new ServerSocket(port);
        System.out.println("Connection Established.");
        
        Socket socket = ss.accept();
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        PCB process_pcb = (PCB) ois.readObject();
        System.out.println("CPU: Exec "
            + process_pcb.program_name + " "
            + Integer.toString(process_pcb.est_remain_time));
        minusCycle(process_pcb);
        
        oos.writeObject(process_pcb);
        socket.close();       
    }

    private void minusCycle(PCB process) {
        if(process.est_remain_time > 200)
        {
            process.est_remain_time -= 200;
        }
        else 
        {
            process.est_remain_time = 0;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        new CpuEmulator().runServer();
    }
}