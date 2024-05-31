package IntervalSet;

import java.util.*;
public class MultiIntervalSet<L> extends SetDecorator<L> implements IntervalSet<L>{
    /**
     * Abstraction function:
     * 在维护父类的时间戳的同时，维护自身的一个hash表，否则一些访问父类时间戳的方法不会正常运行
     * hash表的键为标签名称，值为以这个标签为名的所有Item构成的一个时间戳
     * Representation invariant:
     * 同父类
     * Safety from rep exposure:
     * 属性均为private
     */
    private HashMap<L,IntervalSet<L>> timestamp;
    public MultiIntervalSet(IntervalSet<L> initial)
    {
        super(initial);
        this.empty();
        System.out.println("Attention: initialize two instance with one IntervalSet item may cause unpredictable problem");
        if(initial.size() ==0)
        {
            return;
        }
        for(int i = 0;i<initial.size();i++)
        {

            Item<L> item = initial.getByIndex(i);
            long start = item.getStart();
            long end = item.getEnd();
            L label = item.getLabel();
            if(timestamp.containsKey(label))
            {
                IntervalSet<L> tmplist = timestamp.get(label);
                tmplist.setFlag(false);
                tmplist.insert(start,end,label);
                timestamp.replace(label,tmplist);
            }
            else {
                IntervalSet<L> tmplist2 = new CommonIntervalSet<L>();
                tmplist2.insert(start,end,label);
                timestamp.put(label,tmplist2);
            }
        }
    }

    public MultiIntervalSet()
    {
        super();
        this.empty();
    }



    public void empty() {
        this.timestamp = new HashMap<>();
    }


    //依然隐式地维护父类的IntervalSet。
    public void insert(long start, long end, L label) {
        super.setFlag(false);
        super.insert(start,end,label);
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
        if(timestamp.containsKey(label))
        {
            IntervalSet<L> tmplist = timestamp.get(label);
            tmplist.setFlag(false);
            tmplist.insert(start,end,label);
            timestamp.replace(label,tmplist);
        }
        else {
            IntervalSet<L> tmplist2 = new CommonIntervalSet<L>();
            tmplist2.insert(start,end,label);
            timestamp.put(label,tmplist2);
        }
    }


    public Set<L> labels() {
        if(timestamp.isEmpty())
        {
            return null;
        }
        return timestamp.keySet();
    }


    public boolean remove(L label) {
        super.remove(label);
        if(timestamp.containsKey(label))
        {
            timestamp.remove(label);
            return true;
        }
        return false;
    }

    @Override
    public void checkBlank() {
        //System.out.println("Multi");
        super.checkBlank();
    }


    public IntervalSet<L> intervals(L label)
    {
        return timestamp.getOrDefault(label, null);
    }
    @Override
    // { A=[[0,10],[20,30]],B=[[10,20]]}
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(L key:timestamp.keySet())
        {
            sb.append(key).append("=[");
            //sb.append(timestamp.get(key).toString());\
            IntervalSet<L> list = timestamp.get(key);
            for(int i = 0;i<list.size();i++)
            {
                sb.append("[").append(list.getByIndex(i).getStart()).append(",").append(list.getByIndex(i).getEnd()).append("]");
            }
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }
    @Override
    public void setPeriodic(long periodic,long cycleNum){
        super.setPeriodic(periodic,cycleNum);
    }

}
