package Task1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(27115);

        System.out.println("Сервер запущен");

        while (true) {
            System.out.println("Ожидание подключения клиента...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент соединен: " + clientSocket);

            // Создание объекта ClientHandler для нового подключения и запуск в отдельном потоке
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
        }
    }
}
