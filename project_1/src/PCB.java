package pcb_package;

public class PCB 
{
    private String program_name;
    private int PID;
    private String state;
    private Boolean priority;
    private Boolean interruptable;
    private int est_run_time;
    private int est_remain_time;
    
    public PCB(String program_name,
     int PID,
     String state,
     Boolean priority,
     Boolean interruptable,
     int est_run_time,
     int est_remain_time)
    {
        this.setProgramName(program_name);
        this.setPID(PID);
        this.setState(state);
        this.setPriority(priority);
        this.setInterruptable(interruptable);
        this.setEstRunTime(est_run_time);
        this.setEstRemainTime(est_remain_time);
    }



    public String getProgramName()
    {
        return program_name;
    }
    public void setProgramName(String value)
    {
        this.program_name = value;
    }
    public int getPID()
    {
        return PID;
    }
    public void setPID(int value)
    {
        this.PID = value;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String value)
    {
        this.state = value;
    }
    public Boolean getPriority()
    {
        return priority;
    }
    public void setPriority(Boolean value)
    {
        this.priority = value;
    }
    public Boolean getInterruptable()
    {
        return interruptable;
    }
    public void setInterruptable(Boolean value)
    {
        this.interruptable = value;
    }

    public int getEstRunTime()
    {
        return est_run_time;
    }
    public void setEstRunTime(int value)
    {
        this.est_run_time = value;
    }
    public int getEstRemainTime()
    {
        return est_remain_time;
    }
    public void setEstRemainTime(int value)
    {
        this.est_remain_time = value;
    }
}