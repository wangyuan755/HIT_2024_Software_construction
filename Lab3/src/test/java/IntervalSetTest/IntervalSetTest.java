package IntervalSetTest;

import static org.junit.Assert.*;

import IntervalSet.CommonIntervalSet;
import IntervalSet.MultiIntervalSet;
import IntervalSet.NonOverlapIntervalSet;
import IntervalSet.PeriodicIntervalSet;
import org.junit.Test;
import java.util.HashSet;

public class IntervalSetTest {
    CommonIntervalSet<String> timestamp = new CommonIntervalSet<String>();

    @Test
    public void Test()
    {
        /**
         *基础插入功能，验证可重复label和不可重复label是否成功设置
         * 验证各个装饰器子类的独特功能是否正确
         */
        HashSet<String> set = (HashSet<String>) timestamp.labels();
        assertNull(set);
        timestamp.insert(1,3,"r");
        timestamp.insert(3,4,"r");
        timestamp.insert(4,3,"d");
        System.out.println(timestamp.toString());
        timestamp.setFlag(false);
        timestamp.insert(3,4,"r");
        timestamp.insert(7,10,"r");
        System.out.println(timestamp.toString());
        MultiIntervalSet<String> set1 = new MultiIntervalSet<String>(timestamp);
        System.out.println("Multi:" +set1.toString());
        PeriodicIntervalSet<String> pset = new PeriodicIntervalSet<String>(timestamp);
        NonOverlapIntervalSet<String> nset = new NonOverlapIntervalSet<String>(pset);
        //NonOverlapIntervalSet<String> nset = new NonOverlapIntervalSet<String>((new PeriodicIntervalSet<String>(timestamp)));
        nset.checkBlank();
        System.out.println("before:"+nset.toString());
        nset.setPeriodic(10,2);
        System.out.println("after:" + nset.toString());
    }
}
