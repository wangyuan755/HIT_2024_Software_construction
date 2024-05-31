package IntervalSet;

import java.util.*;
public class CommonIntervalSet<L> implements IntervalSet<L> {
    /**
     * Abstraction function:
     * 将时间戳抽象为这个类
     * 用一条链表维护整个时间戳
     * 链表上的元素为Item
     * 维护一个整个时间段的终止时间
     * flag为标志位，用于设置时间戳可否出现同名标签，true为不可重复
     * Representation invariant:
     * 标签默认为true，即不可重复（可通过设置flag标志位更改）
     * Safety from rep exposure:
     * 属性均为private
     */
    private LinkedList<Item<L>> timestamp;

    private long EndTime;
    //是否开启标签唯一
    //用这个标志位来实现Multi insert对Common的复用
    private boolean flag =true;
    //private long startTime;
    //private long endTime;
    public CommonIntervalSet()
    {
        this.empty();
    }


    //flag==true则标签唯一,时间非负，终止时间大于起始时间
    private void checkRep()
    {
        for(Item<L> i:timestamp)
        {
            if(i.getEnd()*i.getStart()<0)
            {
                assert false : "time should be not negative";
            }
            if(i.getStart()>i.getEnd())
            {
                assert false: "start time should be smaller than end";
            }
        }
        //关闭可重复的情况下进行检测
        if(flag) {
            Set<L> label = this.labels();
            for (L i : label) {
                int res = 0;
                for (L l : label) {
                    if (l == i) {
                        res++;
                    }
                }
                if (res > 1) {
                    assert false : "same label";
                }
            }
        }
    }
    @Override
    public void empty() {
        this.timestamp = new LinkedList<>();
    }
    @Override
    public int size()
    {
        return timestamp.size();
    }
    @Override
    public Item<L> getByIndex(int i)
    {
        return timestamp.get(i);
    }


    //插入时排好序，方便后续工作
    @Override
    public void insert(long start, long end, L label) {
        Set<L> Labelset = this.labels();
        if(flag &&Labelset!=null&&Labelset.contains(label))
        {
            System.out.println("already have label");
            return;
        }
        if(start*end<0)
        {
            System.out.println("time should be not negative");
            return;
        }
        if(start>end)
        {
            System.out.println("start time should be smaller than end");
            System.out.println("automatically change the order");
            long tmp = start;
            start = end;
            end = tmp;
        }
        //负责维护结束时间
        if(end>this.EndTime)
        {
            this.EndTime = end;
        }
        Item<L> item = new Item<>(label,start,end);
        int index = -1;
        if(timestamp.isEmpty())
        {
            timestamp.add(item);
            checkRep();
            return;
        }
        Set<L> set = this.labels();
        //head
        if( timestamp.getFirst().getStart()>start)
        {
            timestamp.addFirst(item);
            checkRep();
            return;
        }
        //tail
        if(timestamp.getLast().getStart()<=start)
        {
            timestamp.addLast(item);
            checkRep();
            return;
        }
        //middle
        //根据开始时间在时间轴上的顺序排序
        //若起始时间相同，则根据结束时间
        for(int i=0;i<timestamp.size()-1;i++)
        {
            if(timestamp.get(i).getStart()<start&&timestamp.get(i+1).getStart()>start)
            {
                timestamp.add(i+1,item);
                checkRep();
                return;
            }
            if(timestamp.get(i).getStart()==start)
            {
                if(end<timestamp.get(i).getEnd())
                {
                    timestamp.add(i,item);
                    checkRep();
                    return;
                }
                else
                {
                    timestamp.add(i+1,item);
                    checkRep();
                    return;
                }
            }
        }
            System.out.println("unexpected error");
    }

    @Override
    public Set<L> labels() {
        if(timestamp.isEmpty())
        {
            return null;
        }
        HashSet<L> set = new HashSet<>();
        for(Item<L> i :timestamp)
        {
            set.add(i.getLabel());
        }
        return set;
    }

    //不会有两个一样的标签
    @Override
    public boolean remove(L label) {
        Iterator<Item<L>> iterator = timestamp.iterator();
        boolean flag = false;
        while(iterator.hasNext())
        {
            if(iterator.next().getLabel()==label)
            {
                iterator.remove();
                flag = true;
            }
        }
        checkRep();
        return flag;
    }

    @Override
    public long start(L label) {
        for(Item<L> i:timestamp)
        {
            if(i.getLabel()==label)
            {
                return i.getStart();
            }
        }
        return -1;
    }

    @Override
    public long end(L label) {
        for(Item<L> i:timestamp)
        {
            if(i.getLabel()==label)
            {
                return i.getEnd();
            }
        }
        return -1;
    }

    @Override
    public long getEndTime() {
        if(this.size()==0)
        {
            return 0;
        }
        return this.EndTime;
    }

    @Override
    public long getStartTime() {
        if(this.size()==0)
        {
            return 0;
        }
        return this.getByIndex(0).getStart();
    }
    @Override
    public void setEndTime(long time)
    {
        if(time<this.EndTime)
        {
            System.out.println("Couldn't set EndTime");
            return;
        }
        this.EndTime = time;
    }

    @Override
    public void setFlag(boolean i) {
        this.flag = i;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Item<L> i : timestamp)
        {
            sb.append(i.toString());
        }
        return sb.toString();
    }

    @Override
    public void checkBlank() {
        //System.out.println("COM");
    }

    @Override
    public void setPeriodic(long periodic, long cycleNum) {

    }
}


