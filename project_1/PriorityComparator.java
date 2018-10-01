import java.util.Comparator;

public class PriorityComparator implements Comparator<PCB>
{
    @Override
    public int compare(PCB x, PCB y)
    {
        if(x.priority == y.priority)
        {
            return x.PID < y.PID ? -1 : 1; 
        }
        else
        {
            return x.priority < y.priority ? -1 : 1;
        }
    }
}