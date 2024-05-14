import java.util.HashSet;
import java.lang.*;

public class Person {
    private String name;
    static private HashSet<String> nameTable;

    public Person(String name)
    {
        if(nameTable == null)
        {
            nameTable = new HashSet<String>();
        }
        if(nameTable.contains(name))
        {
            System.out.println("已经有人叫 " + name + "了!");
            System.exit(1);
        }
        this.name = name;
        nameTable.add(name);
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



