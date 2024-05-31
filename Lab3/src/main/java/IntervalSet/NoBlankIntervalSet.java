package IntervalSet;

public class NoBlankIntervalSet<L> extends SetDecorator<L> implements IntervalSet<L>{
    public NoBlankIntervalSet(IntervalSet<L> set) {
        super(set);
    }
    public void insert(long start, long end, L label)
    {
        super.insert(start, end, label);
    }
    public void checkBlank()
    {
        String c = "Blank!";
        if(super.getByIndex(0).getStart()!=0)
        {
            throw new RuntimeException(c);

        }
        for(int i = 0;i<super.size();i++)
        {
            if(super.getByIndex(i).getEnd()<super.getByIndex(i+1).getStart())
            {
                throw new RuntimeException(c);
            }
        }
        super.checkBlank();
    }
    @Override
    public void setPeriodic(long periodic,long cycleNum){
        super.setPeriodic(periodic,cycleNum);
    }
    @Override
    public String toString()
    {
        return super.toString();
    }
}
