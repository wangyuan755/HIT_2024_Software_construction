package IntervalSet;

import java.util.Set;

public abstract class SetDecorator<L> implements IntervalSet<L>{
    /**
     * 装饰器方法
     */
    private IntervalSet<L> set;

    public SetDecorator(IntervalSet<L> set) {
        this.set = set;
    }

    public SetDecorator()
    {
        this.set = new CommonIntervalSet<L>();
    }

    @Override
    public void empty() {
        set.empty();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public Item<L> getByIndex(int i) {
        return set.getByIndex(i);
    }

    @Override
    public void insert(long start, long end, L label) {
        set.insert(start,end,label);
    }

    @Override
    public Set<L> labels() {
        return set.labels();
    }

    @Override
    public boolean remove(L label) {
        return set.remove(label);
    }

    @Override
    public long start(L label) {
        return set.start(label);
    }

    @Override
    public long end(L label) {
        return set.end(label);
    }

    @Override
    public void setFlag(boolean i) {
        set.setFlag(i);
    }

    public  void checkBlank()
    {
        set.checkBlank();
    }

   public void setPeriodic(long periodic,long cycleNum)
    {
        set.setPeriodic(periodic,cycleNum);
    }
    public String toString()
    {
       return set.toString();
    }

    @Override
    public void setEndTime(long time)
    {
        set.setEndTime(time);
    }
    @Override
    public long getEndTime() {
        return set.getEndTime();
    }

    @Override
    public long getStartTime() {
        return 0;
    }
}
