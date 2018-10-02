import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.LinkedList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.ClassNotFoundException;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Scheduler
{
    public static Socket socket = null;
    public static void main(String[] args) 
    {
        // Priority Scheduler
        if(args.length > 0)
        {
            priorityScheduling();
        }
        // default: Round Robin Scheduler
        else
        {        
            roundRobinScheduling();
        }

    }

    public static void priorityScheduling()
    {
        Comparator<PCB> comparator = new PriorityComparator();
        PriorityQueue<PCB> scheduler = new PriorityQueue<PCB>(20, comparator);

        try (BufferedReader br = new BufferedReader(new FileReader("processes.txt")))
        {

            // Read file line by line
            String line = "";
            while ((line = br.readLine()) != null)
            {
                scheduler.add(new PCB(line));
            }
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            scheduler.removeAll(scheduler);
        }
        int priority_count = 1;
        Queue<PCB> priority_queue = new LinkedList<>();
        while(scheduler.peek() != null)
        {
            if(scheduler.peek().priority == priority_count)
            {
                priority_queue.add(scheduler.remove());
                //Check last priority elements
                if (scheduler.peek() == null)
                {
                    priorityQueueScheduling(priority_queue);
                }
            }
            else 
            {
                priorityQueueScheduling(priority_queue);
                priority_count += 1; 
            }
        }
        
    }

    public static void priorityQueueScheduling(Queue<PCB> priority_queue)
    {
        while(priority_queue.peek() != null)
        {
            PCB first_queue = priority_queue.remove();
            PCB return_process = connect(first_queue);
            if (return_process.est_remain_time == 0)
                System.out.println("Scheduler: Process " + return_process.PID + ", " + return_process.program_name + " Completed.");
            else
                priority_queue.add(return_process);
        }
    }

    public static void roundRobinScheduling()
    {
        Queue<PCB> scheduler = new LinkedList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("processes.txt")))
        {

            // Read file line by line
            String line = "";
            while ((line = br.readLine()) != null)
            {
                scheduler.add(new PCB(line));
            }
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            scheduler.removeAll(scheduler);
        }

        while(scheduler.peek() != null)
        {
            PCB first_queue = scheduler.remove();
            PCB return_process = connect(first_queue);
            if (return_process.est_remain_time == 0)
                System.out.println("Scheduler: Process " + return_process.PID + ", " + return_process.program_name + " Completed.");
            else
                scheduler.add(return_process);
        }
    }

        
    public static PCB connect(PCB pcb)
    {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        PCB return_process = null;
        try
        {
            socket = new Socket("127.0.0.1", 6666);
            oos  = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(pcb);

            ois = new ObjectInputStream(socket.getInputStream());
            return_process = (PCB) ois.readObject();
            socket.close();
        }

        catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        
        return return_process;
    }
}