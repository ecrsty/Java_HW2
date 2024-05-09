package Task2;

import Task1.ToxicPollutionPerMonth;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Client {
    // метод генерации словаря выбросов
    public static Map<LocalDate, List<Integer>> generateData(){

        HashMap<LocalDate, List<Integer>> toxicPollutionPerMonth = new HashMap<>();

        for(int i = 1; i < LocalDate.of(2023, 4, 1).lengthOfMonth(); i++){
            toxicPollutionPerMonth.put(LocalDate.of(2023, 4, i), new Random()
                    .ints(24, 0, 400)
                    .boxed()
                    .toList());
        }
        return toxicPollutionPerMonth;
    }

    public static void main(String[] args) {
        try {
            Socket server = new Socket("localhost", 27115);
            System.out.println("Соединение с сервером установлено");

            System.out.println("Генерация данных...");
            ToxicPollutionPerMonth toxicPollutionPerMonth = new ToxicPollutionPerMonth(generateData());

            System.out.println("Отправка данных...");
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            out.writeObject(toxicPollutionPerMonth);
            out.flush();

            System.out.println("Ожидание данных...");
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            toxicPollutionPerMonth = (ToxicPollutionPerMonth) in.readObject();

            System.out.println("Данные получены:");
            Map<String, Double> processedData = toxicPollutionPerMonth.getProcessedData();

            for (Map.Entry<String, Double> entry : processedData.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("Закрытие соединения");
            server.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
