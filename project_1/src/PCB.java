package pcb_package;

public class PCB 
{
    public String program_name;
    public int PID;
    public String state;
    public int priority;
    public int interruptable;
    public int est_run_time;
    public int est_remain_time;
    
    public PCB(String line) {
        String[] split = line.split(",");
        program_name = split[0];
        PID = Integer.parseInt(split[1]);
        state = split[2];
        priority = Integer.parseInt(split[3]);
        interruptable = Integer.parseInt(split[4]);
        est_run_time = Integer.parseInt(split[5]);
        est_remain_time = Integer.parseInt(split[6]);
    }
    // public PCB(String program_name,
    //  int PID,
    //  String state,
    //  Boolean priority,
    //  Boolean interruptable,
    //  int est_run_time,
    //  int est_remain_time)
    // {
    //     this.setProgramName(program_name);
    //     this.setPID(PID);
    //     this.setState(state);
    //     this.setPriority(priority);
    //     this.setInterruptable(interruptable);
    //     this.setEstRunTime(est_run_time);
    //     this.setEstRemainTime(est_remain_time);
    // }



    // public String getProgramName()
    // {
    //     return program_name;
    // }
    // public void setProgramName(String value)
    // {
    //     this.program_name = value;
    // }
    // public int getPID()
    // {
    //     return PID;
    // }
    // public void setPID(int value)
    // {
    //     this.PID = value;
    // }
    // public String getState()
    // {
    //     return state;
    // }
    // public void setState(String value)
    // {
    //     this.state = value;
    // }
    // public Boolean getPriority()
    // {
    //     return priority;
    // }
    // public void setPriority(Boolean value)
    // {
    //     this.priority = value;
    // }
    // public Boolean getInterruptable()
    // {
    //     return interruptable;
    // }
    // public void setInterruptable(Boolean value)
    // {
    //     this.interruptable = value;
    // }

    // public int getEstRunTime()
    // {
    //     return est_run_time;
    // }
    // public void setEstRunTime(int value)
    // {
    //     this.est_run_time = value;
    // }
    // public int getEstRemainTime()
    // {
    //     return est_remain_time;
    // }
    // public void setEstRemainTime(int value)
    // {
    //     this.est_remain_time = value;
    // }
}