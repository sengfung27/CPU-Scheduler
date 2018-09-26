package pcb_package;

import pcb_package.PCB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
public class Scheduler
{
    public static void main(String[] args)
    {

        ArrayList<PCB> processes = new ArrayList<PCB>();

        try (BufferedReader br = new BufferedReader(new FileReader("processes.txt")))
        {


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
            System.out.println(e);
            processes.removeAll(processes);
            
        }
        finally
        {
            br.close();
        }
        for(PCB i : processes)
        {
            System.out.println(i.program_name);
        }
    }
}