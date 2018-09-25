package pcb_package;

import pcb_package.PCB;

public class Scheduler
{
    public static void main(String[] args)
    {
        PCB pcb = new PCB(3);
        //pcb.setProcess(2);
        System.out.println(pcb.getProcess());

    }
}