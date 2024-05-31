package IntervalSetTest;

import static org.junit.Assert.*;

import IntervalSet.CommonIntervalSet;
import IntervalSet.NonOverlapIntervalSet;
import IntervalSet.PeriodicIntervalSet;
import org.junit.Test;
import java.util.HashSet;
public class MultiTest {
    IntervalSet.MultiIntervalSet<String> set = new IntervalSet.MultiIntervalSet<String>();
    @Test
    public void Test()
    {
        set.insert(1,3,"r");
        set.insert(5,7,"r");
        set.insert(9,10,"r");
        System.out.println(set.toString());
        set.remove("r");
        System.out.println(set.toString());
    }
}
