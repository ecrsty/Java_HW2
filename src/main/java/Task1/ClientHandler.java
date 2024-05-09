package Task1;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.net.Socket;
import java.util.OptionalInt;
import java.util.Map;
import java.util.List;

public class ClientHandler implements Runnable{
    Socket clientSocket;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    ObjectInputStream in;
    ObjectOutputStream out;

    @Override
    public void run(){
        try {
            System.out.println("Запуск потока обработки данных...");

            // получаем потоки ввода и вывода
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            ToxicPollutionPerMonth inputData = (ToxicPollutionPerMonth) in.readObject();

            System.out.println("Получены данные. Начало обработки...");
            ToxicPollutionPerMonth processedData = processData(inputData);

            Thread.sleep(3000);

            System.out.println("Отправка данных клиенту...");
            out.writeObject(processedData);
            out.flush();
            System.out.println("Закрытие потока...");
            out.close();

        }catch (IOException | InterruptedException | ClassNotFoundException e){
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
                System.out.println("Клиент "+clientSocket+" отсоединен");
            } catch (IOException e) {
                System.err.println("Ошибка закрытия потока: " + e.getMessage());
            }
        }
    }

    // метод обработки полученных данных
    private ToxicPollutionPerMonth processData(ToxicPollutionPerMonth toxicPollutionPerMonth) {
        Map<LocalDate, List<Integer>> data = toxicPollutionPerMonth.getToxicPollutionPerMonth();

        Map<String, Double> processedData = new HashMap<>();

        // максимальное количество выбросов за день
        OptionalInt maxPerDay = data.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(Integer::intValue)
                .max();
        processedData.put("Максимальное количество выбросов за день", (double) maxPerDay.orElse(0));

        // найти количество выбросов вредных веществ в городе за месяц: найти среднее за месяц
        double averagePerMonth = data.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        processedData.put("Среднее количество выбросов за месяц", averagePerMonth);

        toxicPollutionPerMonth.setProcessedData(processedData);

        return toxicPollutionPerMonth;
    }
}
