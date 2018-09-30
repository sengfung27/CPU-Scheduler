import java.util.Comparator;

public class PriorityComparator implements Comparator<PCB>
{
    @Override
    public int compare(PCB x, PCB y)
    {

        if (x.priority < y.priority)
        {
            return -1;
        }
        if (x.priority > y.priority)
        {
            return 1;
        }
        return 0;
    }
}