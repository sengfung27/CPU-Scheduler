package pcb_package;

import pcb_package.PCB;

public class CpuEmulator
{
    public static void main(String[] args)
    {
        PCB pcb = new PCB(10);
        //pcb.setProcess(2);
        System.out.println(pcb.getProcess());

    }
}