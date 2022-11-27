# Was ist EasyFPE?

![EasyFPE-App-Icon](assets/images/pizza_small.png){ align=left }

EasyFPE ist eine iOS-App - hauptsächlich für Typ 1-Diabetiker - zum einfachen Berechnen von Kohlenhydraten aus Zucker, regulären Kohlenhydraten und Fett-Protein-Einheiten (FPE) bzw. verzögerten Kohlenhydraten (auch bekannt als e-Carbs oder Fake Carbs) - synchronisiert zwischen allen Ihren iOS-Geräten via iCloud (optional).

## Haftungsausschluss

!!! warning "Warnung"

    Ich muss und will vorab darauf hinweisen, dass die Nutzung dieser App auf eigenes Risiko erfolgt.

Die von der App errechneten Fett-Protein-Einheiten und Absorptionszeiten orientieren sich an den Empfehlungen z.B. der Deutschen Diabeteshilfe (siehe Links), wobei mit Absicht die vereinfachte Berechnungslogik angewandt wird: Statt die Fettmenge eines Essens mit 9 kcal/g und die Proteinmenge mit 4 kcal/g zu berechnen, werden von der Gesamtkalorienmenge die Kalorien aus Kohlenhydraten (4 kcal/g) abgezogen. Die Differenz ergibt dann die Kalorienmenge aus Fett und Proteinen. Die detaillierten Berechnungsschritte sind im Kapitel über das Absorptionsschema beschrieben.

Vorteil dieser Methode: Der Nutzer muss nur die Kalorien und die Kohlenhydrate pro 100g Essen eingeben und nicht zusätzlich noch die spezifischen Fett- und Proteinmengen.

!!! warning "Warnung"

    Wie Sie mit dieser Berechnung dann umgehen, ist Ihr eigenes Risiko. Obwohl ich den Rechenalgorithmus gründlich getestet habe und ihn selbst im Rahmen einer Insulinpumpentherapie in meiner Familie einsetze, lehne ich jede Garantie für dessen Korrektheit ab.

Ich will hier explizit die [Deutsche Diabeteshilfe](https://www.diabetesde.org/ueber_diabetes/was_ist_diabetes_/diabetes_lexikon/fett-protein-einheit-fpe){:target="_blank"} zitieren, die schreibt:

!!! quote "Deutsche Diabeteshilfe"

    Egal ob Pumpe oder Pen – wenn Sie nach Absprache mit Ihrem Diabetologen die ersten Versuche mit Insulingaben für Fett-Eiweiß-Einheiten machen, sollten Sie Ihren Blutzuckerspiegel sehr gut im Auge behalten.

Ein paar persönliche Erfahrungswerte zum Schluss:

- Wenn Sie loopen, ist es von Vorteil, wenn der Loop weiß, dass weitere Kohlenhydrate über die nächste Zeit zu erwarten sind. Er wird dann entweder die Menge oder die Anzahl der Autobolus-Gaben erhöhen.
- Wenn Ihre Auto-Bolus-Einstellungen aus Sicherheitsgründen eine höhere oder häufigere Bolusgabe verhindern, werden Sie eine sehr hohe Insulinmenge als manuelle Abgabe vorgeschlagen bekommen (z.B. 1,5 IE bei einem Blutzucker von 180). Reduzieren Sie diese Menge am Anfang aus Sicherheitsgründen auf die Hälfte.
- Und behalten Sie bei Erstanwendung bitte stets Ihren Blutzuckerverlauf im Blick.

## Motivation und Konzept

Die Motivation zum Programmieren von EasyFPE war, insbesondere Kindern mit Typ 1-Diabetes die Berechnung von Fett-Protein-Einheiten zu erleichtern.

Das Errechnen von Kohlenhydraten erfolgt quasi nebenbei, ist jedoch bei vielen Typ 1-Diabetikern schon in Fleisch und Blut übergegangen und kann mit vielen anderen Apps auch erledigt werden. Mahlzeiten mit einem hohen Fett- oder Proteinanteil führen jedoch sehr oft zu einem späten Blutzuckeranstieg. Diesem kann in der heutigen Pumpentherapie mit einem verzögerten Bolus entgegengewirkt werden. Allerdings ist die Berechnung der abzugebenden Einheiten und der Abgabedauer nicht trivial. Genau dies erledigt diese App.

Das Konzept hierfür ist absichtlich sehr einfach: Der Nutzer wählt ein oder mehrere Essen aus einer Liste aus und gibt deren Menge ein - wahlweise auch eine von beliebig vielen vordefinierten typischen Mengen. Damit berechnet die App die FPE.

Als Nutzer (bzw. als Eltern von Kindern mit Typ 1-Diabetes) muss man allerdings vorab etwas Zeit in das Anlegen von Essen investieren. Die Essensliste ist mit Absicht bei Erstinstallation komplett leer, da jeder sein eigenes typisches Essen hat ([Beispieldaten](manual.md#beispieldaten) können heruntergeladen werden). Es kann sich allerdings darauf beschränkt werden, nur Essen anzulegen, welches eben einen hohen Fett- oder Proteinanteil hat, z.B. Pizza, Bratwurst, McDonalds-Essen, usw.

EasyFPE gibt es auch für Android, wobei die iOS-Variante die neuere ist mit deutlich mehr Features, da ich sie selbst bei mir in der Familie im Rahmen einer Insulintherapie nutze. Ob ich Android dereinst wieder auf den gleichen Stand bringen werde, kann ich nicht versprechen.

## Datenschutzerklärung

!!! note "Hinweis"

    Dieser Abschnitt beinhaltet die Datenschutzerklärung der iOS-App. Die Datenschutzerklärung der Webseite finden [an dieser Stelle](https://ulricusr.github.io/de/legal/).

Die iOS-App EasyFPE speichert keinerlei persönliche Informationen und nutzt auch keinen Drittanbieter-Service, der dies tut.

Die Essensliste wird auf Ihrem Smartphone gespeichert und über iCloud mit Ihren anderen Geräten synchronisiert, sofern Sie in Ihrem persönlichen iCloud-Account eingeloggt sind. Diese Liste enthält keinerlei persönliche Informationen.

## Probleme und Wünsche

[Diese Probleme und Wünsche](https://github.com/UlricusR/iOS-EasyFPU/issues){:target="_blank"} sind mir bekannt und werden in künftigen Versionen angegangen. Sollten Sie über neue Probleme stolpern, die mir noch nicht bekannt sind, oder Wünsche und Anregungen zur Verbesserung der App haben, können Sie unter gleichem Link ein Issue öffnen.

Da ich EasyFPE in meiner Freizeit entwickle, bitte ich um Ihr Verständnis, dass ich keine detaillierte Releaseplanung vorweisen kann.

## Unterstützung

Sollten Sie die App mitentwickeln wollen, herzlich gerne - es ist alles Open Source und auf [GitHub](https://github.com/UlricusR/iOS-EasyFPU){:target="_blank"} verfügbar.

Ansonsten freue ich mich natürlich über jeden spendierten Kaffee...

[![Buy me a coffee](assets/images/buymeacoffee_darkbackground.png#only-dark){ .off-glb }](https://www.buymeacoffee.com/ulricus){:target="_blank"}
[![Buy me a coffee](assets/images/buymeacoffee_lightbackground.png#only-light){ .off-glb }](https://www.buymeacoffee.com/ulricus){:target="_blank"}
