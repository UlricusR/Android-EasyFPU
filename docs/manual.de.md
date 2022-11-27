# Benutzerhandbuch

## Installieren

Die App ist im Apple App Store verfügbar:

[![Apple App Store](assets/images/appstore.png){ .off-glb }](https://apps.apple.com/de/app/easyfpu/id1529949520){:target="_blank"}

## Der Hauptbildschirm mit der Essensliste

![Die Essensliste](assets/images/01_FoodList.png){style="width:100px", align=right }

Der Hauptbildschirm erscheint beim Öffnen der App und enthält eine Liste mit Essen. Diese ist beim allerersten Öffnen zunächst leer - nach einem Update wird die vorhandene Liste mit Essen übernommen.

In dieser App ist mit einem Essen eine einzelne Komponente einer Mahlzeit gemeint, die in sich „homogen“ ist. Mehrere Essen können dann zu einer Mahlzeit zusammengestellt werden. So bestünde z.B. die Mahlzeit Schnitzel mit Pommes und Ketchup aus drei Essen, nämlich dem Schnitzel, den Pommes und dem Ketchup.

Die Essensliste auf dem Hauptbildschirm ist alphabetisch sortiert. Es empfiehlt sich, jedem Essen einen eindeutigen Namen zu geben und nicht zweimal denselben Namen zu verwenden, auch wenn das möglich ist (es wird von der App nicht verhindert).

![Die gefilterte Essensliste](assets/images/02_FoodList-filtered.png){style="width:100px", align=left }
![Die Suche in der Essensliste](assets/images/02_FoodList-search.png){style="width:100px", align=left }

Klickt man auf den kleinen Stern rechts über der Essensliste, werden nur die Essen angezeigt, die als Favoriten markiert wurden. Außerdem kann man über das Suchfeld nach Essen suchen (beliebige Suche innerhalb der Essensnamen).

Langes Drücken eines Essens öffnet den Dialog zur Bearbeitung des Essens.

Durch Wischen nach links kann ein Essen aus der Liste gelöscht werden.

## Anlegen von Essen

Zum Anlegen von neuem Essen tippt man auf das große, grüne Plus-Symbol in der oberen rechten Ecke. Es gibt drei Möglichkeiten um Anlegen von neuem Essen.

### Option 1: Manueller Eintrag

![Neues Essen anlegen / Essen bearbeiten](assets/images/10_FoodEdit.png){style="width:100px", align=right }

Es öffnet sich ein Dialog zur Eingabe der notwendigen Daten:

- Name (Pflichtfeld): Der Name des Essens
- Lieblingsessen: Auswahl, ob das Essen in der Liste der Lieblingsessen angezeigt wird
- Kalorien pro 100g (Pflichtfeld): Die Kalorien des Essens pro 100g in kcal
- Kohlenhydrate pro 100g (Pflichtfeld): Die Kohlenhydrate des Essens pro 100g in Gramm
- Davon Zucker pro 100g (Pflichtfeld): Die Menge an Kohlenhydraten aus Zucker pro 100g als Teilmenge der Kohlenhydrate

Außerdem können optional noch beliebig viele typische Mengen des Essens hinzugefügt werden, jeweils versehen mit einem Kommentar. Man bestätigt die Eingabe durch Antippen des kleinen grünen Plus-Symbols.

Die typischen Mengen werden später bei der Auswahl des Essens im Berechnungsdialog angezeigt. Die Eingabe der typischen Menge ist besonders bei vorher bekannten Mengen nützlich (z.B. abgepacktes Essen oder „4 Stück“ ChickenMcNuggets) und erleichtert die spätere Nutzung der App insbesondere für Kinder.

Nach dem Abspeichern erscheint das neu angelegte Essen in der Essensliste (alphabetisch sortiert) am Hauptbildschirm.

### Option 2: Suche in der Lebensmitteldatenbank

![Die Ergebnisliste der Suche](assets/images/03_FoodSearch-results.png){style="width:100px", align=left }
![Die Detailansicht der Suche / des Scans](assets/images/03_FoodSearch-details.png){style="width:100px", align=left }

Seit Version 2.0.0 kann ein Essen in einer Lebensmitteldatenbank gesucht werden. Geben Sie dazu den Suchbegriff in das Namensfeld ein und tippen Sie anschließend auf das Such-Symbol.

Derzeit ist nur eine Lebensmitteldatenbank angebunden, nämlich [OpenFoodFacts](https://world.openfoodfacts.org/){:target=blank}. OpenFoodFacts ist eine offene Lebensmitteldatenbank, d.h. jeder kann beitragen. Das bedeutet, dass dort auch falsche Nährwerte eingetragen sein können. Überprüfen Sie daher bitte immer, ob die in der Datenbank gefundenen Nährwerte auch den tatsächlichen Nährwerten entsprechen. Nutzen Sie hierzu das Detailanzeigefenster, wo Sie auch Produktbilder finden, in die Sie hineinzoomen können.

### Option 3: Scannen des Barcodes

Tippen Sie zum Scannen eines Barcodes auf das Scan-Symbol neben dem Such-Symbol und richten Sie dann die Kamera Ihres Geräts auf den Barcode des Produkts. Wird das Produkt in der Datenbank gefunden, wird Ihnen das Detailanzeigefenster angezeigt, wo Sie entscheiden können, ob das Produkt übernommen wird oder nicht.

Sollte kein Scan möglich sein, wischen Sie das Kamerafenster einfach von oben nach unten weg.

## Zusammenstellen einer Mahlzeit

### Auswahl von Essen

![Gegessene Menge eingeben oder auswählen](assets/images/03_FoodSelect.png){style="width:100px", align=right }

Eine Mahlzeit besteht aus mindestens einem Essen. Zum Berechnen der Mahlzeit wählt man also aus der Essensliste zunächst ein Essen aus. Es erscheint ein Dialog zur Eingabe der Essensmenge.

Wurden beim Anlegen des Essens typische Mengen festgelegt, kann nun eine typische Menge gewählt oder immer auch eine eigene Mengenangabe gemacht werden. Alternativ stehen grüne Knöpfe zur Verfügung, die ihren abgebildeten Wert zur bereits eingestellten Menge addieren. Die hier eingegegebene Menge sollte der tatsächlich gegessenen Menge des Essens in Gramm entsprechen.

Außerdem besteht die Möglichkeit, die aktuell eingegebene Menge als neue typische Menge abzuspeichern. Dazu tippt man auf "Zu typischen Mengen hinzufügen", gibt man einen Kommentar ein und bestätigt mit dem grünen Plus-Symbol. Die Pflege oder Änderung von typischen Mengen geschieht im Dialog zum Bearbeiten von Essen.

Diesen Vorgang wiederholt man optional für weitere Essensbestandteile seiner Mahlzeit.

### Die Zusammenfassung der Mahlzeit

![Die Zusammenfassung der Kohlenhydrate aus Zucker](assets/images/04_MealSummary.png){style="width:100px", align=left }

Haben Sie mindestens ein Essen ausgewählt, erscheint ein Schwebefenster. Wenn Sie dieses ganz öffnen durch "Hochwischen", werden Ihnen die drei Arten von Kohlenhydraten - Zucker, reguläre und verlängerte Kohlenhydrate - als Zusammenfassung angezeigt:

- Rotes Zuckerwürfelsymbol: Kohlenhydrate aus Zucker werden in der Regel am schnellsten absorbiert. Sie können die entsprechenden Parameter in den Einstellungen anpassen.
- Grünes Hasensymbol: Reguläre Kohlenhydrate werden langsamer als Zucker absorbiert. Auch diese Parameter können Sie in den Einstellungen anpassen.
- Blaues Schildkrötensymbol: Verlängerte Kohlenhydrate (im Englischen auch bekannt unter e-Carbs oder Fake Carbs) stammen nicht aus Kohlenhydraten, sondern aus Fett und Proteinen. Daher werden sie viel später und viel länger absorbiert.

Sie können an dieser Stelle auch den Spritz-Ess-Abstand einstellen.

Durch Antippen des roten Kreuzes in der Zusammenfassung der Mahlzeit können Sie die Mahlzeit zurücksetzen, d.h. alle Essensbestandteile sowie der "Mahlzeit beginnt in x Minuten"-Parameter werden auf Null gesetzt.

### Die Details der Mahlzeit

![Die Details des Essens inkl. Zusatzinformation](assets/images/08_MealDetails_full.png){style="width:100px", align=right }

Tippen Sie auf das Pfeil-Icon in der Zusammenfassung, öffnet sich die Ansicht mit den Details der Mahlzeit. Hier können Sie nochmal die Bestandteile Ihrer Mahlzeit anzeigen lassen.

## Export nach Apple Health

![Die Vorschau des Exports nach Apple Health](assets/images/09_HealthExport.png){style="width:100px", align=left }

Durch Tippen des Export-Knopfes (ein Rechteck mit Pfeil nach oben) können Sie die berechneten Kohlenhydrate, aber auch die Kalorien der Mahlzeit nach Apple Health exportieren.

Durch An- oder Abwählen der Schalter wählen Sie aus, welche Daten exportiert werden.

Außerdem können Sie den "Mahlzeit beginnt in x-Minuten"-Parameter einstellen, was in der Regel der Spritz-Ess-Abstand ist.

Für die Kohlenhydrate wird eine Vorschau erstellt, die zeigt, zu welchem Zeitpunkt welcher Kohlenhydratanteil welcher Art exportiert werden wird. In rot werden die Kohlenhydrate aus Zucker dargestellt, in grün die regulären Kohlenhydrate und in blau die verlängerten Kohlenhydrate.

In wievielen kleinen Teilmengen die jeweiligen Kohlenhydrate über die Zeitspanne der Absorptionszeit verteilt werden, regelt man mit den jeweiligen Intervall-Parametern in den Einstellungen.

!!! warning "Wichtige Hinweise für Looper"

    Dieses Feature kann sehr nützlich für Typ 1-Diabetiker sein, die ihre Insulintherapie über Loop steuern. Loop liest, wenn Sie dies in den Einstellungen von Apple Health erlauben, die von EasyFPE exportierten Kohlenhydratdaten und passt den Insulinbedarf entsprechend an. Geben Sie die Mahlzeit auf keinen Fall erneut in Loop ein, denn das könnte zu doppelten Einträgen und einer Unterzuckerung führen.

    Wenn Sie die Kohlenhydrate einer Mahlzeit jedoch versehentlich doppelt exportieren, kann das zur Unterzuckerung oder sogar schweren Unterzuckerung führen.

    Zwei Sicherheitsmechanismen versuchen das zu verhindern:

    - Bevor Daten nach Apple Health exportiert werden, überprüft EasyFPE den Zeitpunkt des letzten Datenexports. Liegt dieser innerhalb einer Zeitspanne x, wird eine Warnung angezeigt und Sie müssen den Export explizit ein zweites Mal bestätigen. Die Zeitspanne x kann in den Einstellungen konfiguriert werden. Voreinstellung ist 15 Minuten.
    - Als zweite Bestätigung vor dem Export werden Sie gebeten, sich zu authentifizieren - abhängig von den iOS-Einstellungen per FaceID, TouchID oder Code.

## Eigene Rezepte verwalten

Seit Version 2.0.0 ist es möglich, eigene Rezepte in EasyFPE zu verwalten. Das soll hier anhand eines Beispiels gezeigt werden - wir backen gemeinsam einen Marmorkuchen.

### Schritt 1: Zutaten anlegen

![Die Liste der Zutaten](assets/images/06_IngredientList.png){style="width:100px", align=right }

Um ein Rezept abbilden zu können, benötigt man zunächst all seine Zutaten in der Zutatenliste. Die Zutatenliste befindet sich im Reiter "Zutaten" rechts neben den Produkten. Zutaten können mittels Kontextmenü (langes Drücken) zwischen der Produkt- und der Zutatenliste verschoben werden.

Das Anlegen von Zutaten funktioniert identisch wie das [Anlegen von Produkten](#anlegen-von-essen) - per manueller Eingabe, per Suche in einer Essensdatenbank oder per Scan eines Barcodes. Der einzige Unterschied besteht in der Auswahl der Kategorie "Zutat" statt "Produkt".

### Schritt 2: Zutaten eines Rezepts auswählen

![Das "weggewischte" Schwebefenster](assets/images/07_ComposedProduct-min.png){style="width:100px", align=left }
![Das Schwebefenster mit der Zusammenfassung des Rezepts](assets/images/07_ComposedProduct-half.png){style="width:100px", align=left }

Genauso wie auf der Produktliste wählt man nun alle Zutaten eines Rezepts per Antippen aus. Im Gegensatz zum Zusammenstellen einer Mahlzeit erscheint nun aber nicht das Schwebefenster mit der Zusammenfassung der Mahlzeit, sondern ein Schwebefenster mit der Zusammenfassung des Rezepts. Dies kann man zunächst einmal nach unten "wegwischen", so dass die gesamte Zutatenliste gut zugänglich ist.

Gibt man eine Menge falsch ein, tippt man einfach ein zweites Mal auf die ausgewählte Zutat, wodurch diese aus dem Rezept entfernt wird. Anschließend fügt man dieselbe Zutat mit korrigierter Menge wieder hinzu.

### Schritt 3: Name, Gesamtgewicht und Anzahl der Portionen festlegen

![Eingabe eines Namens, des Gesamtgewichts und der Anzahl der Portionen](assets/images/07_ComposedProduct-full.png){style="width:100px", align=right }

Für den nächsten wichtigen Schritt öffnet man das Schwebefenster komplett mit einem Wisch von unten nach oben. Folgende Daten sind einzugeben:

- Der Name des Rezepts, z.B. "Marmorkuchen mit Schokoglasur"
- Das Gesamtgewicht des fertigen Produkts - die ist wichtig für die korrekte Berechnung der Nährwerte pro 100g
- Falls die automatische Erstellung von typischen Mengen ausgewählt ist, muss noch die Anzahl der Portionen eingegeben werden, die aus dem fertigen Produkt (möglichst gleichmäßig) geschnitten wird

### Schritt 4: Speichern des Produkts

![Abweichende Mengen müssen bestätigt werden](assets/images/07_ComposedProduct-confirmweight.png){style="width:100px", align=left }

Zum Speichern des fertigen Produkts tippt man auf "Speichern". Damit wird das Produkt - genauso wie fertige Produkte auch - in der Liste der Produkte abgelegt. Sollte das Gesamtgewicht des zusammengestellten Produkts von der Summe seiner Zutaten abweichen, muss dies noch bestätigt werden.

### Schritt 5: Bearbeiten eines Rezepts

![Das Kontextmenü eines Produkts](assets/images/08_Product-context.png){style="width:100px", align=right }

Will man ein Rezept bearbeiten, so wählt man es in der Produktliste mittels langem Tippen aus (Kontextmenü). Will man es anpassen, ohne das Originalrezept zu verändern, muss man vorher "Duplizieren" wählen, ansonsten wird das Originalrezept überschrieben.

## Das Haupt- und das Kontextmenü

### Das Hauptmenü

Öffnet man das Hauptmenü mittels der drei vertikalen Striche in der oberen linken Ecke der App, hat man folgende Auswahlmöglichkeiten:

![Das Hauptmenü](assets/images/11_Menu.png){style="width:100px", align=left }

- Einstellungen: Öffnet den Dialog zum Bearbeiten der Absorptionsschemata und weiterer Einstellungen
- Importieren (Format: JSON): Importiert die Datenbank aus einer JSON-Datei - Sie können im Anschluss wählen, ob Sie die bestehende Essensliste ersetzen oder ergänzen wollen
- Exportieren (Format: JSON): Export der Essensliste zwecks Backup oder Austausch mit anderen
- Über: Infos über die App
- Haftungsausschluss: Zeigt den Haftungsausschluss an
- Hilfe im Web: Der Link zu dieser Dokumentation

Zum Schließen des Hauptmenüs tippt man auf das Kreuz-Symbol oder wischt man das Menü nach links weg.

### Das Kontextmenü

![Das Kontextmenü eines Produkts](assets/images/08_Product-context.png){style="width:100px", align=right }

Das Kontextmenü jedes Produkts oder jeder Zutat öffnet sich durch langes Drücken. Folgende Aktionen stehen zur Verfügung:

- Bearbeiten: Öffnet den Dialog zum Bearbeiten eines Produkts oder Rezepts
- Duplizieren: Dupliziert das Produkt oder die Zutat - es wird der gleiche Name verwendet, aber "- Kopie" angehängt
- Teilen: Hiermit können Sie das Produkt oder die Zutat via AirDrop, iMessage, WhatsApp, Mail, etc. mit anderen EasyFPE-Nutzern teilen
- Zu Zutaten / Produkten verschieben: Verschiebt ein Produkt in die Zutatenliste bzw. umgekehrt
- Löschen: Löscht das Produkt bzw. die Zutat

## Das Absorptionsschema und weitere Einstellungen

![Absorptionsschemata und Einstellungen bearbeiten](assets/images/12_Settings.png){style="width:100px", align=left }

In den Einstellungen können Sie die Absorptionsschemata für Kohlenhydrate aus Zucker, reguläre Kohlenhydrate und verlängerte Kohlenhydrate bearbeiten.

Jedes dieser Absorptionsschemata hat drei Parameter:

- Verzögerung: Die Zeit, die Ihr Körper benötigt, um die jeweiligen Kohlenhydrate zu verdauen, d.h. nach dieser Zeit wirken diese Kohlenhydrate auf Ihren Blutzucker.
- Absorptionszeit: Die Dauer, in der diese Kohlenhydrate auf Ihren Blutzucker wirken, nachdem sie (nach der Verzögerung) begonnen haben (dieser Parameter kann nur für Zucker und reguläre Kohlenhydrate eingegeben werden, für verlängerte Kohlenhydrate wird er berechnet).
- Intervall: Dieser Parameter wird lediglich für den Export der Kohlenhydrate nach Apple Health benötigt. Die Gesamtmenge der jeweiligen Kohlenhydrate wird für den Export gleichmäßig in diesem Abstand über die Absorptionszeit verteilt. Beispiel: Bei einer 3-stündigen (=180min) Absorptionszeit und einem 10-Minuten-Intervall wird eine Kohlenhydratmenge von 36g auf 18 Teile zu je 2g verteilt: 36g / (180min / 10min).

### Absorptionsschema für Kohlenhydrate aus Zucker

Sie können auswählen, ob Kohlenhydrate aus Zucker getrennt von regulären Kohlenhydraten ausgewiesen werden sollen. Sollten Sie dies nicht nutzen, werden Kohlenhydrate aus Zucker wie reguläre Kohlenhydrate behandelt.

Kohlenhydrate aus Zucker werden in der Regel relativ schnell absorbert. Die Voreinstellung ist 2 Stunden mit keiner Verzögerung (sofortige Wirkung).

### Absorptionsschema für reguläre Kohlenhydrate

Sollten Sie die separate Behandlung von Kohlenhydraten aus Zucker ausgewählt haben, werden diese von den regulären Kohlenhydraten abgezogen.

Reguläre Kohlenhydrate werden in der Regel langsamer absorbiert als die aus Zucker. Die Voreinstellung ist 3 Stunden mit einer Verzögerung von 5 Minuten.

### Absorptionsschema für verlängerte Kohlenhydrate

Verlängerte Kohlenhydrate sind eigentlich gar keine Kohlenhydrate, sondern Fett-Protein-Einheiten (FPE), sie wirken jedoch sehr ähnlich auf den Blutzucker. Da sie aus Fett und Proteinen stammen, die zunächst vom Körper verdaut werden müssen, beginnt ihre Wirkung eher spät (Voreinstellung ist 90 Minuten).

Das Absorptionsschema legt fest, welche Absorptionszeit bei einer gegebenen Anzahl an FPE ausgegeben wird. Das voreingestellte Absorptionsschema entspricht den heutigen Erkenntnissen der Ernährungswissenschaft und ist wie folgt angelegt:

- 1 FPE (= 10g verlängerte Kohlenhydrate): 3 Stunden Absorptionszeit
- 2 FPE (= 20g verlängerte Kohlenhydrate): 4 Stunden Absorptionszeit
- 3 FPE (= 30g verlängerte Kohlenhydrate): 5 Stunden Absorptionszeit
- 4 FPE (= 40g verlängerte Kohlenhydrate): 6 Stunden Absorptionszeit
- 6 FPE (= 60g verlängerte Kohlenhydrate) und mehr: 8 Stunden Absorptionszeit

Dieses Absorptionsschema kann auch bearbeitet werden.

Da jeder Körper anders auf Fett-Protein-Einheiten reagiert, ist es wichtig, dass Sie ihren eigenen Kohlenhydrate-pro-FPE-Faktor herausfinden und hier einstellen. Voreinstellung ist 10g Kohlenhydrate pro FPE, für Kinder rate ich jedoch zu deutlich niedrigeren Faktoren.

Die Berechnungslogik der App geht wie folgt vor:

Schritt 1: Berechnung der Gesamtkalorien, z.B. bei 72g ChickenMcNuggets (249 kcal pro 100g): 72g * 249 kcal / 100g = 179 kcal

Schritt 2: Berechnung der Kalorien, die durch Kohlenhydrate verursacht werden (4 kcal pro 1 g Kohlenhydrate), z.B. bei 72g ChickenMcNuggets (17g Kohlenhydrate pro 100g): 72g * 17gKH / 100g * 4 kcal/gKH = 49 kcal

Schritt 3: Abzug der Kalorien aus Kohlenhydraten von den Gesamtkalorien; die sich ergebenden Kalorien stammen dann aus Fett und Proteinen, im obigen Beispiel: 179 kcal - 49 kcal = 130 kcal

Schritt 4: Da 100 kcal einer FPE entsprechen, ergeben sich im Beispiel 1,3 FPE, was bei einem Kohlenhydrate-pro-FPE-Faktor von 10 dann 13,0 g verlängerter Kohlenhydrate entsprechen würde.

Schritt 5: Die FPE werden gerundet, im Beispiel auf 1 FPE, und damit die Absorptionszeit nachgeschlagen, im Beispiel 3 Stunden.

Eine Änderung des Absorptionsschemas wird nur ernährungswissenschaftlich erfahrenen Personen empfohlen. Alle Absorptionsdaten können auf die Voreinstellungen zurückgesetzt werden.

### Hinweisdauer zwischen Exporten nach Apple Health

Um zu vermeiden, dass Kohlenhydrate aus Mahlzeiten versehentlich mehrfach hintereinander nach Apple Health exportiert werden und damit ggf. die Insulinmenge Ihres Loop-Systems erhöhen, wird Sie EasyFPE darauf hinweisen, wenn Sie innerhalb einer gegebenen Zeitspanne nach dem letzten Export einen erneuten Export vornehmen. Diese Zeitspanne kann hier eingestellt werden. Voreinstellung ist 15 Minuten.

## Beispieldaten

[Diese JSON-Datei](assets/fpu_calculator_database.json) können Sie herunterladen und als Beispieldaten nutzen. Importieren Sie die Datei über den Menüpunkt "JSON-Import".

Ich empfehle jedoch dringend, Ihre eigenen Essen anzulegen, da auch die typischen Mengen von Person zu Person unterschiedlich sind.
