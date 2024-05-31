package IntervalSet;

import java.util.Set;

public class Item<L> {
    /**
     * Abstraction function:
     * 将时间戳上的时间段抽象为这个类
     * label为标号名，可为String等
     * start，end为这个时间段的起止时间
     * Representation invariant:
     * 时间均为非负，终止时间大于起始时间
     * Safety from rep exposure:
     * 属性均为private
     */
    private L label;
    private long start;
    private long end;
    public Item()
    {
    }
    public Item(L label,long start,long end)
    {
        this.label = label;
        this.start = start;
        this.end = end;
    }
    public void  setLabel(L label)
    {
        this.label = label;
    }
    public long getLength()
    {
        return this.end-this.start;
    }
    public void setStart(long start)
    {
        this.start = start;
    }
    public void setEnd(long end)
    {
        this.end = end;
    }
    public long getStart()
    {
        return this.start;
    }
    public long getEnd()
    {
        return this.end;
    }
    @Override
    public String toString()
    {
        return "{"+label.toString()+"=["+start+","+end+']'+"}";
    }


    public L getLabel()
    {
        return this.label;
    }
}
