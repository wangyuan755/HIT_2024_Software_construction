package Course;

import Duty.DutyIntervalSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;


public class CourseScheduleApp {
    public static void main(String[] Args) throws IOException
    {
        CourseIntervalSet set = new CourseIntervalSet();
        System.out.println("HELLO");
        System.out.println("you can type --help to get the command list");
        help();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("input command:");
            String input = br.readLine();
            if (input.equals("--help")) {
                help();
                continue;
            }
            if (input.equals("--setStartTime")) {
                setStartTimeHandler(set);
                continue;
            }
            if(input.equals("--setPeriod"))
            {
                setPeriodHandler(set);
                continue;
            }
            if (input.equals("--exit")) {
                break;
            }
            if(input.equals("--insertCourse"))
            {
                insertCourseHandler(set);
                continue;
            }
            if(input.equals("--setLength"))
            {
                setSemesterLengthHandler(set);
                continue;
            }
            if(input.equals("--CourseList"))
            {
                set.printCourseList();
                continue;
            }
            if(input.equals("--addSchedule"))
            {
                addScheduleHandler(set);
                continue;
            }
            if(input.equals("--check"))
            {
                set.check();
                continue;
            }
            if(input.equals("--printSchedule"))
            {
                set.printSchedule();
                continue;
            }
            if(input.equals("--searchByDate"))
            {
                searchByDateHandler(set);
                continue;
            }
            else
            {
                System.out.println("Unknown command");
            }
        }
}

    private static void setSemesterLengthHandler(CourseIntervalSet set) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("please enter the total week num of the semester");
        String input = br.readLine();
        int length  = Integer.parseInt(input);
        if(length<=0)
        {
            System.out.println("Error input");
            return;
        }
        set.setSemesterLength(length);
    }


    private static void setStartTimeHandler(CourseIntervalSet set) throws IOException {
        System.out.println("type  --exit to exit");
        String inputDate;
        while(true) {
            LocalDate startDate = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("PLEASE SET THE START TIME:(YYYY-MM-DD)");
            while (startDate == null) {
                inputDate = br.readLine();
                if(Objects.equals(inputDate, "--exit"))
                {
                    return;
                }
                startDate = DateSplitter(inputDate);
            }
            System.out.println(startDate);
            set.setStartTime(startDate);
            break;
            }
    }
    private static void insertCourseHandler(CourseIntervalSet set) throws IOException {
        String input;
        System.out.println("type --exit to exit");
        System.out.println("input format:  CourseID-CourseName-TeacherName-Location-creditHour");
        System.out.println("CreditHour should be even");
        while (true)
        {
            String[] parts;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
            if(Objects.equals(input, "--exit"))
            {
                return;
            }
            parts = inputSplitter(input);
            if(parts.length!=5)
            {
                System.out.println("Error input");
                continue;
            }
            set.insertCourse(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3], Integer.parseInt(parts[4]));
        }

    }
    private static void searchByDateHandler(CourseIntervalSet set) throws IOException {
        if(!set.isPeriod())
        {
            System.out.println("Schedule is not Periodic,please make it periodic first");
            return;
        }
        String inputDate;
        while(true) {
            LocalDate date = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("PLEASE ENTER THE TIME:(YYYY-MM-DD)");
            while(date==null)
            {
                inputDate = br.readLine();
                if(Objects.equals(inputDate, "--exit"))
                {
                    return;
                }
                date = DateSplitter(inputDate);
                if (date != null && date.isBefore(set.getStartTime())) {
                    System.out.println("inputDate should be later than startTime");
                }
                if (date != null && date.isAfter(set.getEndTime())) {
                    System.out.println("inputDate should be earlier than EndTime");
                }
            }

            System.out.println(date+CourseIntervalSet.toWeekday((int) (set.TransferDateToLong(date)%7)));
            int start = (int) ((set.TransferDateToLong(date))*5);
            int end = start + 4;
            set.printByDay(start,end);
        }
    }


    private static void addScheduleHandler(CourseIntervalSet set) throws IOException {
        if(set.getCourseList().isEmpty())
        {
            System.out.println("CourseList is Empty,please add some Course first");
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("please type an Course's ID from the following list:");
            System.out.println("enter --exit to exit");
            set.printCourseList();
            String input =  br.readLine();
            if(Objects.equals(input, "--exit")) {
                return;
            }
            boolean isMatch = Pattern.matches("\\d",input);
            if(!isMatch)
            {
                System.out.println("Error Input,please enter a num");
                continue;
            }
            int ID = Integer.parseInt(input);
            Course resCourse  = new Course();
            boolean flag=false;
            for(Course c:set.getCourseList().keySet())
            {
                if(c.getCourseID()==ID)
                {
                    resCourse = c;
                    flag = true;
                }
            }
            if(flag)
            {
                System.out.println("add Course:"+resCourse.toString()+" to schedule");
                String inputDay;
                System.out.println("please enter the day and time:  day-time");
                System.out.println("day should be 1-7");
                System.out.println("Time should be 1-5,mean the X class of the day,1-(8:00-10:00),2-(10:00-12:00),3-(13:00-15:00),4-(15:00-17:00),5-(19:00-21:00)");
                System.out.println("for example: 1-2  mean monday 10:00-12:00");
                String inputTime = br.readLine();
                String[] res = inputTime.split("-");
                if(res.length!=2)
                {
                    System.out.println("Error input");
                    continue;
                }
                int day = Integer.parseInt(res[0]);
                int time = Integer.parseInt(res[1]);
                if(day<=0||time<=0||day>7||time>5)
                {
                    System.out.println("error Input");
                    continue;
                }
                else
                {
                    set.addSchedule(resCourse,day,time);
                    //System.out.println("add successfully");
                    continue;
                }
            }
            else
            {
                System.out.println("Couldn't find the ID");
                continue;
            }


        }
    }
    private static void setPeriodHandler(CourseIntervalSet set) {
        if(set==null)
        {
            System.out.println("NULL set");
            return;
        }
        set.setPeriod();
    }


    private static void help() {
        System.out.println("command list");
        System.out.println("all data input should be split by -");
        System.out.println("--setStartTime          to set the semester's start time");
        System.out.println("--setLength             to set the semesterLength");
        System.out.println("--insertCourse          to insert course");
        System.out.println("--CourseList            to list the Course");
        System.out.println("--addSchedule           to add a existing course to the schedule");
        System.out.println("--setPeriod             to set Period by exist Schedule");
        System.out.println("--check                 to check whether the Course are all be scheduled,conflictRadio,FreeRadio");
        System.out.println("--printSchedule         to print the schedule");
        System.out.println("--searchByDate          to find one day's course");
        System.out.println("--exit                  to exit");
    }
    private static String[] inputSplitter(String input)
    {
        return input.split("-");
    }
    public static LocalDate DateSplitter(String in) {
        try {
            String[] parts = in.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            if (!(month <= 12 && month > 0 && year > 0 && day <= 31 && day > 0) )
            {
                throw new NumberFormatException();
            }
            return LocalDate.of(year, month, day);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Error Input");
        }
        return null;
    }
    }
