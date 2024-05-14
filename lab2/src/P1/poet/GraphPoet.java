/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   文档中单词构成的图
    // Representation invariant:
    //   单词不为空
    // Safety from rep exposure:
    //   私有图
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        BufferedReader toread = new BufferedReader(new FileReader(corpus));
        String s1;
        String[] s;
        List<String>mylist = new ArrayList<>();
        Map<String,Integer>mymap = new HashMap<>();
        while((s1 = toread.readLine())!=null)
        {
            s = s1.split(" ");
            mylist.addAll(Arrays.asList(s));
        }
        toread.close();
        for(int i=0;i<mylist.size()-1;i++)
        {
            String source =mylist.get(i).toLowerCase();
            String target = mylist.get(i+1).toLowerCase();
            int before = 0;
            String full = source + target;
            if(mymap.containsKey(full))
            {
                before = mymap.get(full);
            }
            mymap.put(full,before+1);
            graph.set(source,target,before+1);
        }
        checkrep();
    }
    
    // TODO checkRep
    public void checkrep(){
        Set<String> vertices = graph.vertices();
        for (String vertex : vertices) {
            assert (vertex != null);
        }
    }
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        StringBuilder build = new StringBuilder();
        List<String> mylist = new ArrayList<>();
        String[] newinput = input.split(" ");
        mylist.addAll(Arrays.asList(newinput));
        Map<String,Integer>sourcemap = new HashMap<>();
        Map<String,Integer>targetmap = new HashMap<>();
        for(int i =0;i< mylist.size()-1;i++)
        {
            build.append(mylist.get(i)).append(" ");
            String source = mylist.get(i).toLowerCase();
            String target = mylist.get(i+1).toLowerCase();
            targetmap = graph.targets(source);
            sourcemap = graph.sources(target);
            if(targetmap==null||sourcemap==null)
            {
                continue;
            }
            int max = 0;
            String word =" ";
            for(String a: targetmap.keySet())
            {
                if(sourcemap.containsKey(a)&&sourcemap.get(a)+targetmap.get(a)>max)
                {
                    max = sourcemap.get(a) + targetmap.get(a);
                    word=a;
                }
            }
            if(max>0)
            {
                build.append(word+" ");
            }
        }
        build.append(mylist.get(mylist.size()-1));
        checkrep();
        return build.toString();
    }
    
    @Override
    public String toString()
    {
        return graph.toString();
    }
}
