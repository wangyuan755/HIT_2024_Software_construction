package IntervalSet;

public class PeriodicIntervalSet<L> extends SetDecorator<L> implements IntervalSet<L> {
    public PeriodicIntervalSet(IntervalSet<L> set) {
        super(set);
    }
    private long Periodic;
    private long cycleNum;

    public void setPeriodic(long periodic,long cycleNum)
    {
        assert (periodic>0&&cycleNum>0):"Unavailable num";
        this.Periodic = periodic;
        for(int i = 0;i<super.size();i++)
        {
            if(periodic<super.getByIndex(i).getEnd()&&periodic>super.getByIndex(i).getStart())
            {
                System.out.println("Shouldn't separate timestamp to set periodic,please check your periodic");
                throw new RuntimeException("Unavailable periodic");
            }
        }
        if(periodic<(super.getByIndex(super.size()-1).getEnd()))
        {
            System.out.println("Warning : periodic set will overwrite some timestamp");
            System.out.println("Make sure your periodic bigger than existing IntervalSet.IntervalSet");
        }
        int flag = 0;
        for(int i=0;i<super.size()&&super.getByIndex(i).getEnd()<=periodic;i++)
        {
            flag = i;
        }
        for(int j =1;j<cycleNum;j++)
        {
            for(int i = 0;i<=flag;i++)
            {
                super.insert(super.getByIndex(i).getStart()+(j*periodic),super.getByIndex(i).getEnd()+(j*periodic),super.getByIndex(i).getLabel());
            }
        }
    }
    @Override
    public void checkBlank() {
        //System.out.println("Multi");
        super.checkBlank();
    }
    public long getPeriodic()
    {
        return this.Periodic;
    }
    public long getCycleNum()
    {
        return this.cycleNum;
    }
}
