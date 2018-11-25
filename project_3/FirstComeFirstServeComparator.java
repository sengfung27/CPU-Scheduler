import java.util.Comparator;

class FirstComeFirstServeComparator implements Comparator<PCB>
{
    @Override
    public int compare(PCB x, PCB y)
    {
        return x.arrival_time < y.arrival_time ? -1 : 1;  
    }
}