
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class CPUScheduling {
    public static void main(String[] args) throws IOException {
        Queue<PCB> schedulerTask = readFile(); // read process from file
        Queue<PCB> scheduler = null;
        int scheduling_case = Integer.parseInt(args[0]);
        // first come first serve algorithm - no pre-emption
        if (scheduling_case == 1) {
            scheduler = new LinkedList<>();
            System.out.println("First Come First Serve Scheduling - no pre-emption");
            schedulingNoPreEmption(schedulerTask, scheduler);
        }
        // shortest job next algorithm - no pre-emption
        else if (scheduling_case == 2) {
            scheduler = new PriorityQueue<PCB>(20, new ShortestJobComparator());
            System.out.println("Shortest Job Next Scheduling - no pre-emption");
            schedulingNoPreEmption(schedulerTask, scheduler);
        }
        // priority algorithm - no pre-emption
        else if (scheduling_case == 3) {
            scheduler = new PriorityQueue<PCB>(20, new PriorityComparator());
            System.out.println("Priority Scheduling - no pre-emption");
            schedulingNoPreEmption(schedulerTask, scheduler);
        }
        // round robin - first come first serve algorithm time quantum 10 unit
        else if (scheduling_case == 4) {
            scheduler = new LinkedList<>();
            System.out.println("Round Robin First Come First Serve Scheduling with time quantum: 10 units");
            schedulingPreEmption(schedulerTask, scheduler, 10);
        }
        // round robin - first come first serve algorithm time quantum 100 unit
        else if (scheduling_case == 5) {
            scheduler = new LinkedList<>();
            System.out.println("Round Robin First Come First Serve Scheduling with time quantum: 10 units");
            schedulingPreEmption(schedulerTask, scheduler, 100);
        }
        // round robin - first come first serve algorithm time quantum 1000 unit
        else if (scheduling_case == 6) {
            scheduler = new LinkedList<>();
            System.out.println("Round Robin First Come First Serve Scheduling with time quantum: 10 units");
            schedulingPreEmption(schedulerTask, scheduler, 1000);
        }
        // round robin - shortest job next algorithm time quantum 50 units
        else if (scheduling_case == 7) {
            scheduler = new PriorityQueue<PCB>(20, new ShortestJobComparator());
            System.out.println("Round Robin Shortest Job Next Scheduling");
            schedulingPreEmption(schedulerTask, scheduler, 50);
        }
        // round robin - priority algorithm time quantum 50 units
        else {
            scheduler = new PriorityQueue<PCB>(20, new PriorityComparator());
            System.out.println("Round Robin Priority Scheduling");
            schedulingPreEmption(schedulerTask, scheduler, 50);
        }
    }

    // Read file and add into a scheduler
    public static Queue<PCB> readFile() {
        Queue<PCB> scheduler = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("processes_3.txt"))) {

            // Read file line by line
            String line = "";
            while ((line = br.readLine()) != null) {
                scheduler.add(new PCB(line));
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return scheduler;

    }

    // No Pre Emption Algorithm will comes here
    public static void schedulingNoPreEmption(Queue<PCB> schedulerTask, Queue<PCB> scheduler) {
        int context_switches = 0; // number times of context switch
        int flag = 1; // indicate cpu status. 0 = running, 1 = ready. One process at a time
        int currentTime = 0; // clock, counter

        ArrayList<PCB> finishedPcbList = new ArrayList<PCB>(); // list of finished PCB to collect performance statistics

        PCB currentPcb = schedulerTask.remove(); // the first PCB in Scheduler queue
        PCB cpuPcb = null; // Running cpu PCB

        // keep cycle if both schedulerTask and scheduler has at least one process or
        // cpu is running
        while (schedulerTask.peek() != null || scheduler.peek() != null || flag == 0) {
            // PCB added into scheduler when it's time
            if (currentTime == currentPcb.arrival_time) {
                scheduler.add(currentPcb);
                System.out.println("Scheduler: " + currentPcb.program_name + " is entered the scheduling Queue at time "
                        + currentTime);
                if (schedulerTask.peek() != null) {
                    currentPcb = schedulerTask.remove();
                }

                // first process to come in
                if (flag == 1 && scheduler.peek() != null) {
                    cpuPcb = scheduler.remove();
                    System.out.println(
                            "Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name + " at time " + currentTime);
                    flag = 0;
                }

            }

            // CPU Emulator is running a process
            if (flag == 0) {

                // decrement the process in cpu if it's not 0
                if (cpuPcb.est_remain_time != 0) {
                    cpuPcb.est_remain_time -= 1;
                    cpuPcb.turnaround_time += 1;
                    // increment 1 unit of wait time and turn around time in scheduler process.
                    if (scheduler.peek() != null) {
                        for (PCB waiting_block : scheduler) {
                            waiting_block.total_waiting_time += 1; // 1 unit wait time for process in scheduler
                            waiting_block.turnaround_time += 1; // 1 unit turnaround_time for process in scheduler

                        }
                    }
                }

                // One process finished in cpu
                else {

                    finishedPcbList.add(cpuPcb);

                    System.out.println("Cpu Emulator: " + cpuPcb.program_name + " is finished at time " + currentTime
                            + ". Estimate Remaining Time: " + cpuPcb.est_remain_time);
                    // Scheduler has at least one process
                    if (scheduler.peek() != null) {
                        context_switches += 1; // context switch to next process
                        currentTime += 2; // add 2 units context switch
                        for (PCB waiting_block : scheduler) {
                            waiting_block.total_waiting_time += 2; // 2 unit wait time for process in scheduler
                            waiting_block.turnaround_time += 2; // 2 unit turnaround_time for process in scheduler
                        }
                        cpuPcb = scheduler.remove(); // load the next PCB to cpu
                        System.out.println("Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name + " at time "
                                + currentTime + ". Waited time: " + cpuPcb.total_waiting_time);
                        cpuPcb.est_remain_time -= 1; // decrement estimate remaining time
                        // decrement other property in scheduler

                    }

                    // finished all the process in cpu emulator and scheduler and print it out
                    else {

                        flag = 1;
                        int sum_wait_time = 0;
                        int sum_turnaround_time = 0;
                        int longest_wait_time = 0;
                        for (PCB finished_block : finishedPcbList) {
                            sum_wait_time += finished_block.total_waiting_time;
                            sum_turnaround_time += finished_block.turnaround_time;
                            if (longest_wait_time < finished_block.total_waiting_time) {
                                longest_wait_time = finished_block.total_waiting_time;
                            }
                        }
                        System.out.println("Cpu Scheduling Finished.");
                        System.out.println("Total time used: " + currentTime);
                        System.out.println("Context switch in total: " + context_switches);
                        System.out.println("Longest wait time: " + longest_wait_time);
                        System.out.println("Average wait time: " + sum_wait_time / 14.0);
                        System.out.println("Average turnaround time: " + sum_turnaround_time / 14.0);
                        System.out.println(
                                "---------------------------------------------------------------------------------------------");
                        try (FileWriter fw = new FileWriter("myfile.txt", true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                PrintWriter out = new PrintWriter(bw)) {
                            out.print((sum_turnaround_time / 14.0) + ",");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            currentTime += 1;
        }
    }

    // Pre Emption Algorithm will comes here
    public static void schedulingPreEmption(Queue<PCB> schedulerTask, Queue<PCB> scheduler, int timeQuantum) {
        int context_switches = 0; // number times of context switch
        int flag = 1; // indicate cpu status. 0 = running, 1 = ready. One process at a time
        int currentTime = 0; // clock, counter
        int fixedTime = timeQuantum;
        ArrayList<PCB> finishedPcbList = new ArrayList<PCB>(); // list of finished PCB to collect performance statistics

        PCB currentPcb = schedulerTask.remove(); // the first PCB in Scheduler queue
        PCB cpuPcb = null; // Running cpu PCB

        while (schedulerTask.peek() != null || scheduler.peek() != null || flag == 0) {
            if (currentTime == currentPcb.arrival_time) {
                scheduler.add(currentPcb);
                System.out.println("Scheduler: " + currentPcb.program_name + " is entered the scheduling Queue at time "
                        + currentTime);
                if (schedulerTask.peek() != null) {
                    currentPcb = schedulerTask.remove();
                }

                // first process to come in
                if (flag == 1 && scheduler.peek() != null) {
                    cpuPcb = scheduler.remove();
                    System.out.println(
                            "Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name + " at time " + currentTime);
                    flag = 0;
                }

            }
            // cpu is in running mode
            if (flag == 0) {
                // the cpu PCB is not zero
                if (cpuPcb.est_remain_time != 0) {
                    // check if the time quantum is zero
                    if (fixedTime != 0) {
                        cpuPcb.est_remain_time -= 1;
                        cpuPcb.turnaround_time += 1;
                        fixedTime -= 1;
                        // increment 1 unit of wait time and turn around time in scheduler process.
                        if (scheduler.peek() != null) {
                            for (PCB waiting_block : scheduler) {
                                waiting_block.total_waiting_time += 1; // 1 unit wait time for process in scheduler
                                waiting_block.turnaround_time += 1; // 1 unit turnaround_time for process in scheduler

                            }
                        }
                    }
                    // time quantum = 0, context switch to next ready process
                    else {
                        PCB contextPcb = null;
                        System.out.println("Cpu Emulator: Out of time quantum, switching out " + cpuPcb.program_name
                                + " at time " + currentTime + ". Estimate Remaining Time: " + cpuPcb.est_remain_time);
                        context_switches += 1; // context switch to next process
                        currentTime += 1; // add 1 units context switch
                        // check if any new process comes
                        if (currentTime == currentPcb.arrival_time) {
                            contextPcb = currentPcb;
                            contextPcb.total_waiting_time += 1;
                            contextPcb.turnaround_time += 1;
                            // scheduler.add(currentPcb);
                            System.out.println("Scheduler: " + contextPcb.program_name
                                    + " is entered the scheduling Queue at time " + currentTime);
                            if (schedulerTask.peek() != null) {
                                currentPcb = schedulerTask.remove();
                            }
                        }
                        // add one unit of wait time an turnaround time in ready scheduler
                        if (scheduler.peek() != null) {

                            for (PCB waiting_block : scheduler) {
                                waiting_block.total_waiting_time += 1; // 1 unit wait time for process in scheduler
                                waiting_block.turnaround_time += 1; // 1 unit turnaround_time for process in scheduler
                            }
                        }
                        // one process arrives during context switch
                        if (contextPcb != null) {
                            scheduler.add(contextPcb);
                            contextPcb = null;
                        }
                        currentTime += 1; // add 1 units context switch
                        // check if any new process comes
                        if (currentTime == currentPcb.arrival_time) {
                            contextPcb = currentPcb;
                            contextPcb.total_waiting_time += 1;
                            contextPcb.turnaround_time += 1;
                            // scheduler.add(currentPcb);
                            System.out.println("Scheduler: " + contextPcb.program_name
                                    + " is entered the scheduling Queue at time " + currentTime);
                            if (schedulerTask.peek() != null) {
                                currentPcb = schedulerTask.remove();
                            }
                        }
                        // add one unit of wait time an turnaround time in ready scheduler
                        if (scheduler.peek() != null) {

                            for (PCB waiting_block : scheduler) {
                                waiting_block.total_waiting_time += 1; // 1 unit wait time for process in scheduler
                                waiting_block.turnaround_time += 1; // 1 unit turnaround_time for process in scheduler
                            }
                        }
                        // one process arrives during context switch
                        if (contextPcb != null) {
                            scheduler.add(contextPcb);
                            contextPcb = null;
                        }

                        cpuPcb.total_waiting_time += 2; // add total wait time for cpu pcb
                        cpuPcb.turnaround_time += 2; // add turn around time for cpu pcb
                        if (scheduler.peek() != null) {

                            PCB temp = cpuPcb;
                            cpuPcb = scheduler.remove(); // load the next PCB to cpu
                            scheduler.add(temp);
                        }
                        fixedTime = timeQuantum;
                        System.out.println("Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name + " at time "
                                + currentTime + ". Estimate Remaining Time: " + cpuPcb.est_remain_time);
                        cpuPcb.est_remain_time -= 1; // decrement estimate remaining time
                        fixedTime -= 1;
                    }

                }
                // One process finished in cpu
                else {

                    finishedPcbList.add(cpuPcb);

                    System.out.println("Cpu Emulator: " + cpuPcb.program_name + " is finished at time " + currentTime
                            + ". Waited time: " + cpuPcb.total_waiting_time);
                    // Scheduler has at least one process
                    if (scheduler.peek() != null) {
                        context_switches += 1; // context switch to next process
                        currentTime += 1;
                        if (currentTime == currentPcb.arrival_time) {
                            scheduler.add(currentPcb);
                            System.out.println("Scheduler: " + currentPcb.program_name
                                    + " is entered the scheduling Queue at time " + currentTime);
                            if (schedulerTask.peek() != null) {
                                currentPcb = schedulerTask.remove();
                            }
                        }
                        currentTime += 1; // add 1 units context switch
                        if (currentTime == currentPcb.arrival_time) {
                            scheduler.add(currentPcb);
                            System.out.println("Scheduler: " + currentPcb.program_name
                                    + " is entered the scheduling Queue at time " + currentTime);
                            if (schedulerTask.peek() != null) {
                                currentPcb = schedulerTask.remove();
                            }
                        }
                        for (PCB waiting_block : scheduler) {
                            waiting_block.total_waiting_time += 2; // 2 unit wait time for process in scheduler
                            waiting_block.turnaround_time += 2; // 2 unit turnaround_time for process in scheduler
                        }
                        cpuPcb = scheduler.remove(); // load the next PCB to cpu
                        fixedTime = timeQuantum;
                        System.out.println("Cpu Emulator: Cpu is ready to process " + cpuPcb.program_name + " at time "
                                + currentTime + ". Estimate Remaining Time: " + cpuPcb.est_remain_time);
                        cpuPcb.est_remain_time -= 1; // decrement estimate remaining time
                        fixedTime -= 1;

                    }

                    // finished all the process in cpu emulator and scheduler and print it out
                    else {

                        flag = 1;
                        int sum_wait_time = 0;
                        int sum_turnaround_time = 0;
                        int longest_wait_time = 0;
                        for (PCB finished_block : finishedPcbList) {
                            sum_wait_time += finished_block.total_waiting_time;
                            sum_turnaround_time += finished_block.turnaround_time;
                            if (longest_wait_time < finished_block.total_waiting_time) {
                                longest_wait_time = finished_block.total_waiting_time;
                            }
                        }
                        System.out.println("Cpu Scheduling Finished.");
                        System.out.println("Total time used: " + currentTime);
                        System.out.println("Context switch in total: " + context_switches);
                        System.out.println("Longest wait time: " + longest_wait_time);
                        System.out.println("Average wait time: " + sum_wait_time / 14.0);
                        System.out.println("Average turnaround time: " + sum_turnaround_time / 14.0);
                        System.out.println(
                                "---------------------------------------------------------------------------------------------");
                        try (FileWriter fw = new FileWriter("myfile.txt", true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                PrintWriter out = new PrintWriter(bw)) {
                            out.print((sum_turnaround_time / 14.0) + ",");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            currentTime += 1;
        }
    }
}
