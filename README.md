# Projekt systemu realizującego operacje na bazie danych Northwind
Informatyka (niestcjonarne), **Bazy Danych 2020**<br/>
*AGH Wydział Informatyki, Elektroniki i Telekomunikacji*
## Skład zespołu    
- Mateusz Gałka    
Nazwa użytkownika na github: lambdaforg
- Dawid Karaś      
Nazwa użytkownika na github: dawkaras
- Kacper Kondratek          
Nazwa użytkownika na github: kKondratek
- Krzysztof Wicher           
Nazwa użytkownika na github: krwicher
## Dokumentacja
Plik zawierający dokumentację znajduje się w **folderze docs** i nazywa się **dokumentacja.pdf**
## Technologie
* Java Spring Boot
* MongoDB
## Baza danych MongoDB
W projekcie została wykorzystana baza danych MongoDB, jest ona niezbędna do poprawnego działania aplikacji. 
Należy pobrać i zainstalować serwer MongoDb. W celu prostszego korzystania zainstalować również środowisko graficzne (https://www.mongodb.com/try/download/community)
Dodać ścieżkę folderu bin serwera do zmiennej środowiskowej PATH.
Następnie w wierszu poleceń otwartym z uprawnieniami administratora wykonać polecenie, które ustawi ścieżkę dla plików baz danych oraz ścieżkę do przechowywania plików logów.
```
mongod --dbpath C:\MongoData\ --logpath C:\MongoLogs\mongolog.log
```
Serwer nie powinien wymagać ręcznego uruchamiania i w 100% działać w tle. W celu uruchomienia go ręcznie należy wykonać polecenie: 
```
net start MongoDB
```
## Projekt aplikacji
Aplikacja została stworzona na podstawie wygenerowanego podstawowego projektu ze strony https://start.spring.io/. Wykorzystane zostały:
* Java w wersji 11, 
* narzędzie Maven,
* Spring Boot 2.3.6,
* Spring Data for MongoDB - do obsługi bazy danych
* Spring Web - do tworzenia architekture MVC
* Thymeleaf - do obsługi HTML
