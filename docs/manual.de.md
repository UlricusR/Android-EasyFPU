# Benutzerhandbuch

## Installation

Holen Sie sich die App im Google Play Store:

[![Google Play Store](assets/images/googleplay.png){ style="width:200px", align=left, .off-glb }](https://play.google.com/store/apps/details?id=info.rueth.fpucalculator){:target="_blank"}

## Der Hauptbildschirm mit der Essensliste

![Der Hauptbildschirm mit der Essensliste](assets/images/MainActivity.png){style="width:80px", align=left }

Der Hauptbildschirm erscheint beim Öffnen der App und enthält eine Liste mit Essen. Diese ist beim allerersten Öffnen zunächst leer - nach einem Update wird die vorhandene Liste mit Essen übernommen.

In dieser App ist mit einem Essen eine einzelne Komponente einer Mahlzeit gemeint, die in sich „homogen“ ist. Mehrere Essen können dann zu einer Mahlzeit zusammengestellt werden. So bestünde z.B. die Mahlzeit Schnitzel mit Pommes und Ketchup aus drei Essen, nämlich dem Schnitzel, den Pommes und dem Ketchup.

Die Essensliste auf dem Hauptbildschirm ist alphabetisch sortiert. Es empfiehlt sich, jedem Essen einen eindeutigen Namen zu geben und nicht zweimal denselben Namen zu verwenden, auch wenn das möglich ist (es wird von der App nicht verhindert).

![Das Suchfeld](assets/images/MainActivity_search.png){style="width:80px", align=right }
![Die gefilterte Essensliste](assets/images/MainActivity_fav.png){style="width:80px", align=right }

Klickt man auf den kleinen Stern rechts über der Essensliste, werden nur die Essen angezeigt, die als Favoriten markiert wurden (entweder im Essensdialog oder über das Kontextmenü des Essens). Außerdem kann man über das Suchsymbol nach Essen suchen (beliebige Suche innerhalb der Essensnamen).

## Anlegen von Essen

![Neues Essen anlegen](assets/images/NewFoodActivity.png){style="width:80px", align=left }

Es öffnet sich ein Bildschirm zur Eingabe der notwendigen Daten:

- Name (Pflichtfeld): Der Name des Essens. 
- Favorit: Auswahl, ob das Essen in der Liste der Lieblingsessen angezeigt wird. 
- Kalorien pro 100g (Pflichtfeld): Die Kalorien des Essens pro 100g in kcal. 
- Kohlenhydrate pro 100g (Pflichtfeld): Die Kohlenhydrate des Essens pro 100g in Gramm. 

Außerdem können optional noch drei typische Mengen des Essens eingegeben werden, jeweils versehen mit einem Kommentar. Dieser wird später bei der Auswahl des Essens im Berechnungsdialog angezeigt. Die Eingabe der typischen Menge ist besonders bei vorher bekannten Mengen nützlich (z.B. abgepacktes Essen oder „4 Stück“ ChickenMcNuggets) und erleichtert die spätere Nutzung der App insbesondere für Kinder.

Nach dem Abspeichern erscheint das neu angelegte Essen in der Essensliste (alphabetisch sortiert) am Hauptbildschirm. 

## Berechnen einer Mahlzeit

![Neue Mahlzeit](assets/images/NewMealActivity.png){style="width:80px", align=right }

Eine Mahlzeit besteht aus mindestens einem Essen. Zum Berechnen der Mahlzeit wählt man also am Hauptbildschirm mindestens ein Essen aus der Essensliste aus und tippt dann den magentafarbenen Mahlzeit-Knopf mit dem Besteck-Symbol. Es erscheint ein Dialog zur Eingabe der Essensmengen.

Wurden beim Anlegen des Essens typische Mengen festgelegt, kann nun für jedes Essen die typische Menge gewählt oder immer auch eine eigene Mengenangabe gemacht werden. Die Menge sollte der tatsächlich gegessenen Menge des Essens in Gramm entsprechen.

Hat man die Mengen für alle gewählten Essen festgelegt, tippt man auf das Pfeil-Symbol, um die Berechnung zu starten.

![Ergebniswerte der Mahlzeit und aller einzelnen Essen](assets/images/CalcMealActivity.png){style="width:80px", align=left }

Im folgenden Bildschirm werden nun ganz oben die Ergebniswerte der Gesamtmahlzeit (gelb hinterlegt) gefolgt von denen der einzelnen Essen der Mahlzeit angezeigt. Diese sind:

- Die Menge in Gramm
- Die Kohlenhydrate in Gramm
- Die Fett-Protein-Einheiten (FPE)
- Die verlängerten Kohlenhydrate in Gramm - entsprechen den FPE multipliziert mit 10
- Die Absorptionszeit in Stunden

Bei einer Typ 1-Diabetes-Pumpentherapie können nun die FPE bzw. die verlängerten Kohlenhydrate (je nach Pumpe) als verzögerter Bolus über die angegebene Absorptionszeit eingegeben werden. So verhindert man das späte Ansteigen des Blutzuckers durch viele Fett-Protein-Einheiten (wie z.B. bei Pizza).

## Das Kontextmenü eines Essens

![Kontextmenü eines Essens](assets/images/FoodListContextMenu.png){style="width:80px", align=right }

Tippt man auf die drei vertikal angeordneten Punkte rechts neben einem Essen, bekommt man folgende Menüoptionen angezeigt:

- Zu den Favoriten hinzufügen / Aus den Favoriten entfernen: Fügt ein Essen zur Liste der Favoriten hinzu oder entfernt es
- Bearbeiten: Öffnet einen Dialog, in dem man das Essen bearbeiten kann - der Dialog entspricht dem Dialog zum Hinzufügen eines neuen Essens
- Löschen: Löscht das Essen aus der Liste

## Das Hauptmenü und die Einstellungen

![Das Hauptmenü](assets/images/MainMenu.png){style="width:80px", align=left }

Öffnet man das Hauptmenü mittels der drei vertikalen Punkte in der oberen rechten Ecke der App, hat man folgende Auswahlmöglichkeiten:

- Einstellungen: Öffnet die Einstellungen der App (siehe unten)
- Über: Infos über die App und Link zur dieser Dokumentation

![Die Einstellungen](assets/images/SettingsScreen.png){style="width:80px", align=right }

In den Einstellungen gibt es zwei Gruppen:

Gruppe "Absorptionsschema":

- Absorptionsschema bearbeiten: Öffnet den Dialog zum Bearbeiten des Absorptionsschemas
- Maximale FPE: Die maximale Anzahl an Fett-Protein-Einheiten, die im Absorptionsschema ausgewählt werden kann
- Maximale Absorptionszeit: Die maximale Absorptionszeit, die im Absorptionsschema ausgewählt werden kann

Gruppe "Datenbank":

- Als JSON-Datei exportieren: Speichert die Datenbank in lesbarer Form (JSON-Format) an einem vom Benutzer frei wählbaren Ort ab (empfohlen für Datenaustausch oder Backup)
- Von JSON-Datei importieren: Importiert die Datenbank aus einer JSON-Datei
- Rohdatenbank exportieren: Öffnet einen Dialog zum Exportieren der Datenbank im Rohformat (nicht empfohlen)

## Das Absorptionsschema

Das Absorptionsschema legt fest, welche Absorptionszeit bei einer gegebenen Anzahl an FPE ausgegeben wird. Das voreingestellte Absorptionsschema entspricht den heutigen Erkenntnissen der Ernährungswissenschaft und ist wie folgt angelegt:

- 1 FPE (= 10g verlängerte Kohlenhydrate): 3 Stunden Absorptionszeit
- 2 FPE (= 20g verlängerte Kohlenhydrate): 4 Stunden Absorptionszeit
- 3 FPE (= 30g verlängerte Kohlenhydrate): 5 Stunden Absorptionszeit
- 4 FPE (= 40g verlängerte Kohlenhydrate): 6 Stunden Absorptionszeit
- 6 FPE (= 60g verlängerte Kohlenhydrate) und mehr: 8 Stunden Absorptionszeit

![Absorptionsschema bearbeiten](assets/images/EditAbsorptionschemeActivity.png){style="width:80px", align=left }

Die Berechnungslogik der App geht wie folgt vor:

Schritt 1: Berechnung der Gesamtkalorien, z.B. bei 72g ChickenMcNuggets (249 kcal pro 100g): 72g * 249 kcal / 100g = 179 kcal

Schritt 2: Berechnung der Kalorien, die durch Kohlenhydrate verursacht werden (4 kcal pro 1 g Kohlenhydrate), z.B. bei 72g ChickenMcNuggets (17g Kohlenhydrate pro 100g): 72g * 17gKH / 100g * 4 kcal/gKH = 49 kcal

Schritt 3: Abzug der Kalorien aus Kohlenhydraten von den Gesamtkalorien; die sich ergebenden Kalorien stammen dann aus Fett und Proteinen, im obigen Beispiel: 179 kcal - 49 kcal = 130 kcal

Schritt 4: Da 100 kcal einer FPE entsprechen, ergeben sich im Beispiel 1,3 FPE bzw. 13,0 g verlängerte Kohlenhydrate.

Schritt 5: Die FPE werden gerundet, im Beispiel auf 1 FPE, und damit die Absorptionszeit nachgeschlagen, im Beispiel 3 Stunden.

Eine Änderung des Absorptionsschemas wird nur ernährungswissenschaftlich erfahrenen Personen empfohlen. Ein Zurücksetzen auf oben genanntes Standard-Schema ist jederzeit möglich.

## Beispieldaten

Sie können [diese JSON-Datei](assets/fpu_calculator_database.json) herunterladen und als Beispieldaten nutzen. Importieren Sie die Datei über den Menüpunkt "Datenbank von JSON-Datei importieren".

Ich empfehle jedoch dringend, Ihre eigenen Essen anzulegen, da auch die typischen Mengen von Person zu Person unterschiedlich sind.
