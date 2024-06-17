## Project:

    Aplikacja dla salonów samochodowych.
    Program polega na oglądaniu, dodawaniu i usuwaniu samochodów/offert z katalogu salonu.
    Bedę wykorzystać Spring Boot - framework, JPA, MySQL - baza danych (wszystko open-source). 
    W formacie REST API wizualizowane.

## Możliwości:

    * możliwość wprowadzenia, zmodyfikowania lub usunięcia parametrów w tabeli «producers»: nazwa producenta
    * możliwość wprowadzenia, zmodyfikowania lub usunięcia parametrów w tabeli «car models»: nazwa modelu, rodzaje nadwozia, nazwa producenta
    * możliwość wprowadzenia, zmodyfikowania lub usunięcia parametrów w tabeli «offers»: nazwa modelu, VIN, rok wydania, kolor samochodu, cena
    * możliwość zaimportowania wszystkich tabel z pliku JSON
    * możliwość wyeksportowania wszystkich tabel do pliku JSON


## Dla uruchomienia projektu trzeba:

    * skopijować nazwę pliku .env.examle na .env 
    * w pliku .env wprowadzić swoje dane (url bazy danych, username, password)
    * "?createDatabaseIfNotExist=true" musi znajdować się na końcu łącza, aby utworzyć bazę

## Uruchomienia projektu:
```bash
mvn install
```

```bash
mvn spring-boot:run
```

## [Otwórz link do aplikacji](http://127.0.0.1:8080)