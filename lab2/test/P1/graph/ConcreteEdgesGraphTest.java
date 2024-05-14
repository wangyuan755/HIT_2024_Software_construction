/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest<L> extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()\

    private ConcreteEdgesGraph<String> buildBasicStringGraph()//建立一个基础的示例图
    {
        ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<String>();
        graph.set("alpha","beta",1);
        graph.set("beta","Gamma",2);
        graph.set("A","B",1);
        graph.add("E");
        return graph;
    }

    //测试基本结构
    @Test
    public void testStructure(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        assertTrue(graph.vertices().contains("alpha"));
        assertTrue(graph.vertices().contains("B"));
        assertFalse(graph.vertices().contains("C"));
        graph.add("C");
        assertTrue(graph.vertices().contains("C"));
        assertFalse(graph.add("C"));

    }
    @Test
    public void testToString(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        String exp = "vertices:" + graph.vertices().toString() + "Edges:alphabeta1 betaGamma2 AB1 ";
        assertEquals(exp,graph.toString());
    }

    //测试基本功能和错误提示(无顶点)
    @Test
    public void testTarget(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        Map<String,Integer> map = new HashMap<>();
        map.put("Gamma",2);
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
    //原理同上，不做过多测试
    @Test
    public void testSource(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        Map<String,Integer> map = new HashMap<>();
        map.put("alpha",1);
        assertEquals(map,graph.sources("beta"));
    }

    //测试覆盖weight为负，起终点重合的异常情况
    //并测试set修改并返回原有weight和删除边
    @Test
    public void testSet(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        assertEquals(0,graph.set("H","Z",7));
        assertEquals(7,graph.set("H","Z",5));
        assertEquals(5,graph.set("H","Z",4));
        assertEquals(1,graph.set("A","B",0));
        assertEquals(0,graph.set("A","B",4));
    }
    //测试移除已有的是否返回true并验证是否成功移除，验证移除未有的是否返回false
    @Test
    public void testRemove(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        assertTrue(graph.vertices().contains("alpha"));
        assertTrue(graph.remove("alpha"));
        assertFalse(graph.vertices().contains("alpha"));
        assertFalse(graph.remove("GG"));
    }
//测试基本功能防御性拷贝
    @Test
    public void testVertices(){
        ConcreteEdgesGraph<String> graph = buildBasicStringGraph();
        graph.vertices().add("TEST");
        assertFalse(graph.vertices().contains("TEST"));
        graph.add("TEST");
        assertTrue(graph.vertices().contains("TEST"));
        graph.vertices().remove("alpha");
        assertTrue(graph.vertices().contains("alpha"));
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    // TODO tests for operations of Edge
    @Test
    public void testEdgetoString()
    {
        Edge<String> test0 = new Edge<String>("a","b",1);
        assertEquals("ab1 ",test0.toString());
        Edge<String> test1 = new Edge<String>("a","b",0);
        assertEquals("ab0 ",test1.toString());
        Edge<Integer> test2 = new Edge<Integer>(1,2,0);
        assertEquals("120 ",test2.toString());
    }
    
}
