# API для ресторана (система заказов)

## Общие сведения
Простое REST API, реализованное согласно [условию](https://edu.hse.ru/mod/forum/discuss.php?d=44397). Представляет собой систему обработки заказов с клиентской и сервеной частью.

## Стэк и SETUP
Приложение написано с использованием Spring Boot 3. В качестве базы данных используется Postgres. Для запуска приложения можно развернуть ее самостоятельно или запустить 
docker-compose.yaml файл. Для системы контроля версий БД используется Liquebase. Для реализации аутентификации используется Spring Security. Взаимодействие с вебом работает через
Spring Web. Для работы с БД используется Spring JPA.

## Документация
[Здесь](https://www.postman.com/avionics-engineer-38114273/workspace/sodtware-design/documentation/33123391-095b3c52-3f1c-49e9-b148-51dc5a7412e3?entity=folder-06dbf72e-8593-4fca-95bf-c388903107b1) 
представлена коллекция запросов к API на Postman. Она польностью документирована.

## Несколько пояснений
- Для многопоточной обработки заказов используется ExecutorService и CachedThreadPool. Система приоритетов при обработке заказов (или дополнений заказов) - FIFO.
- В качестве шаблонов проектирования используется Builder для созданния DTO и сущностей.
- Для обработки исключений используется @RestCobtrollerAdvice.
- При возникновении некорректных запросов и других ошибок, причина сообщается пользователю в теле ответа.
- Приложение не привязано к текущему дню, потому дата создания заказа может быть и до, и после настоящего времени. Сделано это, в частности, для удобства тестирования функционала отбора заказов по периоду.
