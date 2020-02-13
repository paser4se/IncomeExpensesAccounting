# IncomeExpensesAccounting - IEA <img src="images/Logo.png" width="40" height="40">

## :bookmark_tabs: Übersicht
- [Was ist IEA?](#money_with_wings-was-ist-iea)
- [Anwendungsfalldiagramm](#chart_with_upwards_trend-anwendungsfalldiagramm)
- [Systemarchitektur](#statue_of_liberty-systemarchitektur)
- [Projektstrukturplan](#scroll-projektstrukturplan)
- [Funktionsweise](#currency_exchange-funktionsweise)
  - [Erklärung](#newspaper-erklärung)
- [Konfiguration](#wrench-konfiguration)
  - [JEE- und Webserver dockern](#floppy_disk-jee--und-webserver-dockern)
- [Technologien](#microscope-technologien)
- [Das Team](#surfer-das-team)
- [Lizenz](#closed_lock_with_key-lizenz)

## :money_with_wings: Was ist IEA?
IncomeExpensesAccounting (IEA) ist ein Einnahmen- & Ausgabenprogramm, welches einen Überblick über die gesamten Finanzen schafft.

## :chart_with_upwards_trend: Anwendungsfalldiagramm
![alt text](images/IEA_use_case_diagram.png)

## :statue_of_liberty: Systemarchitektur
![alt text](images/systemarchitektur.png)
Zusatzinformationen: Die Applikation wurde gedockert; es wird eine postgresql Datenbank verwendet und Jakarta EE wurde auf Quarkus umgestellt.

## :currency_exchange: Funktionsweise
![alt text](webserver/wwwnew/src/assets/Homescreen.png)

### :newspaper: Erklärung
Die Zahlungen werden von der Bank als csv-File zur Verfügung gestellt und müssen auf Buchungen aufgeteilt werden. Dabei werden zwei Fälle unterschieden: 
Fall A: Eine Zahlung kann automatisiert (ev. auch manuell) auf ein Konto gebucht werden (sachliche Abgrenzung), sowie dem richtigen Jahr zugeordnet werden.  
Fall B: Manche Zahlungen betreffen aber nicht nur das Jahr der Zahlung, sondern werden über mehrere Jahre hinweg abgeschrieben: PCs im Wert von 30.000 EUR werden bspw. drei Jahre genutzt und für diesen Zeitraum abgeschrieben, d.h. es entsteht ein Aufwand von 10.000 EUR pro Jahr. 
Damit eine solche Abschreibung möglich ist gibt es Kontenarten wie Aufwands- und Ertragskonten. In die Aufwandskonten werden die negativen Beträge gebucht, die positiven Beträge werden ins Ertragskonto gebucht.
Bestandskonten werden wir in weiterer Folge auch noch brauchen um die Anschaffungskosten der Investitionsgüter zu verbuchen, diese sind jedoch momentan noch unwichtig.

## :wrench: Konfiguration

### :floppy_disk: JEE- und Webserver dockern
- Dem Script "startup.sh" Rechte erteilen
```
chmod +x startup.sh
```
- Das Script ausführen
```
./startup.sh
```
- Website unter [http://localhost:8081/iea](http://localhost:8081/iea) aufrufen


## :microscope: Technologien
- Jakarta EE
- Docker
- Vuejs
- HTML5
- CSS
- JavaScript

## :surfer: Das Team
- [Michael Frech](https://github.com/michifrech)
- [Julian Nobis](https://github.com/juliannobis)

## :closed_lock_with_key: Lizenz

[MIT License](LICENSE)

Copyright (c) 2019 IEA

Hiermit wird unentgeltlich jeder Person, die eine Kopie der Software und der zugehörigen Dokumentationen (die "Software") erhält, die Erlaubnis erteilt, sie uneingeschränkt zu nutzen, inklusive und ohne Ausnahme mit dem Recht, sie zu verwenden, zu kopieren, zu verändern, zusammenzufügen, zu veröffentlichen, zu verbreiten, zu unterlizenzieren und/oder zu verkaufen, und Personen, denen diese Software überlassen wird, diese Rechte zu verschaffen, unter den folgenden Bedingungen:

Der obige Urheberrechtsvermerk und dieser Erlaubnisvermerk sind in allen Kopien oder Teilkopien der Software beizulegen.

DIE SOFTWARE WIRD OHNE JEDE AUSDRÜCKLICHE ODER IMPLIZIERTE GARANTIE BEREITGESTELLT, EINSCHLIEẞLICH DER GARANTIE ZUR BENUTZUNG FÜR DEN VORGESEHENEN ODER EINEM BESTIMMTEN ZWECK SOWIE JEGLICHER RECHTSVERLETZUNG, JEDOCH NICHT DARAUF BESCHRÄNKT. IN KEINEM FALL SIND DIE AUTOREN ODER COPYRIGHTINHABER FÜR JEGLICHEN SCHADEN ODER SONSTIGE ANSPRÜCHE HAFTBAR ZU MACHEN, OB INFOLGE DER ERFÜLLUNG EINES VERTRAGES, EINES DELIKTES ODER ANDERS IM ZUSAMMENHANG MIT DER SOFTWARE ODER SONSTIGER VERWENDUNG DER SOFTWARE ENTSTANDEN.
