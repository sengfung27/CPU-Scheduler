package pcb_package;

import pcb_package.PCB;

public class Scheduler
{
    public static void main(String[] args)
    {
        PCB pcb = new PCB("ls", 3003, "ready", 1, 0, 300, 200);
        System.out.println(pcb.getProgramName());
        System.out.println(pcb.getState());
        File file = new File("processes.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {

            // List to collect PCB objects
            List<PCB> processes = new ArrayList<PCB>();

            // Read file line by line
            String line = "";
            while ((line = br.readLine()) != null) {

                // String[] data = this.parseLine(line);
                PCB process = new PCB(line);

                processes.add(process);
            }

            

        }
        catch (IOException e)
        {
            processes.removeAll(processes);
            br.close();
        }
    }
}