# Sistem de agregare, prelucrare și distribuție a datelor

Proiectul va trata trei probleme esențiale:
* Achiziții de date, sub forma de serii de timp:
  * Surse compatibile: local (interfață seriala/usb), JSON/XML, OpenTSDB
  * Conversie inițială de tip
  * Soluție de stocare a datelor, scalabilă la dimensiuni mari de date
* Prelucrare de date:
  * Proiectare scheme de prelucrare pe baza de diagrame bloc funcționale (FFBD)
  * Implementare blocuri implicite de prelucrare (ex. blocuri statistice)
  * Implementarea blocurilor responsabile cu planificarea și programarea evenimentelor
  * Implementare blocuri de alertă evenimente
  * Vizualizarea datelor în timp real
* Distribuția datelor:
  * Interfațarea cu surse compatibile: local (interfață seriala/usb), JSON/XML, OpenTSDB
  * Conversie finala de tip
  * Vizualizare date
  * Monitorizare alarme
  
Se va realiza o platformă web care va implementa sistemul descris mai sus.
* Se va realiza un studiu de caz care va pune în evidenta facilitățile sistemului implementat.
