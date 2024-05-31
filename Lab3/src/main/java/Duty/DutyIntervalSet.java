package Duty;
import IntervalSet.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.LongAccumulator;

public class DutyIntervalSet {
    //private MultiIntervalSet<Employee> timestamp;
    private final NonOverlapIntervalSet<Employee> timestamp;
    private final ArrayList<Employee> employeeList;
    private LocalDate startTime;
    private LocalDate endTime;
    public DutyIntervalSet()
    {
        //this.timestamp = new MultiIntervalSet<Employee>();
        this.timestamp = new NonOverlapIntervalSet<Employee>(new MultiIntervalSet<Employee>());
        employeeList = new ArrayList<>();
    }
    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
        timestamp.setEndTime(TransferDateToLong(endTime));
    }
    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public LocalDate getStartTime() {
        return startTime;
    }
    private long TransferDateToLong(LocalDate date)
    {
        return ChronoUnit.DAYS.between(startTime,date);
    }

    private  LocalDate TransferLongToDate(long date)
    {
        return startTime.plusDays(date);
    }
    public void insertEmployee(String name, String duty, String phoneNum)
    {
        Employee employee = new Employee(name,duty,phoneNum);
        for(Employee i :employeeList)
        {
            if(Objects.equals(i.getName(), name))
            {
                System.out.println("Already have employee"+employee.getName());
                return;
            }
        }
        employeeList.add(employee);
    }



    public int deleteEmployee(String name)
    {
        Iterator<Employee> iterator = employeeList.iterator();
        while(iterator.hasNext())
        {
            Employee next = iterator.next();
            if(Objects.equals(next.getName(), name))
            {
                if(timestamp.size()!=0) {
                    for (int j = 0; j < timestamp.size(); j++) {
                        if (Objects.equals(timestamp.getByIndex(j).getLabel().getName(), name))
                        {
                            System.out.println("Please delete the information in the schedule first");
                            return 0;
                        }
                    }
                }
                iterator.remove();
                return 1;
            }
        }
        System.out.println("Couldn't find the employee"+name);
        return -1;
    }

    public void deleteEmployeeInSchedule(Employee employee)
    {
        if(timestamp.size()!=0) {
            timestamp.remove(employee);
        }
    }


    private void addSchedule(long startTime,long endTime,Employee employee)
    {

        if(timestamp.size()!=0)
        {
            for (int j = 0; j < timestamp.size(); j++)
            {
                if(Objects.equals(timestamp.getByIndex(j).getLabel().getName(), employee.getName()))
                {
                    System.out.println("Already have employee" + employee.getName()+"in schedule");
                    return;
                }
            }
        }
        try {
            timestamp.insert(startTime, endTime, employee);
        }catch (RuntimeException e)
        {
            System.out.println("InterSetOverlap!");
            return;
        }
    }
    public void randGen()
    {
        long endTime = TransferDateToLong(this.endTime);
        long startTime = 0;
        int size = employeeList.size();
        int averageTime = (int) (endTime/size);
        long preEndTime = 0;
        //前i-1个员工均分
        for(int i =0;i<size-1;i++)
        {
            addSchedule(preEndTime,preEndTime+averageTime-1,employeeList.get(i));
            preEndTime = preEndTime + averageTime;
        }
        //最后一个员工获取剩下的
        addSchedule(preEndTime,endTime,employeeList.get(size-1));
    }
    public void addByDate(LocalDate startTime, LocalDate endTime,Employee employee)
    {
        long start = TransferDateToLong(startTime);
        long end = TransferDateToLong(endTime);
        addSchedule(start,end,employee);
    }

    public void check()
    {
        double res = APIs.calcFreeTimeRatio(timestamp);
        if(res == 1)
        {
            System.out.println("Schedule filled");
            return;
        }
        System.out.println("Not filled,free rate:"+ res);
    }

    public void printSchedule()
    {
        if(timestamp.size()==0)
        {
            System.out.println("NULL SCHEDULE");
            return;
        }
        System.out.println("|        Date                NAME                DUTY                PHONENUM        |");
        System.out.println("======================================================================================");
        long flag = 0;
        for(int i=0;i<timestamp.size();i++)
        {
            Item<Employee> item = timestamp.getByIndex(i);
            for(long j = item.getStart();j<=item.getEnd();j++)
            {
                System.out.println("|       "+TransferLongToDate(j)+"               "+item.getLabel().getName()+"                       "+item.getLabel().getDuty()+"               "+item.getLabel().getPhoneNum()+"       |");
                System.out.println("======================================================================================");
                if(flag<item.getEnd())
                {
                    flag = item.getEnd();
                }
            }

        }
        for(long i = flag;i<timestamp.getEndTime();i++)
        {
            System.out.println("|       "+TransferLongToDate(i)   +"                   NULL");
        }
    }
    public void printEmployee()
    {
        if(employeeList.isEmpty())
        {
            System.out.println("Empty EmployeeList");
            return;
        }
        System.out.println("EmployeeList:");
        System.out.println("==================================");
        int flag=0;
        for(Employee i :employeeList)
        {
            System.out.println(flag+" : "+i.toString());
            flag++;
        }
    }
    public ArrayList<Employee> getEmployeeList()
    {
        return employeeList;
    }




}
