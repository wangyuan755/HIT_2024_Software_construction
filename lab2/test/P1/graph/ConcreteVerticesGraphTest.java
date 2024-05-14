/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest<L> extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    private ConcreteVerticesGraph<String> buildBasicStringGraph(){
        ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<String>();
        graph.set("alpha","beta",1);
        graph.set("beta","Gamma",2);
        graph.set("A","B",1);
        graph.add("E");
        return graph;
    }
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    @Test
    public void testStruct(){
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        assertFalse(graph.vertices().contains("F"));
        assertTrue(graph.add("F"));
        assertFalse(graph.add("A"));
        assertTrue(graph.vertices().contains("E"));

    }
    @Test
    public void testSet()
    {
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        assertThrows(RuntimeException.class,()->graph.set("C","D",-1));
        assertThrows(RuntimeException.class,()->graph.set("C","C",5));
        assertEquals(0,graph.set("H","Z",7));
        assertEquals(7,graph.set("H","Z",5));
        assertEquals(5,graph.set("H","Z",4));
        assertEquals(1,graph.set("A","B",0));
        assertEquals(0,graph.set("A","B",4));
        assertEquals(0,graph.set("X","Gamma",4));
        assertEquals(4,graph.set("X","Gamma",0));
    }
    @Test
    public void testRemove()
    {
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        assertTrue(graph.vertices().contains("alpha"));
        assertTrue(graph.remove("alpha"));
        assertFalse(graph.vertices().contains("alpha"));
        assertFalse(graph.remove("GG"));
    }
    @Test
    public void testTarget(){
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        Map<String,Integer> map = new HashMap<>();
        map.put("Gamma",2);
        assertThrows(RuntimeException.class,()->graph.targets("GG"));
        assertEquals(map , graph.targets("beta"));
        graph.set("beta","GG",1);
        assertNotEquals(map , graph.targets("beta"));
        map.put("GG",1);
        assertEquals(map , graph.targets("beta"));
        graph.set("beta","Gamma",0);
        map.remove("Gamma");
        assertEquals(map , graph.targets("beta"));
        graph.set("beta","GG",0);
        map.remove("GG");
        assertEquals(map, graph.targets("beta"));
    }
    @Test
    public void testSource(){
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        Map<String,Integer> map = new HashMap<>();
        map.put("alpha",1);
        assertEquals(map,graph.sources("beta"));
    }
    @Test
    public void testVertices(){
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        graph.vertices().add("TEST");
        assertFalse(graph.vertices().contains("TEST"));
        graph.add("TEST");
        assertTrue(graph.vertices().contains("TEST"));
        graph.vertices().remove("alpha");
        assertTrue(graph.vertices().contains("alpha"));
    }
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToString(){
        ConcreteVerticesGraph<String> graph = buildBasicStringGraph();
        assertEquals("alpha{}{beta=1}beta{alpha=1}{Gamma=2}Gamma{beta=2}{}A{}{B=1}B{A=1}{}E{}{}",graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    @Test
    public void testEdgetoString()
    {
        Vertex<String> test0 = new Vertex<>("a");
        assertEquals("a{}{}",test0.toString());

    }
}
