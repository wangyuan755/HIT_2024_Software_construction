package IntervalSet;

import java.util.Set;
public interface IntervalSet<L> {

    /** 创建空时间戳
     * */
    public void empty();

    /**
    返回时间戳的尺寸
     */
    public int size();

    /**返回时间戳上顺序排列第i个Item
     *
     * @param i 想要的第i个元素
     * @return 第i个元素
     */
    public Item<L> getByIndex(int i);
    /**根据起止时间和标名插入到时间戳内，插入时会按起始时间做好排序
     * @param start 起始时间
     * @param end 终止时间
     * @param label 标号
     */
    public void insert(long start, long end, L label);
    /**返回时间戳上所有标号构成的集合
     * @return 所有标号构成的集合
     */
    public Set<L> labels();
    /**根据标号删除时间戳上的元素，成功返回true，失败返回false
     * @param label 要移除的标号
     * @return true if 移除成功
     */
    public boolean remove(L label);
    /**
    @param label：需要的标号
    @return  返回时间戳上特定标号的开始时间
     */
    public long start (L label);
    /**
     @param label：需要的标号
     @return  返回时间戳上特定标号的结束时间
     */
    public long end (L label);
    /**返回整个时间戳的终止时间
     * @return 终止时间
    */
    public long getEndTime();
    /**返回整个时间戳的起始时间（一般为0）
     * @return 起始时间
     */
    public long getStartTime();

    /**设置整个时间戳的终止时间
    *@param time 终止时间
    */

    public void setEndTime(long time);

    /**设置标号可重复标志位
     * @param i true为不可重复
     */
    public void setFlag(boolean i);
    public String toString();

    /**
     * 检差时间戳是否填满
     */
    public void checkBlank();

    /**
     * 设置时间戳周期
     * @param periodic 周期
     * @param cycleNum 循环轮次
     */
    public void setPeriodic(long periodic,long cycleNum);
}
