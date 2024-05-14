/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 * 用点集的形式来构建有向图
 * vertices为图的所有顶点信息构成的集合
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   将图抽象为点的集合，边的信息也存在点集中
    // Representation invariant:
    //   无重复点，图中所有点的source集和target集相等
    // Safety from rep exposure:
    //   所有内部类型都是private
    public ConcreteVerticesGraph()
    {

    }
    private void checkrep()
    {
        Iterator<Vertex<L>> iterator = vertices.iterator();
        HashSet<L> sources = new HashSet<L>();
        HashSet<L> targets = new HashSet<L>();
        while(iterator.hasNext())
        {
            Vertex<L> next = iterator.next();
            next.checkrep();
            if(next.getSources()!=null)
            {
                targets.add(next.getName());
            }
            if(next.getTargets()!=null)
            {
                sources.add(next.getName());
            }
            sources.addAll(next.getSources().keySet());
            targets.addAll(next.getTargets().keySet());
        }
        if(!sources.equals(targets))
        {
            assert  false;
        }
        assert  true;
    }



    @Override public boolean add(L vertex) {
        Vertex<L> n = new Vertex<>(vertex);
        for(Vertex<L> i:vertices)
        {
            if(i.getName()==vertex)
            {
                return false;
            }
        }
        vertices.add(n);
        checkrep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        if(weight<0)
        {
            throw new RuntimeException("error input");
        }
        if(source.equals(target))
        {
            throw new RuntimeException("same vertex for source and target");
        }
        Vertex<L> sour = new Vertex<>(source);
        Vertex<L> targ = new Vertex<>(target);
        sour.addTargets(target,weight);
        targ.addSources(source,weight);
        if(weight>0) {
            Iterator<Vertex<L>> iterator = vertices.iterator();
            while (iterator.hasNext()) {
                Vertex<L> ver = iterator.next();
                //至少存在一个端点相同
                if(ver.equals(sour))
                {
                    if(ver.getTargets().containsKey(target))
                        {
                            int pre = ver.addTargets(target,weight);
                            for(Vertex<L> i:vertices)
                            {
                                if(i.equals(targ))
                                {
                                    i.addSources(source,weight);
                                }
                            }
                            //checkrep();
                            return pre;
                        }
                        else {
                            ver.addTargets(target,weight);
                            vertices.add(targ);
                            //checkrep();
                            return 0;
                        }
                }
                if(ver.equals(targ)) {
                    if(ver.getSources().containsKey(source))
                    {
                        int pre = ver.addSources(source,weight);
                        for(Vertex<L> i:vertices)
                        {
                            if(i.equals(sour))
                            {
                                i.addSources(target,weight);
                            }
                        }
                       // checkrep();
                        return pre;
                    }
                    else {
                        ver.addSources(source,weight);
                        vertices.add(sour);
                        //checkrep();
                        return 0;
                    }
                }
            }
            vertices.add(sour);
            vertices.add(targ);
            //checkrep();
            return 0;
        }
        else{//weight==0删边不删点
            Iterator<Vertex<L>> iterator = vertices.iterator();
            while (iterator.hasNext()) {
                Vertex<L> ver = iterator.next();
                if(ver.equals(sour))
                {
                    if(ver.getTargets().containsKey(target)) {
                        for (Vertex<L> i : vertices) {
                            if (i.equals(targ)) {
                                int pre = i.addSources(source,weight);
                                ver.addTargets(target,weight);
                                //checkrep();
                                return pre;
                            }
                        }
                    }
                    else
                    {
                        return 0;
                    }
                }
                if(ver.equals(targ))
                {
                    if(ver.getSources().containsKey(source)) {
                        for (Vertex<L> i : vertices) {
                            if (i.equals(sour)) {
                                int pre = i.addTargets(target,weight);
                                ver.addSources(source,weight);
                                //checkrep();
                                return pre;
                            }
                        }
                    }
                    else
                    {
                        //checkrep();
                        return 0;
                    }
                }
            }
            //checkrep();
            return 0;
        }
    }
    
    @Override public boolean remove(L vertex) {
        Iterator<Vertex<L>> iterator = vertices.iterator();
        while(iterator.hasNext())
        {
            Vertex<L> i = iterator.next();
            if(i.getName()==vertex)
            {
                ArrayList<L> dele =new ArrayList<L>();
                dele.addAll(i.getSources().keySet());
                dele.addAll(i.getTargets().keySet());
                for(Vertex<L> j :vertices)
                {
                    if(dele.contains(j.getName()))
                    {
                        j.addSources(vertex,0);//删除对应边
                        j.addTargets(vertex,0);
                        iterator.remove();
                        checkrep();
                        return true;
                    }
                }
            }
        }
        checkrep();
        return false;
    }
    
    @Override public Set<L> vertices() {
        Set<L> Copyvertices = new HashSet<>();
        for(Vertex<L> i:vertices)
        {
            Copyvertices.add(i.getName());
        }
        return Copyvertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        for(Vertex<L> i:vertices)
        {
            if(i.getName()==target)
            {
                return i.getSources();
            }
        }
        throw new RuntimeException("target dont exist");
    }
    
    @Override public Map<L, Integer> targets(L source) {
        for(Vertex<L> i:vertices)
        {
            if(i.getName()==source)
            {
                return i.getTargets();
            }
        }
        throw new RuntimeException("source dont exist");
    }
    
   @Override
    public String toString()
    {
        if(vertices.isEmpty())
        {
            throw new RuntimeException("empty graph");
        }
        StringBuilder sb = new StringBuilder();
        for(Vertex<L> i:vertices)
        {
            sb.append(i.toString());
        }

        return sb.toString();
    }
    
}

/**
 *
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *使用点集抽象成点的名字，可以去的终点和权重，可以去的起点和权重
 * 例如，假设3个点A------1------>B-------2------>C
 *对于B来说,B.name=B
 * B.sources = {A,1}
 * B.targets = {C,2}
 */
class Vertex<L> {

    private L name;

    private Map<L, Integer> sources = new HashMap<>();

    private Map<L, Integer> targets = new HashMap<>();


    // Abstraction function:
    //   使用map来存储当前的点所能到达的起点和终点及对应的权重
    // Representation invariant:
    //   源点和终点不为点本身，权值非负
    // Safety from rep exposure:
    //   使用private来防止泄露

    Vertex(L name) {
        this.name = name;
    }
    //根据RI设计

    public void checkrep()
    {
        if(this.name==null||this.name=="")
        {
            assert  false;
        }
        L nextname;
        Iterator<L> iterator = this.sources.keySet().iterator();
        while (iterator.hasNext())
        {
            nextname = iterator.next();
            if(nextname.equals(this.name)||nextname.equals("")|| nextname == null||sources.get(nextname)<0)
            {
                assert  false;
            }
        }
        Iterator<L> iterator2 = this.targets.keySet().iterator();
        L nextname2;
        while (iterator2.hasNext())
        {
            nextname2 = iterator2.next();
            if(nextname2.equals(this.name)||nextname2.equals("")|| nextname2 == null||targets.get(nextname2)<0)
            {
                assert  false;
            }
        }
        assert  true;
    }
    /*获取当前点对应源点
     * @param
     *@return  对应源点名和权重构成的图
     * */
    //注意防御性拷贝
    public Map<L, Integer> getSources() {
        Map<L, Integer> source = new HashMap<>();
        source.putAll(sources);
        return source;
    }
    public Map<L, Integer> getTargets() {
        Map<L, Integer> target = new HashMap<>();
        target.putAll(targets);
        return target;
    }
    /*添加源点，如果源点存在就修改键值,weight==0就删除对应边
     * @param  源点，名和权重
     *@return  添加点时返回0。删除或更改权重时返回之前的权重
     * */
    public int addSources(L sources,int weight) {
        if(!this.sources.containsKey(sources)) {
            if(weight ==0) {
                return 0;
            }
            this.sources.put(sources, weight);
            checkrep();
            return  0;
        }
        else {
            int pre = this.sources.get(sources);
            if(weight ==0) {
                this.sources.remove(sources);
                return pre;
            }
            this.sources.replace(sources, weight);
            checkrep();
            return pre;
        }
    }
    //同上
    public int addTargets(L targets,int weight) {
        if(!this.targets.containsKey(targets)) {
            if(weight ==0) {
                return 0;
            }
            this.targets.put(targets, weight);
            checkrep();
            return  0;
        }
        else
        {
            int pre = this.targets.get(targets);
            if(weight==0)
            {
                this.targets.remove(targets);
                return pre;
            }
            this.targets.put(targets,weight);
            checkrep();
            return pre;
        }
    }

    public L getName(){
        return this.name;
    }

    /*判断当前边的顶点名是否和目标顶点名一致（仅名字相同即可）
     * @param  对比的目标顶点
     *@return  若相同则返回true，反之返回false
     * */
    public boolean equals(Vertex<L> obj) {
        if(this.name ==obj.name)
        {
            return true;
        }
        return false;
    }
@Override
    public String toString()
    {
        String res = this.name + sources.toString() + targets.toString();
        return  res;
    }
}
