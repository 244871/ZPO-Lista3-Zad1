import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import javafx.util.Pair;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVTester {

    public static ArrayList<Teacher> teachers = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        String fileName = "src/main/resources/teachers";
        try (Reader reader= Files.newBufferedReader(Paths.get(fileName))){
            ColumnPositionMappingStrategy<Teacher> strategy = new ColumnPositionMappingStrategy<Teacher>();
            strategy.setType(Teacher.class);
            String[] memberFieldsToBindTo={"name", "surname", "salary", "district", "position"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(0)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<Teacher> teacherIterator = csvToBean.iterator();
            while(teacherIterator.hasNext()){
                teachers.add(teacherIterator.next());
            }

            aboveSalary(149769);
            meanSalary( "Penn Manor");
            districtCounter();
            meanSalaryPosition();
        }
    }

    private static void aboveSalary(int dividor) {
        int teacherCounter = 0;
        int aboveCounter = 0;

        for (Teacher teacher : teachers) {
            if(teacher.getSalary() > dividor)
                aboveCounter++;
            teacherCounter ++;
        }

        System.out.println("Nauczylisli zarabiajacycwięcej niż " + dividor + " jest " + aboveCounter + "\nzarabiających mniej: " + (teacherCounter - aboveCounter));
    }

    private static void meanSalary(String district){
        int teacherCounter = 0;
        int salaryCounter=0;
        double deviation=0;

        for (Teacher teacher : teachers) {
            if (teacher.getDistrict().startsWith(district)){
                teacherCounter++;
                salaryCounter += teacher.getSalary();
                deviation=Math.pow(teacher.getSalary()-(salaryCounter/teacherCounter),2)/teacherCounter;
            }
        }
        double meanSalary=((double)salaryCounter/(double)teacherCounter);
        System.out.println("Srednia zarobków w hrabstwie "+ district+" jest równa: "+meanSalary);
        System.out.println("Odchylenie standardowe: "+(Math.sqrt(deviation)));
    }

    private static void districtCounter(){

        Map<String, Integer> districtCounter = new HashMap<>();
        for (Teacher teacher : teachers) {
            districtCounter.putIfAbsent(teacher.getDistrict(), 1);
        }
        System.out.println("Ilość Hrabstw: " + districtCounter.size());
    }

    private static void meanSalaryPosition(){
        Map<String, ArrayList<Teacher>> salaryCaounter = new HashMap<>();
        for (Teacher teacher : teachers) {
            if(teacher.getSalary() == 0)
                continue;
            ArrayList<Teacher> value = salaryCaounter.get(teacher.getPosition());
            if(value == null){
                ArrayList<Teacher> teacherValue = new ArrayList<>();
                teacherValue.add(teacher);
                salaryCaounter.put(teacher.getPosition(), teacherValue);
            }
            else{
                value.add(teacher);
                salaryCaounter.put(teacher.getPosition(),value);
            }
        }
        for(Map.Entry<String, ArrayList<Teacher>> entry : salaryCaounter.entrySet()) {
            String key = entry.getKey();
            ArrayList<Teacher> teachersOnPosition = entry.getValue();
            int teacherCounter = teachersOnPosition.size();
            int salaryCount=0;
            double deviation=0;
            for (Teacher teacher : teachersOnPosition) {
                salaryCount += teacher.getSalary();
                deviation=Math.pow(teacher.getSalary()-(salaryCount/teacherCounter),2)/teacherCounter;
            }
            double meanSalary=((double)salaryCount/(double)teacherCounter);
            System.out.println("Dla stanowiska "+ key+" średnie zarobki to: "+meanSalary+" , a odchylenie standardowe: "+(Math.sqrt(deviation)));

        }
    }


}
