/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    @Test
    public void testGrapgPoet() throws IOException {
        final GraphPoet virgil = new GraphPoet(new File("src/P1/poet/virgil.txt"));
        assertEquals("vertices:[the, that, storm, approaching, i, is, am]Edges:iam1 amthe1 thestorm1 stormthat1 thatis1 isapproaching1 ",virgil.toString());
    }
    @Test
    public void testPoem() throws IOException {
        final GraphPoet virgil = new GraphPoet(new File("src/P1/poet/virgil.txt"));
        final String input2 = "storm is approaching";
        assertEquals("storm that is approaching",virgil.poem(input2));

    }

    // TODO tests
    
}
