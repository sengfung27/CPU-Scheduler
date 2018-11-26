public class PCB 
{ 
    public int arrival_time;
    public String program_name;
    public int PID;
    public String state; 
    public int priority;
    public int interruptable; 
    public int est_run_time;
    public int est_remain_time; 
    public int total_waiting_time; 
    public int turnaround_time; 
    public int time_quantum;
    public PCB(String line)
    {
        String[] split = line.split(",");
        arrival_time = Integer.parseInt(split[0]);
        program_name = split[1];
        PID = Integer.parseInt(split[2]);
        state = split[3];
        priority = Integer.parseInt(split[4]);
        interruptable = Integer.parseInt(split[5]);
        est_run_time = Integer.parseInt(split[6]);
        est_remain_time = Integer.parseInt(split[7]);
        total_waiting_time = 0;
        turnaround_time = 0;
    }

}