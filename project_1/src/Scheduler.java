import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.ClassNotFoundException;

public class Scheduler
{
    public static void main(String[] args) throws 
            IOException, ClassNotFoundException
    {

        ArrayList<PCB> processes = new ArrayList<PCB>();
        Queue<PCB> scheduler = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("processes.txt")))
        {

            // Read file line by line
            String line = "";
            while ((line = br.readLine()) != null) {

                // String[] data = this.parseLine(line);
                PCB process = new PCB(line);

                processes.add(process);
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            processes.removeAll(processes);
            
        }

        for(PCB i : processes)
        {
            System.out.println(i.program_name);
            System.out.println("Now pushing to Queue.");
            scheduler.add(i);
            System.out.println(scheduler.peek());
        }
        System.out.println("welcome client");
        Socket socket = new Socket("127.0.0.1", 6666);
        System.out.println("Client connected");
        while (scheduler.peek() != null)
        {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(scheduler.remove());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            PCB return_process = (PCB) ois.readObject();

            if (return_process.est_remain_time == 0)
            {
                System.out.println("Scheduler: Process " + return_process.PID + " " + return_process.program_name + " Completed.");
            }
            else
            {
                scheduler.add(return_process);
            }
        }
        socket.close();
    }

}