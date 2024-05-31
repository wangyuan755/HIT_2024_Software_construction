package IntervalSet;

import java.util.HashSet;
import java.util.Set;

public class APIs<L>{

    /**
     *  相似度计算
     *  思路：寻找同名的label，检查是否有重叠
     * @param s1 第一个要比较的时间戳
     * @param s2 第二个要比较的时间戳
     * @return 二者的相似度
     */
   public static<L> double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2)
    {
        Set<L> label1 = s1.labels();
        Set<L> label2 = s2.labels();
        Set<L> resLabel = new HashSet<>();
        //找出所有相同名的label
        for(L i:label1)
        {
            for(L j :label2)
            {
                if(i==j)
                {
                    resLabel.add(i);
                }
            }
        }
        long res=0;
        for(L i:resLabel)
        {
            res+=IntervalSimilarity(s1.intervals(i),s2.intervals(i));
        }
        long length = Math.max(s1.getEndTime(),s2.getEndTime())-Math.min(s1.getStartTime(),s2.getStartTime());
        return (double) res /length;

    }

    //计算相同label的相似度
    private static<L> long IntervalSimilarity(IntervalSet<L> s1, IntervalSet<L> s2)
    {
        long res = 0;
        for(int i =0;i<s1.size();i++)
        {
            for(int j =0;j<s2.size();j++)
            {
                res+=TimeSimilarity(s1.getByIndex(i),s2.getByIndex(j));
            }
        }
        return res;
    }

    private  static<L> long TimeSimilarity(Item<L> i, Item<L> j)
    {
        if(i.getEnd()>=j.getEnd()&&i.getStart()<=j.getEnd())
        {
            return j.getEnd()-i.getStart();
        }
        if(i.getEnd()>=j.getStart()&&i.getStart()<=j.getStart())
        {
            return i.getEnd()-j.getStart();
        }
        else {
            return 0;
        }
    }


    /**
     *计算一个时间戳上的冲突率
     * @param set 时间戳
     * @return 该时间戳的冲突率
     */
    public static<L> double calcConflictRatio(IntervalSet<L> set)
    {
        int length = (int)set.getEndTime();
        int[] arr = new int[length];
        for(int i =0;i<set.size()-1;i++)
        {
            Item<L> iItem = set.getByIndex(i);
            int j=1;
            while (TimeSimilarity(iItem,set.getByIndex(i+j))!=0)
            {
                long startTime = set.getByIndex(i+j).getStart();
                long endTime = set.getByIndex(i+j).getEnd();
                if(endTime>iItem.getEnd())
                {
                    endTime = iItem.getEnd();
                }
                for(int flag = (int) startTime;flag <endTime;flag++)
                {
                    arr[flag] = 1;
                }
                j++;
            }
        }
        int res = 0;
        for(int i =0;i<length;i++)
        {
            res+=arr[i];
        }
        return (double) res /length;
    }


    /**
     * 计算时间戳的空闲率
     * @param set 要计算的时间戳
     * @return 空闲率
     */
   public static<L>  double calcFreeTimeRatio(IntervalSet<L> set)
    {
        int length = (int)set.getEndTime();
        int[] arr = new int[length];
        for(int i =0;i<set.size();i++)
        {
            Item item = set.getByIndex(i);
            for(int j = (int) item.getStart(); j<item.getEnd();j++)
            {
                arr[j] = 1;
            }
        }
        int res = 0;
        for(int i =0;i<length;i++)
        {
            res+=arr[i];
        }
        return (double)  res /length;
    }

}
