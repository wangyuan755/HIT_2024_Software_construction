import org.junit.Test;

import static org.junit.Assert.*;

public class FriendshipGraphTest {

    @Test
    public void getDistanceTest()
    {
        FriendshipGraph graph = new FriendshipGraph();
        Person v1 = new Person("v1");
        Person v2 = new Person("v2");
        Person v3 = new Person("v3");
        Person v4 = new Person("v4");
        Person v5 = new Person("v5");
        Person v6 = new Person("v6");
        Person v7 = new Person("v7");
        Person v8 = new Person("v8");
        Person v9 = new Person("v9");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);
        graph.addVertex(v9);
        graph.addUnDirectedEdge(v1,v2);
        graph.addEdge(v1,v3);
        graph.addUnDirectedEdge(v2,v4);
        graph.addUnDirectedEdge(v5,v2);
        graph.addUnDirectedEdge(v4,v8);
        graph.addUnDirectedEdge(v5,v8);
        graph.addUnDirectedEdge(v3,v6);
        graph.addUnDirectedEdge(v3,v7);
        graph.addUnDirectedEdge(v6,v7);
        assertEquals(2,graph.getDistance(v1,v4));
        assertEquals(-1,graph.getDistance(v7,v8));
        assertEquals(5,graph.getDistance(v8,v7));
        assertEquals(-1,graph.getDistance(v9,v4));
    }
}
