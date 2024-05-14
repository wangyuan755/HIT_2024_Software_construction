/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2;

import java.util.*;
import java.util.spi.AbstractResourceBundleProvider;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 * 用边的形式来构建有向图Graph，
 * Grapg.vertices为图的点名称构成的集合
 * Graph.edges代表图的边构成的集合
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   vertices为图中点，edges为图中有向边，AF为到对应有向图的映射
    // Representation invariant:
    //   两点之间最多有一条边，顶点非空，权重大于0
    // Safety from rep exposure:
    //   内部变量均为private
    public ConcreteEdgesGraph()
    {

    }
    // 边和点不为空，无重复的边,权值大于0
    private void checkrep()
    {
        for(L i : vertices)
        {
            assert(i!=null):"NULL vertex";
        }
        for(int i = 0;i<edges.size();i++)
        {
            assert(edges.get(i)!=null):"NULL edge";
            assert (edges.get(i).getWeight()>0):"error weight";
            for(int j = i+1;j<edges.size();j++)
            {
             if(edges.get(j).equals(edges.get(i)))
             {
                 assert false : "repeated edges";
             }
            }
        }
    }
    //以下函数的设计规约mit的文档已给出

    @Override public boolean add(L vertex) {
        return vertices.add(vertex);
    }

    @Override public int set(L source, L target, int weight) {
       if(weight<0)
       {
            System.out.println("error weight");
           return -1;
       }
       if(source.equals(target))
       {
           System.out.println("same vertex for source and target");
           return -1;
       }
       Edge<L> newedge = new Edge<L>(source,target,weight);
        //点不存在，加点加边返回0
       if(!vertices.contains(source)||!vertices.contains(target))
       {
           if(weight>0)
           {
               if (!vertices.contains(source)) {
                   vertices.add(source);
               }
               if (!vertices.contains(target)) {
                   vertices.add(target);
               }
               edges.add(newedge);
               checkrep();
               return 0;
           }
           else //weight==0
           {
               checkrep();
               return 0;
           }
       }

        Iterator<Edge<L>> iterator=edges.iterator();
       while (iterator.hasNext())
       {
           Edge<L> nextedge = iterator.next();
           if(nextedge.sameEdge(source,target))  //存在相同边
           {
               int preweight = nextedge.getWeight();
               //都存在且权值为0，删去原有，返回原有权值
               if(weight==0)
               {
                    iterator.remove();
                   checkrep();
                    return preweight;
               }
               // 更新权值返回原来权值
                nextedge.setWeight(weight);
               checkrep();
               return preweight;
           }
       }
        //点存在边不存在，加边返回
        edges.add(newedge);
        checkrep();
        return 0;
    }

    
    @Override public boolean remove(L vertex) {
            if(vertices.contains(vertex))
            {
                Iterator<Edge<L>> iterator=edges.iterator();
                while (iterator.hasNext())
                {
                    Edge<L> nextedge = iterator.next();
                    if(nextedge.getSource()==vertex||nextedge.getTarget()==vertex)
                    {
                        iterator.remove();
                    }
                }
                vertices.remove(vertex);
                checkrep();
                return true;
            }
            return false;
    }
    @Override public Set<L> vertices() {
        //防御性拷贝
        Set<L> Copyvertices = new HashSet<>();
        Copyvertices.addAll(vertices);
        return Copyvertices;
    }
    @Override
    public Map<L, Integer> sources(L target) {
        if(!vertices.contains(target))
        {
            //throw new RuntimeException("target do not exist");
            System.out.println("target do not exist");
            return null;
        }
        Map<L,Integer> resmap = new HashMap<>();
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge<L> nextedge = iterator.next();
            if(nextedge.getTarget().equals(target))
            {
                resmap.put(nextedge.getSource(),nextedge.getWeight());
            }
        }
        return resmap;
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        if(!vertices.contains(source))
        {
            //throw new RuntimeException("source do not exist");
            System.out.println("source do not exist");
            return null;
        }
        Map<L,Integer> resmap = new HashMap<>();
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge<L> nextedge = iterator.next();
            if(nextedge.getSource().equals(source))
            {
                resmap.put(nextedge.getTarget(),nextedge.getWeight());
            }
        }
        return resmap;
    }
    
    @Override
    public String toString()
    {
        if(edges.isEmpty())
        {
            throw new RuntimeException("empty graph");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<edges.size();i++)
        {
            sb.append(edges.get(i).toString());
        }
        return  "vertices:" + vertices.toString() + "Edges:"+sb;
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * 用边来存储有向图的主要信息，把边抽象为原点终点和权重，
 * 例如{source,target,1}就代表一条从source到target的权重为1的边
 */
class  Edge<L> {

    // Abstraction function:
    //   带权边抽象为原点，终点，权重
    // Representation invariant:
    //   原点终点不为空且不重合，权重非负（checkrep）
    // Safety from rep exposure:
    //   上述三个内部参数均为private
    private final L source;
    private final L target;
    private int weight;
    // TODO constructor
    public Edge(L source,L target,int weight)
    {
        this.source = source;
        this.target = target;
        this.weight = weight;
        //checkrep
        assert (checkrep()):"build error";
    }
    // 根据RI
    private boolean checkrep()
    {
        if(this.target==null||this.source==null|| this.source.equals(this.target) ||this.weight<0)
        {
            return false;
        }
        return true;
    }


    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int newweight)
    {
        this.weight = newweight;
        assert (checkrep()):"error weight";
    }
    public L getSource() {
        return this.source;
    }

    public L getTarget() {
        return this.target;
    }

    /*判断当前边的顶点是否和输入的两个顶点相同
     * @param  对比的目标顶点
     *@return  若相同则返回true，反之返回false
     * */
    public boolean sameEdge(L source,L target)
    {
        if(this.source ==source&&this.target==target)
        {
            return true;
        }
        return false;
    }
    /*判断当前边是否和某个特定边的顶点相同（不考虑权重）
     * @param  对比的目标边
     *@return  若相同则返回true，反之返回false
     * */
    public boolean equals(Edge<L> edge)
    {
        if(this.source.equals(edge.source)&&this.target.equals(edge.target))
        {
            return true;
        }
        return false;
    }


    /*将Edge转为String
    * @param  null
    *@return  按source，target，weight,空格排列的字符串，如"SourceTarget1 "
    * */
    @Override
    public String toString()
    {
        //String res = "source:" + this.source + "--weight:" + Integer.toString(weight) + "--target:" + this.target;
        String res = this.source.toString() + this.target.toString() + Integer.toString(weight)+ " ";
        return res;
    }
}
