package pcb_package;

import pcb_package.PCB;

public class Scheduler
{
    public static void main(String[] args)
    {
        PCB pcb = new PCB("ls", 3003, "ready", true, false, 300, 200);
        System.out.println(pcb.getProgramName());
        System.out.println(pcb.getState());
    }
}