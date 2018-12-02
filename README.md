# IncomeExpensesAccounting

- Michael Frech
- Julian Nobis

## Inbetriebnahme des JEE-Servers

### DB starten

```
cd db/
/opt/derbydb/bin/startNetworkServer -noSecurityManager
```

### Wildfly konfigurieren

- In der Toolbar auf "Add Configuration..." klicken
- JBoss-server local hinzufügen
- Darauf achten, dass der wildfly server mit der ip `localhost:8085` konfiguriert ist

### JEE-Server starten bzw. stoppen

- Starten: in der Toolbar auf "Run" klicken
- Stoppen: in der Toolbar auf "Stop" klicken


## Inbetriebnahme des Webservers

- Webserver projekt in der Webstorm IDE öffnen

### Nginx-server starten

- Docker starten

```
docker-compose up
```

### Seite im Browser öffnen

- Im Browser `localhost:80` eingeben

### Nginx-server stoppen

- Docker herunterfahren 

```
docker-compose down
```
