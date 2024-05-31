package Course;

import IntervalSet.APIs;

public class Course{
    /**
     * Abstraction function:
     * 将课程数据抽象为这个类
     * CourseName为课程名
     * CourseTeacher为授课教师
     * Location为地点
     * CreditHour为每周学时数
     * Representation invariant:
     * CreditHour为偶数
     * Safety from rep exposure:
     * 属性均为private
     */
    private long CourseID;
    private String CourseName;
    private String CourseTeacher;
    private String Location;
    private int creditHour;

    public Course(long courseID, String courseName, String courseTeacher, String location, int creditHour) {
        CourseID = courseID;
        CourseName = courseName;
        CourseTeacher = courseTeacher;
        Location = location;
        this.creditHour = creditHour;

    }

    public Course() {
    }

    public long getCourseID() {
        return CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getCourseTeacher() {
        return CourseTeacher;
    }

    public String getLocation() {
        return Location;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public void setCourseID(long courseID) {
        CourseID = courseID;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setCourseTeacher(String courseTeacher) {
        CourseTeacher = courseTeacher;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }
    public boolean equals(Course course) {
        if(this.getCourseID()==this.CourseID)
        {
            return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
            return this.CourseID+"---"+this.CourseName+"---"+this.CourseTeacher+"---"+this.Location;
    }


}
