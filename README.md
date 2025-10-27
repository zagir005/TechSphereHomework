# 🪩 TechSphere Homework

<div align="center"> 

**[Github Project](https://github.com/users/zagir005/projects/2) / [Макет итогового проекта](https://www.figma.com/design/yVwWM66nw4siaEgA5kgjsZ/Auth-Homework?node-id=2648-3654&t=x9ABKyCIjZIEuRuS-1) / [APK](https://github.com/zagir005/TechSphereHomework/releases/download/0.3/app-debug.apk)**

</div>
Домашнее задание выполненное в рамках отбора на стажировку в компанию "Техносфера". Ниже дам краткое описание стеку и фичам проекта. 

## ✨ Фичи, которые успел
- **Авторизация** - разделение пользователей на Admin/Client, хранение токена авторизации с помощью Data Store
- **Clean Arch / MVI Kotlin** - в проекте стараюсь следовать чистой архитектуре (Data, Domain, Presentation слои), presentation слой строю согласно MVI.

## 😬 Что не успел
- **Почти все (добавление компьютеров, тарифов, сессий, экспорт и импорт данных)** - Планировал сделать Base CRUD для всех экранов, для Computer и User модулей частично успел.

## 🛠️ Стек

- **🎨 Jetpack Compose** - минус xml.
- **📱 Decompose** - библиотека для навигации.
- **📱 MVI Kotlin** - MVI фреймворк.
- **🛜 Retrofit, OkHTTP** - библиотека для запросов в API.

## 🛠️ Как запустить проект?

### 📋 Требования

- **Gradle:** 8.13  
- **Kotlin:** 2.2.20  
- **minSdk:** 28  
- **targetSdk:** 36
- **JVM Target:** 11

---

### 🚀 Установка и запуск

1. **Клонировать репозиторий:**
   ```bash
   git clone https://github.com/zagir005/AuthHomework.git
   ```
2. **Открыть проект в Android Studio.**
3. **По желанию, в View -> Tool Windows -> BuildVariants у модуля App выбрать Release вариант, тогда приложение будет подгружать новости из API.**
3. **Запустить приложение.**

**Как авторизоваться в приложении?** По дефолту для админа есть пользователь Логин_Админа, для клиента Логин_Клиента, у обоих пароль - Пароль123

## ⏯️ Видео-демонстрация
<div align="center">

[gameplay01.webm](https://github.com/user-attachments/assets/cdeb8c16-ae12-4e97-8d56-8c9ecb1ff5a3)

</div>

<div align="center">

**Made by zagir_lek**

</div>

<div align="center">
  
**tg: [@zagir_lek](https://t.me/zagir_lek)**

</div>
