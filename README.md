# Тестовое задание для компании Релэкс
# Описание проекта
Данный проект представляет собой сервис для обслуживания фермы, взаимодействие происходит с помощью REST-запросов. Реализован на языке Java с использованием фреймворка <b>Spring Boot</b> и базы данных <b>PostgreSQL</b>. 
Данные, отправляемые и получаемые приложением, представлены в формате <b>JSON</b>.<br>

Выполнил: Нелезин Олег
# Стек технологий
<li>Java</li>
<li>Maven</li>
<li>Spring Boot</li>
<li>Spring Data JPA</li>
<li>JWT</li>
<li>Spring Security</li>
<li>PostgreSQL</li>
<li>Liquibase</li>
<li>Lombok</li>
<li>Spring Mail Server</li>
<li>Spring Validation</li>
<li>JSON</li>
<li>Docker</li>
<li>Redis</li>

## Запуск проекта
  1. Собрать jar файл
  2. В папке с проектом в теминале ввести команду "docker build -t farm-service ."
  3. В папке с проктом в терминале ввести команду "docker-compose up". 
По всем вопросам:astravsu@gmail.com или @scvrlvrd(telegram)
# Реализованные требования
## Администратор
  <ul>
    <li>Администратор может изменить свои данные для входа в систему</li>
    <li>Администратор может регистрировать новых работников</li>
    <li>Администратор может блокировать работников</li>
    <li>Администратор может просмотреть список всех работников</li>
    <li>Администратор может регистрировать новые товары</li>
    <li>Администратор может удалять товары</li>
    <li>Администратор может просматривать статистику по собранным товарам за конкретный день или месяц</li>
      <ul>
        <li>По конкретному работнику</li>
        <li>По ферме в целом</li>
      </ul>
    <li>Администратор получает на почту ежедневный отчет по собранным товарам за день</li>
    <li>Администратор может ставить оценки работникам за день</li>
    <li>Администратор может удалить оценку работника за день</li>
    <li>Администратор может задавать норму сбора товаров для конкретного работника</li>
    <li>Администратор может удалять норму сбора товаров для конкретного работника</li>
  </ul>

 ## Работник
   <ul>
     <li>Работник может собирать товары</li>
     <li>Работник может посмотреть свою оценку, поставленную администратором</li>
     <li>Работник может посмотреть свою норму сбора товаров, заданную администратором</li>
   </ul>
   
# Детали реализации
<b>Данные для входа администратором:</b>
```yaml
{
  "email": "relex@gmail.com",
  "password": "admin"
}
```
<li>Данные администратора создаются в базе данных при первом запуске программы(с помощью <b>Liquibase</b>)</li>
<li>Реализовано хеширование паролей</li>
<li>Реализована проверка на email формат</li>
<li>Информация о сессии хранится в Json Web Token и передается в HTTP хэдерах</li>
<li>Реализован обработчик ошибок. Ошибки обрабатываются ExceptionHandleController</li>
<li>В качестве базы данных была использована PostgreSQL</li>
<li>Библиотека Validation используюется для проверки корректности данных в запросах</li>
<li>Для документации использовался Javadoc</li>
<li>С помощью Redis приложение хранит токен после логаута и не дает зайти по нему в систему</li>

# ER-модель базы данных

![relexfinal drawio](https://github.com/olegnelezin/Farm-service/assets/77767886/2d0ec950-9054-4739-8098-9fb4a95afe77)


# Примеры REST-запросов
## Логин
```yaml
POST /auth/login

{
  "email": "relex@gmail.com",
  "password": "admin"
}
```
Ответ в случае успешного логина:
```yaml
{
  "accessToken": <token>,
  "refreshToken": <refreshToken>
}
```
## Выход из системы
```yaml
GET /auth/logout
Authorization: Bearer <token>
```
Ответ в случае успешного логина:
```yaml
{
  "message": "You have been successfully logout."
}
```
## Регистрация работника
```yaml
POST /admin/register/employee
Authorization: Bearer <token>
Required-role: ADMIN

{
  "firstName": "Oleg",
  "lastName": "Nelezin",
  "patronymic": "Yurievich",
  "email": "astravsu@gmail.com",
  "password": 123
}
```
Ответ в случае успешной регистрации:
```yaml
{
    "firstName": "Oleg",
    "lastName": "Nelezin",
    "patronymic": "Yurievich",
    "email": "astravsu@gmail.com"
}
```
## Удаление работника
```yaml
DELETE /admin/employee
Authorization: Bearer <token>
Required-role: ADMIN

{
  "email": "astravsu@gmail.com
}
```
Ответ в случае успешного удаления:
```yaml
{
  "message": "Employee has been deleted."
}
```
## Получение списка всех работников
```yaml
GET /admin/employee/all
Authorization: Bearer <token>
Required-role: ADMIN

```
Ответ в случае успеха:
```yaml
[
  {
    "firstName": "Oleg",
    "lastName": "Nelezin",
    "patronymic": "Yurievich",
    "email": "astravsu@gmail.com"
  },
  {
    "firstName": "Jordan",
    "lastName": "Belfort",
    "patronymic": "Relex",
    "email": "jordano@gmail.com"
  }
]
```
## Добавление нового типа товара
```yaml
POST /admin/register/product
Required-role: ADMIN

{
  "name": "apple",
  "unit": "kg"
}
```
Ответ в случае успешного добавления:
```yaml
{
  "message": "Product has been added."
}
```
## Удаление товара
```yaml
DELETE /admin/product
Authorization: Bearer <token>
Required-role: ADMIN

{
  "name": "apple"
}
```
Ответ в случае успешного удаления:
```yaml
{
  "message": "Product has been deleted."
}
```
## Просмотреть статистику по собранным товарам по работнику за конкретный день/месяц
```yaml
POST /admin/collected-products/employee
Authorization: Bearer <token>
Required-role: ADMIN

{
  "period": "day",
  "periodNumber": 11,
  "email": "astravsu@gmail.com"
}
```
<i>Примечание</i>: в графе "period" еще может быть значение "month", а в "periodNumber" - номер месяца, за который мы хотим получить статистику.<br>
Ответ в случае успеха:
```yaml
[
    {
        "email": "astravsu@gmail.com",
        "productName": "apple",
        "unit": "unit",
        "count": 15
    },
    {
        "email": "astravsu@gmail.com",
        "productName": "pineapple juice",
        "unit": "liter",
        "count": 11
    },
    {
        "email": "astravsu@gmail.com",
        "productName": "peas",
        "unit": "kg",
        "count": 20
    }
]
```
## Просмотреть статистику по собранным товарам по ферме в целом за конкретный день/месяц
```yaml
POST /admin/collected-products/farm
Authorization: Bearer <token>
Required-role: ADMIN

{
  "period": "month",
  "periodNumber": 10
}
```
<i>Примечание</i>: в графе "period" еще может быть значение "day", а в "periodNumber" - номер дня, за который мы хотим получить статистику.<br>
Ответ в случае успеха:
```yaml
[
    {
        "productName": "apple",
        "unit": "unit",
        "count": 54
    },
    {
        "productName": "cabbage",
        "unit": "unit",
        "count": 5
    },
    {
        "productName": "peas",
        "unit": "kg",
        "count": 20
    }
]
```
## Поставить оценку работнику
```yaml
POST /admin/mark
Authorization: Bearer <token>
Required-role: ADMIN

{
  "email": "astravsu@gmail.com",
  "mark": 5
}
```
Ответ в случае успеха:
```yaml
{
  "message": "Mark has been added."
}
```
## Удалить оценку работника
```yaml
DELETE /admin/mark
Authorization: Bearer <token>
Required-role: ADMIN

{
  "email": "astravsu@gmail.com",
}
```
Ответ в случае успеха:
```yaml
{
  "message": "Mark has been deleted."
}
```
## Задать норму собранных товаров работнику
```yaml
POST /admin/plan
Authorization: Bearer <token>
Required-role: ADMIN

{
  "email": "astravsu@gmail.com",
  "productName": "apple",
  "count": 20
}
```
Ответ в случае успеха:
```yaml
{
  "message": "Plan has been added."
}
```
## Удалить норму собранных товаров работника
```yaml
DELETE /admin/plan
Authorization: Bearer <token>
Required-role: ADMIN

{
  "email": "astravsu@gmail.com",
}
```
Ответ в случае успеха:
```yaml
{
  "message": "Plan has been deleted."
}
```
## Собрать товар
```yaml
POST /employee/collect-products
Authorization: Bearer <token>
Required-role: EMPLOYEE

{
  "productName": "apple",
  "count": 6
}
```
Возможные ответы в случае успеха:
```yaml
{
  "message": "Products have been collected. There is no plan for this product."
}
```
```yaml
{
  "message": "Products have been collected. You already collected all planed apple."
}
```
```yaml
{
  "message": "Products have been collected. Remained apple products: 14"
}
```
## Узнать оценку за день
```yaml
GET /employee/mark
Authorization: Bearer <token>
Required-role: EMPLOYEE
```
Ответ в случае успеха:
```yaml
{
  "mark": 5
}
```
## Узнать норму собранных товаров
```yaml
GET /employee/plan
Authorization: Bearer <token>
Required-role: EMPLOYEE
```
Ответ в случае успеха:
```yaml
  [
    {
        "productName": "apple",
        "unit": "unit",
        "count": 20
    },
    {
        "productName": "peas",
        "unit": "kg",
        "count": 30
    }
]
```
# Пример отправленной статистики фермы за день на почту

![image](https://github.com/olegnelezin/Farm-service/assets/77767886/aa0a7b87-d4d4-40c7-93b0-3eb5b47b9ac0)

# Демонстрация работы приложения
[GOOGLE ДИСК](https://drive.google.com/drive/u/0/folders/15hpPfYTSNa-vZ9DuPvE5AfyOc6pbLEvB)
## Контакты
<b>Почта</b>: astravsu@gmail.com<br>
<b>Telegram</b>: @scvrlvrd
