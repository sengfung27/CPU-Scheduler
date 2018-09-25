package pcb_package;

public class PCB 
{
    private int process;
    public PCB(int process)
    {
        this.setProcess(process);
    }
    public int getProcess()
    {
        return process;
    }
    public void setProcess(int process)
    {
        this.process = process;
    }
}