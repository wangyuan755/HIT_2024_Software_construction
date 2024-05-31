package IntervalSetTest;

import IntervalSet.*;
import org.junit.Test;

public class ConflictRatioTest<L> {
    CommonIntervalSet<String> timestamp = new CommonIntervalSet<String>();
    MultiIntervalSet<String> set1 = new MultiIntervalSet<String>();
    @Test
    public void Test()
    {
        set1.insert(0,5,"A");
        set1.insert(10,20,"B");
        //set1.insert(20,25,"A");
       // set1.insert(15,17,"D");
        //set1.insert(19,24,"G");
        set1.insert(25,30,"B");
        System.out.println(APIs.calcConflictRatio(set1));
        System.out.println(APIs.calcFreeTimeRatio(set1));

    }
}
