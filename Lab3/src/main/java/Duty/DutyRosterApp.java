package Duty;
import IntervalSet.*;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DutyRosterApp {
    public static void main(String[] Args) throws IOException
    {

        System.out.println("HELLO");
        System.out.println("you can type --help to get the command list");
        DutyIntervalSet set = new DutyIntervalSet();
        setDateHandler(set);
        help();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("input command:");
            String input = br.readLine();
            if (input.equals("--help")) {
                help();
                continue;
            }
            if(input.equals("--readFile"))
            {
                ReadFromFile(set);
                continue;
            }
            if (input.equals("--setTime")) {
                setDateHandler(set);
                continue;
            }
            if (input.equals("--exit")) {
                break;
            }
            if(input.equals("--insertEmployee"))
            {
                insertEmployeeHandler(set);
                continue;
            }
            if(input.equals("--deleteEmployee"))
            {
                deleteEmployeeHandler(set);
                continue;
            }
            if(input.equals("--EmployeeList"))
            {
                set.printEmployee();
                continue;
            }
            if(input.equals("--addSchedule"))
            {
                addScheduleHandler(set);
                continue;
            }
            if(input.equals("--random"))
            {

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
            else
            {
                System.out.println("Unknown command");
            }
        }

    }
    public static void help()
    {
        System.out.println("command list");
        System.out.println("all data input should be split by -");
        System.out.println("--readFile              to read a file");
        System.out.println("--setTime               to set the start and end time");
        System.out.println("--insertEmployee        to insert employee");
        System.out.println("--EmployeeList          to list the employee");
        System.out.println("--deleteEmployee        to delete one employee");
        System.out.println("--addSchedule           to add a existing employee to the schedule");
        System.out.println("--random                to randomly generalize a schedule ");
        System.out.println("--check                 to check whether the schedule is full");
        System.out.println("--printSchedule         to print the schedule");
        System.out.println("--exit                  to exit");
    }

    //name-duty-phoneNum
    public static void  insertEmployeeHandler(DutyIntervalSet set) throws IOException {
        String input;
        System.out.println("type --exit to exit");
        System.out.println("input format:  name-duty-phoneNum");
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
            if(parts.length!=3)
            {
                System.out.println("Error input");
                continue;
            }
            set.insertEmployee(parts[0],parts[1],parts[2]);
            System.out.println("insert successfully!");
            set.printEmployee();
        }
    }
    public static void setDateHandler(DutyIntervalSet set) throws IOException {
        System.out.println("type  --exit to exit");
        String inputDate;
        while(true) {
            LocalDate startDate = null;
            LocalDate endDate = null;
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
            System.out.println("PLEASE SET THE END TIME:(YYYY-MM-DD)");
            while (endDate == null) {
                inputDate = br.readLine();
                if(Objects.equals(inputDate, "--exit"))
                {
                    return;
                }
                endDate = DateSplitter(inputDate);
            }
            System.out.println(endDate);
            if (endDate.isBefore(startDate)||endDate.equals(startDate)) {
                System.out.println("Error:endDate should be after than startDate!!");
                continue;
            }
            set.setEndTime(endDate);
            break;
        }
    }
    public static void deleteEmployeeHandler(DutyIntervalSet set) throws IOException {
        System.out.println("type  --exit to exit");
        set.printEmployee();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("TYPE the ID to select which one to delete");
            String input = br.readLine();
            if (Objects.equals(input, "--exit")) {
                return;
            }
            boolean isMatch = Pattern.matches("\\d", input);
            if (!isMatch) {
                System.out.println("Error Input,please enter a num");
                continue;
            }
            int ID = Integer.parseInt(input);
            if (ID < 0 || ID > set.getEmployeeList().size()) {
                System.out.println("Error ID");
                continue;
            }
            Employee employee = set.getEmployeeList().get(ID);
            System.out.println("you will delete employee: " + employee.toString());
            System.out.println("Type y to continue");
            if (!(Objects.equals(br.readLine(), "y") || Objects.equals(br.readLine(), "Y"))) {
                continue;
            }
            int flag = set.deleteEmployee(employee.getName());
            if(flag == -1)
            {
                System.out.println("Delete failure");
                continue;
            }
            if(flag==1)
            {
                System.out.println("Successfully delete");
                set.printEmployee();
            }
            if(flag==0)
            {
                System.out.println("Type Y if you want to delete the employee in schedule");
                if(br.readLine().equals("Y")||br.readLine().equals("y"))
                {
                    set.deleteEmployeeInSchedule(employee);
                }
                set.deleteEmployee(employee.getName());
            }

        }
    }

    public static void addScheduleHandler(DutyIntervalSet set) throws IOException {
        if(set.getEmployeeList().isEmpty())
        {
            System.out.println("EmployeeList is Empty,please add some employee first");
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            System.out.println("please type an employee's ID from the following list:");
            set.printEmployee();
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
            if(ID<0||ID>set.getEmployeeList().size())
            {
                System.out.println("Error ID");
                continue;
            }
            Employee employee = set.getEmployeeList().get(ID);
            System.out.println("add employee:"+employee.toString()+" to schedule");
            System.out.println("StartTime:"+set.getStartTime()+"EndTime:"+set.getEndTime());
            String inputDay;
            LocalDate start = null;

            System.out.println("please enter the start day:");
            while(start==null)
            {
                inputDay = br.readLine();
                start = DateSplitter(inputDay);
            }
            System.out.println("please enter the end day:");
            LocalDate end = null;
            while(end==null)
            {
                inputDay = br.readLine();
                end = DateSplitter(inputDay);
            }
            if(start.isBefore(set.getStartTime())||end.isAfter(set.getEndTime()))
            {
                System.out.println("Not in the time frame");
                continue;
            }
            set.addByDate(start,end,employee);
            System.out.println("Insert successfully!");
            System.out.println("you can type  --exit to exit");
        }
    }

    public static void RandomHandler(DutyIntervalSet set)
    {
        if(set.getEmployeeList().isEmpty())
        {
            System.out.println("EmployeeList is Empty,please insert Employee first");
            return;
        }
        set.randGen();
        System.out.println("Done");
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
    public static void ReadFromFile(DutyIntervalSet set) throws IOException {
        System.out.println("Please enter the file dictionary");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File fp;
        FileInputStream ip;
        try {
            fp = new File(br.readLine());
            ip = new FileInputStream(fp);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file");
            return;
        }

        InputStreamReader reader = new InputStreamReader(ip, StandardCharsets.UTF_8);
        int size = ip.available();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            char c =(char) reader.read();
            if(c!= ' ') {
                sb.append(c);
            }
        }
        String REGEX = "[\\s{}]";
        String[] res = sb.toString().split(REGEX);
        ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(res));
        int EmployeeFlag = -1;
        int PeriodFlag = -1;
        int RosterFlag = -1;
        list1.removeIf(e -> e.matches(""));
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).equals("Employee")) {
                EmployeeFlag = i;
            }
            if (list1.get(i).equals("Period")) {
                PeriodFlag = i;
            }
            if (list1.get(i).equals("Roster")) {
                RosterFlag = i;
            }
        }
        int EmployeeEnd = getClose(EmployeeFlag, PeriodFlag, RosterFlag, list1.size());
        int RosterEnd = getClose(RosterFlag, PeriodFlag, EmployeeFlag, list1.size());
        String PhoRegex = "\\d{3}-\\d{4}-\\d{4}";
        //Employee
        if (EmployeeFlag != -1) {
            for (int i = EmployeeFlag + 1; (i + 2) <= EmployeeEnd; i += 2) {
                String[] str = list1.get(i + 1).split(",");
                Pattern p = Pattern.compile(PhoRegex);
                Matcher m = p.matcher(str[1]);
                if(!m.matches())
                {
                    System.out.println("PhoneNum error: "+str[1]);
                    return;
                }
                set.insertEmployee(list1.get(i), str[0], str[1]);

            }
        }

        //Period
        if (PeriodFlag != -1) {
            String[] str = list1.get(PeriodFlag + 1).split(",");
            set.setStartTime(DateSplitter(str[0]));
            set.setEndTime(DateSplitter(str[1]));
        }
        //Roster
        if (RosterFlag != -1) {
            for (int i = RosterFlag + 1; (i + 2) <= RosterEnd;i+=2 ) {
                boolean isFind = false;
                for (Employee e : set.getEmployeeList()) {
                    if (Objects.equals(e.getName(), list1.get(i))) {
                        isFind =true;
                        String[] str = list1.get(i + 1).split(",");
                        set.addByDate(DateSplitter(str[0]), DateSplitter(str[1]), e);
                        break;
                    }
                }
                if(!isFind)
                {
                    System.out.println("Couldn't find the employee: "+list1.get(i));
                    return;
                }
            }
        }
    }


    private static int getClose(int close,int first ,int second,int third)
    {
        int[] arr={close,first,second,third};
        ArrayList<Integer> list = new ArrayList<>();
        for(int i :arr)
        {
            list.add(i);
        }
        Collections.sort(list);
        for(int i=0;i<4;i++)
        {
            if(list.get(i)==close)
            {
                return list.get(i+1);
            }
        }
        return -1;
    }
}
