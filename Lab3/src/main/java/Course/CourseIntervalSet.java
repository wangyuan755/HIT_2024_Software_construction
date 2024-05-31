package Course;

import IntervalSet.APIs;
import IntervalSet.Item;
import IntervalSet.MultiIntervalSet;
import IntervalSet.PeriodicIntervalSet;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CourseIntervalSet {
    /**
     * Abstraction function:
     * 将课程表数据抽象为这个类
     * 用周期性的时间戳
     * 用哈希表来储存课程列表，其值为剩余未分配的学时数
     * startTime为学期开始日期
     * semesterLength为学期周长
     * isPeriod维护是否周期化
     * Representation invariant:
     *
     * Safety from rep exposure:
     * 属性均为private
     */
    private final PeriodicIntervalSet<Course> timestamp;

    private final HashMap<Course,Integer> CourseList;
    private LocalDate startTime;

    private LocalDate endTime;

    private int semesterLength;

    private boolean isPeriod ;
    public CourseIntervalSet()
    {
        this.CourseList = new HashMap<Course,Integer>();
        this.timestamp = new PeriodicIntervalSet<Course>(new MultiIntervalSet<Course>());
    }


    public int getSemesterLength() {
        return semesterLength;
    }

    public void setSemesterLength(int semesterLength) {
        this.semesterLength = semesterLength;
        timestamp.setEndTime(semesterLength*7*5);
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }
    public LocalDate getStartTime() {
        return startTime;
    }
    public long TransferDateToLong(LocalDate date)
    {
        return ChronoUnit.DAYS.between(startTime,date);
    }
    public HashMap<Course,Integer> getCourseList()
    {
        return this.CourseList;
    }
    //增加一组课程，每门课程的信息包括：课程 ID、课程名称、教师名字、地点、周学时数（偶数）
    public void insertCourse(long ID,String CourseName , String Teacher,String Location,int creditHour)
    {
        if(creditHour<=0)
        {
            System.out.println("creditHour should be bigger than 0");
            return;
        }
        if(creditHour%2 !=0)
        {
            System.out.println("Credit Hour should be even number");
            return;
        }
        for(Course i:CourseList.keySet())
        {
            if(i.getCourseID()==ID|| Objects.equals(i.getCourseName(), CourseName))
            {
                System.out.println("CourseName or CourseID already exist");
                return;
            }
        }
        Course course = new Course(ID,CourseName,Teacher,Location,creditHour);
        CourseList.put(course,creditHour);
    }

    //day: 1 - 7
    //courseNum : 1-5
    private int timeTransfer(int day,int courseNum)
    {
        return (day-1) * 5 + courseNum-1;
    }
    //手工选择某个课程、上课时间（只能是 8-10 时、10-12 时、13-15 时、
    //15-17 时、19-21 时），为其安排一次课，每次课的时间长度为 2 小时；可重复安
    //排，直到达到周学时数目时该课程不能再安排；
    public void addSchedule(Course course,int day,int CourseHour)
    {
        int time = timeTransfer(day,CourseHour);
        int remain = CourseList.get(course);
        if(remain==0)
        {
            System.out.println("Credit Hour has full");
            return;
        }
        timestamp.insert(time,time+1,course);
        CourseList.replace(course,remain-2);
    }
    //上步骤过程中，随时可查看哪些课程没安排、当前每周的空闲时间比例、重复时间比例；
    public HashSet<Course> getNonScheduleCourse()
    {
        HashSet<Course> res= new HashSet<>();
        for(Course course:CourseList.keySet())
        {
            if(CourseList.get(course)==course.getCreditHour())
            {
                res.add(course);
            }
        }
        if(res.isEmpty())
        {
            return null;
        }
        return res;
    }
    public double ConflictTimeRadio()
    {
        return APIs.calcConflictRatio(this.timestamp);
    }

    public double FreeTimeRadio()
    {
        return APIs.calcFreeTimeRatio(this.timestamp);
    }

    public void setPeriod()
    {
        if(semesterLength<=0)
        {
            System.out.println("semesterLength not set");
            return;
        }
        timestamp.setPeriodic(35,semesterLength);
        endTime=startTime.plusDays(semesterLength*7);
        isPeriod = true;
    }

//TODO
    public void printCourseList() {
        if(CourseList.isEmpty())
        {
            System.out.println("Empty CourseList");
            return;
        }
        System.out.println("CourseList:");
        System.out.println("==================================");
        for(Course i :CourseList.keySet())
        {
            System.out.println(i.toString());
        }
    }

    public void check() {
        HashSet<Course> set = getNonScheduleCourse();
        if(set==null) {
            System.out.println("All course have been scheduled");
        }
        else {
            System.out.println("this following course are not scheduled:");
            for(Course c :set)
            {
                System.out.println(c.toString());
            }
        }
        System.out.println("ConflictTimeRadio:" + ConflictTimeRadio());
        System.out.println("FreeTimeRadio:"+FreeTimeRadio());
    }

    public void printByDay(int start,int end)
    {
        if(timestamp.size()==0)
        {
            System.out.println("NULL SCHEDULE");
            return;
        }

        System.out.println("|       Time                CourseName                Teacher               Location        |");
        System.out.println("=============================================================================================");
        for(int i = 0;i<timestamp.size();i++)
        {
            Item<Course> item = timestamp.getByIndex(i);
            int time = (int) item.getStart();
            if(time>=start&&time<end)
            {
                System.out.println("|       "+toHour(time%5) +"               "+ item.getLabel().getCourseName()+"               "+ item.getLabel().getCourseTeacher()+"               " + item.getLabel().getLocation()+"               "+ "|");
            }
        }
    }
    public void printSchedule() {
        if(timestamp.size()==0)
        {
            System.out.println("NULL SCHEDULE");
            return;
        }
        System.out.println("One print one week");
        System.out.println("|        Day                Time                CourseName                Teacher               Location        |");
        System.out.println("=================================================================================================================");
        for(int i = 0 ;i<timestamp.size();i++)
        {
            Item<Course> item = timestamp.getByIndex(i);
            int time = (int)item.getStart();
            System.out.println("|       "+toWeekday((time/5)+1)+"               "+ toHour(time%5) +"               "+ item.getLabel().getCourseName()+"               "+ item.getLabel().getCourseTeacher()+"               " + item.getLabel().getLocation()+"               "+ "|");
        }
    }
    public static String toHour(int i)
    {
        if(i == 0)
        {
            return "8:00-10:00";
        }
        if(i == 1)
        {
            return "10:00-12:00";
        }
        if(i == 2)
        {
            return "13:00-15:00";
        }
        if(i == 3)
        {
            return "15:00-17:00";
        }
        if(i == 4)
        {
            return "19:00-21:00";
        }
        else
        {
            return "error";
        }
    }

    public static String toWeekday(int i)
    {
        if(i == 1)
        {
            return "Monday";
        }
        if(i == 2)
        {
            return "Tuesday";
        }
        if(i == 3)
        {
            return "Wednesday";
        }
        if(i == 4)
        {
            return "Thursday";
        }
        if(i == 5)
        {
            return "Friday";
        }
        if(i == 6)
        {
            return "Saturday";
        }
        if(i == 7)
        {
            return "Sunday";
        }
        else {
            return "error";
        }
    }

    public boolean isPeriod() {
        return isPeriod;
    }

    public LocalDate getEndTime() {
        return endTime;
    }
}

