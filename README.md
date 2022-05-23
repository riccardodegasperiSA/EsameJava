Il progetto contiene sia il server TCP che HTTP

Eseguendo il main in StartServers si avviano sia il server TCP che il server HTTP

Per il server TCP (127.0.0.1 1234) eseguire il main nella classe App
da console:

nc 127.0.0.1 1234
comandi...

Per il server HTTP (127.0.0.1:8000) eseguire il main nella classe HTTPApp

127.0.0.1:800/?=comandi...

oppure da console

curl -X POST "http://127.0.0.1:8000" -d "=comandi..."
