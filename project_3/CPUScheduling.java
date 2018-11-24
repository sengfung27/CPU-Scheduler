
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.ClassNotFoundException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class CPUScheduling
{
    // Read file and add into a scheduler 
    public static Queue<PCB> readFile()
    {
        Queue<PCB> scheduler = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("processes_3.txt")))
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

        }
        return scheduler;
        
    }

    // First Come First Serve Scheduling
    public static void firstComeFirstServe(Queue<PCB> schedulerTask)
    {
        int context_switches = 0; // number times of context switch 
        int flag = 1;        // indicate cpu status. 0 = running, 1 = ready. One process at a time

        int currentTime = 0; // clock, counter
        ArrayList<PCB> finishedPcbList = new ArrayList<PCB>();
        Queue<PCB> scheduler = new LinkedList<>();  // scheduling algorithm queue
        PCB currentPcb = schedulerTask.remove();    // the first PCB in Scheduler queue

        PCB cpuPcb = null;                          // Running cpu PCB 
        
        while (schedulerTask.peek() != null || scheduler.peek() != null || flag == 0)
        {
            if (currentTime == currentPcb.arrival_time)
            {
                scheduler.add(currentPcb);
                System.out.println("Scheduler: " + currentPcb.program_name 
                + " is entered the scheduling Queue at time " + currentTime);
                if (schedulerTask.peek() != null)
                {
                    currentPcb = schedulerTask.remove();
                }

                // first process to come in
                if (flag == 1)
                {
                    cpuPcb = scheduler.remove();
                    System.out.println("Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name 
                    + " at time " + currentTime);
                    flag = 0;
                    cpuPcb.is_first_time = false;
                }

            }
            
            // CPU Emulator is running a process
            if (flag == 0)
            {

                // decrement the process if it's not 0 
                if (cpuPcb.est_remain_time != 0)
                {
                    cpuPcb.est_remain_time -= 1;
                    scheduler = decrementScheduler(scheduler, 1);
                }

                // One process finished in cpu
                else
                {
                    finishedPcbList.add(cpuPcb);
                    
                    System.out.println("Cpu Emulator: " + cpuPcb.program_name +
                                    " is finished at time " + currentTime +
                                    ". Waiting Time: "
                                    + cpuPcb.total_waiting_time 
                                    + ". Estimate Remaining Time: "
                                    + cpuPcb.est_remain_time);
                    // Scheduler has at least one process 
                    if (scheduler.peek() != null)
                    {
                        context_switches += 1;    // context switch to next process
                        currentTime += 2;         // add 2 units context switch
                        cpuPcb = scheduler.remove();  // load the next PCB to cpu 
                        System.out.println("Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name 
                        + " at time " + currentTime + ". Waited time: " + cpuPcb.total_waiting_time);
                        cpuPcb.is_first_time = false;  // no longer first time to load in cpu
                        cpuPcb.est_remain_time -= 1;   // decrement estimate remaining time
                        scheduler = decrementScheduler(scheduler, 3); // decrement other property in scheduler
                    }

                    // finished all the process and print it out
                    else
                    {
                        
                        flag = 1;
                        int sum_wait_time = 0;
                        int sum_turnaround_time = 0;
                        int longest_wait_time = 0;
                        for (PCB finished_block : finishedPcbList)
                        {
                            sum_wait_time += finished_block.total_waiting_time;
                            sum_turnaround_time += finished_block.turnaround_time;
                            if (longest_wait_time < finished_block.total_waiting_time)
                            {
                                longest_wait_time = finished_block.total_waiting_time;
                            }
                        }
                        System.out.println("Cpu Scheduling Finished.");
                        System.out.println("Total time used: " + currentTime);
                        System.out.println("Context switch in total: " + context_switches);
                        System.out.println("Longest wait time: " + longest_wait_time);
                        System.out.println("Average wait time: " + sum_wait_time/14 );
                        System.out.println("Average turnaround time: " + sum_turnaround_time/14 );
                    }
                }
            }
            currentTime += 1;
        }
    }

    // decrement other property 
    public static Queue<PCB> decrementScheduler(Queue<PCB> scheduler, int value)
    {
        Queue<PCB> new_scheduler = new LinkedList<>();
        if (scheduler.peek() == null)
        {
            new_scheduler = scheduler;
            return new_scheduler;
        }
        for (PCB waiting_block : scheduler)
        {
            waiting_block.total_waiting_time += value;  // 1 + 2 unit context switch
            if (!waiting_block.is_first_time)
            {
                waiting_block.turnaround_time += value;  // 1 + 2 unit context switch
            }
            new_scheduler.add(waiting_block);
        }

        return new_scheduler;
    }


    public static void main(String [] args)
    {
        
        Queue<PCB> scheduler = readFile();

        firstComeFirstServe(scheduler);
        
    }
}