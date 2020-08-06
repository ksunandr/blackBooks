# blackBooks
Реализовать веб приложение с REST API: магазин книг. Есть сущности книга и автор. У книги атрибуты:  название, год издания, авторы, кол-во У автора атрибуты: фамилия, имя, отчество, книги написанные автором. Связь между авторам и книгой многие ко многим. Книги и авторы хранятся в реляционной БД. Должен быть выставлен rest api: 1) Получение списка книг по фильтру с пагинацией и сортировкой Атрибуты фильтра: название книги, год издания: левая граница диапазона год издания: правая граница диапазона, фамилия автора, статус (отсутствует: кол-во книг = 0/ в наличии: кол-во > =1 ) Фильтр опциональный: в rest запросе может отсутствовать любой атрибут фильтра и,  соответственно, в запросе к БД условие должно быть исключено. 2) Добавление книги. 3) Редактирование книги. 4) Продажа книги: просто уменьшение атрибута кол-ва у книги на 1.  Не забыть про конкурентность:)  При попытке продать книгу с нулевым кол-вом выдается ошибка.  Приложение может быть реализовано как в виде Java EE  приложения (сервер любой) так и на стеке Spring. Использование ORM обязательно. Авторизация не нужна. Любая реляционная БД. Фронт не нужен.
