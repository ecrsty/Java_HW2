Для серверной части приложения были реализованы следующие классы: Server, ToxicPollutionPerMonth, ClientHandler.
Класс Server содержит метод запуска и соединения сервера с клиентом. Как только устанавливается соединение с клиентом, открывается новый поток для ожидания соединения со следующим клиентом.
Класс ToxicPollutionPerMonth содержит в себе 2 словаря: первый для основных данных, состоящий из значения даты и количества вредных выбросов за день, второй для статистических данных, которые будут занесены после обработки сервером, состоит из строкового описания и полученного значения.
ClientHandler отвечает за получение данных от клиента и их последующую обработку с помощью метода  processData(). В процессе обработки из объекта класса ToxicPollutionPerMonth извлекается словарь с основными данными, обрабатывается с помощью методов StreamAPI и полученные данные заносятся в словарь для статистических данных. Далее полученный после обработки методом объект отправляется пользователю и соединение закрывается.
Класс Main является точкой входа в программу, в нем осуществляется инициализация и запуск сервера.

Для клинтской части приложения был создан класс Client, содержащий методы generateData() и main(). Первый метод осуществляет генерацию случайных значений для заполнения словаря с основными данными в классе ToxicPollutionPerMonth. Второй метод – точка входа в программу, в нем устанавливается соединение с сервером, запускается метод генерации данных и происходит отправка экземпляра класса ToxicPollutionPerMonth на сервер. Затем принимаются обработанные данные и значения, записанные со стороны сервера в словарь статистических данных выводятся в консоль.
