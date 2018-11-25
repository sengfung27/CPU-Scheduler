import java.util.Comparator;

public class PriorityComparator implements Comparator<PCB>
{
    @Override
    public int compare(PCB x, PCB y)
    {
        if(x.priority == y.priority)
        {
            return x.arrival_time < y.arrival_time ? -1 : 1; 
        }

        return x.priority < y.priority ? -1 : 1;
        
    }
}