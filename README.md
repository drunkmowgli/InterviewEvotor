# Задание:

Написать WEB-сервис, который слушает определенный URL,
и принимает на него JSON-запросы, отправленные в теле POST-запроса.
Надо учитывать, что WEB-сервис может быть развернут в кластере на нескольких машинах,
балансировку трафика на которые обеспечат администраторы через NGINX.
Есть два вида сообщений:

    1) Регистрация нового клиента:
        {"type":"create", "login":"12345", "password":"pwd123"}
       При этом сервер:
        1. проверяет, что клиент с номером 123456 еще не зарегистрирован
        2. регистрирует этого клиента в системе с нулевым балансом.
       Ответное сообщение:
        {"result":0}
       Возможные коды ошибок:
        0 — все ОК.
        1 — пользователь с таким логином уже существует
        2 — техническая ошибка
        
    2) Запрос клиентом своего баланса
        {"type":"get-balance", "login":"12345", "password":"pwd123"}
       Сервер сверяет логин и пароль, и если они верны - возвращает баланс агента:
        {"result":0, "extras":{"balance":123.45}}
       Возможные коды ошибок:
        0 — все ОК.
        2 — техническая ошибка
        3 — пользователь не существует
        4 — пароль не верен
        
Срок реализации — неделя.
По всем вопросам можно обращаться на y.volokitin@evotor.ru