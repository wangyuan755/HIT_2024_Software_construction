package IntervalSetTest;

import static org.junit.Assert.*;

import IntervalSet.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SimilarityTest {
    CommonIntervalSet<String> timestamp = new CommonIntervalSet<String>();
    MultiIntervalSet<String> set1 = new MultiIntervalSet<String>();
    MultiIntervalSet<String> set2 = new MultiIntervalSet<String>();

    //相似度计算
    @Test
    public void Test()
    {
        set1.insert(0,5,"A");
        set1.insert(10,20,"B");
        set1.insert(20,25,"A");
        set1.insert(25,30,"B");
        set2.insert(0,5,"C");
        set2.insert(10,20,"B");
        set2.insert(20,35,"A");
        System.out.println(set1.toString());
        System.out.println(set2.toString());
        System.out.println(APIs.Similarity(set1,set2));
        List<String> a = Arrays.asList(new String("c"));
        List<String> b = Arrays.asList("c");
        assertEquals(a, b);
    }

}
