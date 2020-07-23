# **UNO Spiel in Java by Britta, Lena, Leo und Aiste a.k.a. Gruppe 4.**

Das altbekannte UNO Spiel programmiert in Java für
 maximal 4 menschliche Spieler oder 4 Bots mit implementierten
 Datenbank.  

# **_Strukturaufbau_:** 

zuerst haben wir alle _Anforderungen_ abgeklärt, es sind 
41 geworden. 

Dann die Karten implementiert und angefangen _Kartendecks_ zu erstellen:
 Ziehstapel, Ablagestapel, Spielerhandkarten. Da war es wichtig eine passende Datenstruktur
 zu wählen, wir haben uns für ArrayList für das gesamte Kartendeck entschieden,  
der Nachziehstapel ist eine LinkedList(Queue) da es gut ist zum Einfügen und Löschen,
 Ablagestapel ist ein Stack weil wir nur
 auf die letzte Karten zugreifen müssen (außer beim herausfordern des +4 Legers P#5)
 und Spielerhandkarten sind in einer LinkedList gespeichert. 
 
 4 Spielerliste in einer ArrayList erstellt und ein Zufallsgenerator "Random" eingebaut.
 
 Im ReadUserInput() Methode haben wir den eigentlichen Spielablauf für den menschlichen Spieler 
 implementiert: Benutzereingabe wird eingelesen, Hilfe Stellung angeboten, Korrekte Eingabe
  abgeglichen.  
 

# _**Probleme & Bugs:**_

1. Die passenden Datenstrukturen für Listen zu wählen. 
2. User input durch den Separator zu spliten. 
3. Zusammenfügen der verschiedenen Versionen (aktuellisierte).  
4. Arbeit aufteilen, Kolleginnen den Code und die Logik zu erklären. 
Sich für einen Strukturaufbau zu enstcheiden. Änderungen vornehmen und zusammenfügen. 
5. Bei +4 Herausforderung auf die vorletzte Karte im Stack zugreifen. 
6. Aktionskarte tritt mehrmals in Aktion --> setPlayedAlready() zu vermeiden, dass
einmal gelegte Aktionskarte mehrmals in Aktion tritt. 
7. Richtungswechsel hat Anfangs nur einmal Richtung gewechselt und dann zurückgesprungen.
 Mit Aufruf von changeDirection() haben wir Variable direction mit -1 multipliziert 
 um die Richtung zu verändern.
8. NextPlayer() mit +1 oder -1 implementiert. 
9. Als bei den Bots der Nachziestapel leer war, hat das Spiel nicht mehr ausgeteilt. 
10. Beim neuen Stapel machen mussten wir alle Karten(Aktionskarten) in ihrem Wert zurücksetzen. 
11. Plus2 auf Plus2 legen eine Rekursion implementieren. 
12. Beim "UNO" rufen das Array von 2 auf 3 Elemente erweitern müssen und damit es funktioniert, 
haben wir in der Exception "catch" Block ausführbaren Code eingebaut. 

# **Was wir jetzt anders machen würden**
Der Austausch mit Kolleginnen anderer Gruppen durch Onlinelehre hat massiv gefehlt. 
Wir würden uns gleich in Person treffen und Anforderungen priorisieren, das Projekt besser 
strukturieren, weil jetzt wissen wir was wichtig ist und was nicht. 


# **Highlights** 
* Daily Scrum
* Tatsächliches Spielen: das erste mal z.B. r-3 eingeben und sehen, dass es funktioniert. 
* Pair- & Groupprogramming Erkenntnisse.
* Gemeinsames Brainstorming.
* Product Owner Unterstützung.  

