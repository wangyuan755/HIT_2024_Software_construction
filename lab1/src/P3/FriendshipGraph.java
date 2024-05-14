import java.util.*;

public class FriendshipGraph {
    //某人可联系上的人
    private HashMap<Person , ArrayList<Person>> contactList;
    public FriendshipGraph() {
        contactList = new HashMap<Person, ArrayList<Person>>();
    }
    //添加人物
    public void addVertex(Person p) {
        if(!contactList.containsKey(p)) {
            contactList.put(p, new ArrayList<Person>());
        }
        else {
            System.out.println("已经存在 " + p + "了!");
        }
    }


    public boolean adjust(Person p1)//判断顶点是否在表内
    {
        if(!contactList.containsKey(p1))
        {
            return false;
        }
        return true;
    }
    public void addEdge(Person p1,Person p2)
    {
        if(adjust(p1)&&adjust(p2))
        {
            contactList.get(p1).add(p2);
        }
        else {
            System.out.println("找不到"+p1);
            return;
        }
    }
    public void addUnDirectedEdge(Person p1, Person p2)
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
                    ArrayList<Person> inext = contactList.get(now.get(i));
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
