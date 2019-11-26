[![Build Status](https://travis-ci.com/drunkmowgli/InterviewEvotor.svg?branch=master)](https://travis-ci.com/drunkmowgli/InterviewEvotor)

[![codecov](https://codecov.io/gh/drunkmowgli/InterviewEvotor/branch/master/graph/badge.svg)](https://codecov.io/gh/drunkmowgli/InterviewEvotor)

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


# Сборка:

    mvn clean install
    
    P.S. jar является исполняемым

# Запуск:

    1. Запускаем docker container postgresql:
        docker run --name postgres -p 5432:5432 -e POSTGRES_USER=prod_user -e POSTGRES_PASSWORD=prod_password -d postgres
        
    2. Запускаем приложение: 
        ./evotor-0.0.1-SNAPSHOT.jar
        
        P.S. Приложение запустится на порту 8888
        
# Входные данные для тестирования:

    1.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"create", "login":"testLogin", "password":"testLogin" }' -H "Content-Type: application/json"
        response: {"result":0}
        re-request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"create", "login":"testLogin", "password":"testLogin" }' -H "Content-Type: application/json"
        response: {"result":1}
    
    2.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"get-balance", "login":"testLogin", "password":"testLogin" }' -H "Content-Type: application/json"
        response: {"result":0,"extras":{"balance":0.0}}
        
    3.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"get-balance", "login":"testLogin123", "password":"testLogin" }' -H "Content-Type: application/json"
        response: {"result":3}
        
    4.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"get-balance", "login":"testLogin", "password":"testLogin123" }' -H "Content-Type: application/json"
        response: {"result":4}
        
    5.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"get-123", "login":"testLogin", "password":"testLogin" }' -H "Content-Type: application/json"
        response: {"result":2}
        
    6.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{"type":"create123", "login":"testLogin", "password":"testLogin" }' -H "Content-Type: application/json"
        response: {"result":2}
        
    7.  request: curl -X POST "http://localhost:8888/api/v1/accounts" -d '{ }' -H "Content-Type: application/json" 
        response: {"result":2}
    
