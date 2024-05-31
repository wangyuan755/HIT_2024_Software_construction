package IntervalSet;

public class NonOverlapIntervalSet<L> extends SetDecorator<L> implements IntervalSet<L>{
    public NonOverlapIntervalSet(IntervalSet<L> set) {
        super(set);
    }
    @Override
    public void insert(long start, long end, L label)
    {
        for(int i=0;i<super.size();i++)
        {
            if(overLap(start,super.getByIndex(i).getStart(),super.getByIndex(i).getEnd())
                    ||overLap(end,super.getByIndex(i).getStart(),super.getByIndex(i).getEnd()))
            {
                throw new RuntimeException("Overlap!");
            }
        }
        super.insert(start,end,label);
    }

    private boolean overLap(long time,long start,long end)
    {
        if(time<end&&time>start)
        {
            return true;
        }
        return false;
    }
    @Override
    public void checkBlank() {
        //System.out.println("Multi");
        super.checkBlank();
    }
    @Override
    public void setPeriodic(long periodic,long cycleNum){
        super.setPeriodic(periodic,cycleNum);
    }
}
