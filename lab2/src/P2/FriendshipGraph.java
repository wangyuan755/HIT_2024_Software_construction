package P2;

import java.util.*;

public class FriendshipGraph {
    private Graph<Person> person;
    //某人可联系上的人
    //private HashMap<Person, ArrayList<Person>> contactList;
    public FriendshipGraph() {
        person = new ConcreteEdgesGraph<Person>();
    }



    public boolean adjust(Person p1)//判断顶点是否在表内
    {
        for(Person p :person.vertices())
        {
            if(p.equals(p1))
            {
                return true;
            }
        }
        return false;
    }
    public void addVertex(Person p) {
        if (!person.add((p))) {
            System.out.println("添加" + p.getName() + "失败");
        }
    }

    public void addEdge(Person p1, Person p2)
    {
        if(adjust(p1)&&adjust(p2))
        {
            person.set(p1,p2,1);
        }
        else {
            throw new RuntimeException("cant find the Person");
        }
    }
    public boolean remove(String p)
    {
        for(Person i :person.vertices())
        {
            if(i.getName().equals(p))
            {
                return person.remove(i);
            }
        }
        return false;
    }

    public void addUnDirectedEdge(Person p1,Person p2)
    {
        addEdge(p1,p2);
        addEdge(p2,p1);
    }

    public int getDistance(Person p1, Person p2) {
        if(adjust(p1)&&adjust(p2))
        {
            if(p1 == p2)
            {
                return 0;
            }
            LinkedList<ArrayList<Person>> List = new LinkedList<ArrayList<Person>>();
            HashMap<Person, Boolean> Isvisit = new HashMap<Person, Boolean>();//判断是否访问过
            ArrayList<Person> start = new ArrayList<Person>();
            start.add(p1);
            List.add(start);
            int step = 0;
            while (!List.isEmpty())
            {
                ArrayList<Person> now = List.poll();
                if(now.contains(p2))
                {
                    return step;
                }
                ArrayList<Person> next = new ArrayList<Person>();
                for(int i = 0;i<now.size();i++)
                {
                    Isvisit.put(now.get(i),true);
                    ArrayList<Person> inext = new ArrayList<Person>(person.targets(now.get(i)).keySet());
                    for (int j=0;j<inext.size();j++)
                    {
                        if(!Isvisit.containsKey(inext.get(j)))
                        {
                            next.add(inext.get(j));
                        }
                    }
                }
                step ++;
                if(!next.isEmpty()) {
                    List.add(next);
                }
            }
            return -1;
       }

       else {
            System.out.println("找不到"+p1);
            return -1;
        }
    }

   /*public int getDistance(Person p1, Person p2) {
       if(adjust(p1)&&adjust(p2)) {
           if (p1 == p2) {
               return 0;
           }



       }
       else {
           System.out.println("找不到"+p1);
           return -1;
       }
   }*/
    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));//should return 1
        System.out.println(graph.getDistance(rachel, ben));//should return 2
        System.out.println(graph.getDistance(rachel, rachel));//should return 0
        System.out.println(graph.getDistance(rachel, kramer));//should return -1

    }

}
