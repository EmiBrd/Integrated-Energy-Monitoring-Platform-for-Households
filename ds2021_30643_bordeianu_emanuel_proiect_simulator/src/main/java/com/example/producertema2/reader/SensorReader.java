package com.example.producertema2.reader;

import com.example.producertema2.model.MeasurementAssignment2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SensorReader {

    public List<MeasurementAssignment2> getMeasurements(Long idSensor) {
        //Long idSensor = 32L;

        List<MeasurementAssignment2> measurements = new ArrayList<>();
        try {
            FileReader fileReader = new
                    FileReader("C:\\D\\An_4_sem_1\\SD\\Lab\\Assignment_2\\sensor.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String l;

            LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 8, 1, 0, 0);
            //LocalDateTime localDateTime = LocalDateTime.now();
            System.out.println("\nlocalDateTime = " + localDateTime.toString()+"\n");

            int i = 0;
            while (
                    (
                            (l = bufferedReader.readLine()) != null
                    )
                            && (i < 60)
            ) {
                StringTokenizer stringTokenizer = new StringTokenizer(l);
                Double measurementValue = Double.valueOf(stringTokenizer.nextToken());

                MeasurementAssignment2 measurementAssignment2 = new
                        MeasurementAssignment2(idSensor, localDateTime, measurementValue);
                measurements.add( new MeasurementAssignment2(idSensor, localDateTime, measurementValue) );
                System.out.println("measurementAssign2 = " + measurementAssignment2);
                localDateTime = localDateTime.plusHours(1);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return measurements;
    }


}
