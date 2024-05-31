package Duty;
import IntervalSet.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.LinkedList;

public class Employee {
    /**
     * Abstraction function:
     * 将员工数据抽象为这个类
     * name为员工名
     * duty为职责
     * phoneNum为电话
     * Representation invariant:
     * null（不对姓名电话之类的进行验证）
     * Safety from rep exposure:
     * 属性均为private
     */
    private final String name;
    private final String duty;

    private final String phoneNum;


    public Employee(String name,String duty,String phoneNum)
    {
        this.name = name;
        this.duty = duty;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getDuty()
    {
        return duty;
    }

    public String getPhoneNum() {
        return phoneNum;
    }



    public String toString()
    {
        return this.name+"---"+this.duty+"---"+this.phoneNum;
    }
}
