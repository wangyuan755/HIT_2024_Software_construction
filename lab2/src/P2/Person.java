package P2;

import java.util.HashSet;
import java.lang.*;

public class Person {
    private String name;
    public Person(String name)
    {
        if(name == ""||name==null)
        {
            throw new RuntimeException("empty name");
        }
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}



