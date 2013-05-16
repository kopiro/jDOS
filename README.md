# jDOS
# Java Dynamic Operation Server

### Cos'è
jDOS `e un server dinamico di operazioni.
Il suo punto di forza risiede nella possibilita` di aggiungere delle operazioni molto semplicemente grazie all’allocazione automatica delle classi in base al tipo di richiesta.
Per definire una nuova operazione, infatti, basta scrivere una nuova classe che estenda la classe base AutoOP e che implementi il suo metodo astratto run().

Il risultato dell’operazione `e semplicemente il valore di ritorno della funzione run, incapsulato nell’oggetto Result; esso può definire un override del metodo toString() per fornire una rappresentazione valida al client.
Per le operazioni piu` complesse, il cui risultato non è riconducibile ad una semplice stringa, ma ad una serie di operazioni, è possibile estendere la classe ManualOP ed implementare il metodo dispatch();
quest’ultimo deve occuparsi di scrivere opportunamente sulla socket.

Un esempio di ManualOP è il trasferimento di file.
Il suo valore di ritorno non è ovviamente una stringa, ma una serie di byte che devono essere frammentanti e inviati in più parti al client, onde evitare problemi di memoria.