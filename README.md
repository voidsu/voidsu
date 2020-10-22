# Voidsu

![voidsu-logotype](https://repository-images.githubusercontent.com/301179117/f4549580-14cc-11eb-9c63-3319c14221b9)

## Overview
Voidsu is a digital certificates diagnostic tools.
It resolves and analyses X509 certificates from any remote address via the SSL/TLS protocols.

Build your certificate monitoring service using embedded Java application!

## Container usage
```
podman run --rm -it -p 8080:8080 registry.hub.docker.com/voidsu/voidsu:0.1.0

docker run --rm -it -p 8080:8080 registry.hub.docker.com/voidsu/voidsu:0.1.0
```

## API
### JSON lookup
#### Request
```
GET /lookup
```
```
curl -i -H "Accept: application/json" "http://127.0.0.1:8080/lookup?address=213.248.63.19&port=443&serverName=void.su"
```
#### Response
```
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/json
Content-Length: 154
Date: Thu, 22 Oct 2020 21:44:42 GMT

{
    "distinguishedName": "CN=void.su",
    "match": true,
    "notAfter": 1609660977,
    "notBefore": 1601884977,
    "remains": 72,
    "serverName": "void.su",
    "validity": true
}
```

### Prometheus lookup
#### Request
```
GET /lookup
```
```
curl -H "Accept: text/plain; version=0.0.4" "http://127.0.0.1:8080/lookup?address=213.248.63.19&port=443&serverName=void.su"
```
#### Response
```
not_after{server_name="void.su:443"} 1609660977 1603403377476
not_before{server_name="void.su:443"} 1601884977 1603403377476
remains{server_name="void.su:443"} 72 1603403377476
validity{server_name="void.su:443"} 1 1603403377476
match{server_name="void.su:443"} 1 1603403377476
```

## License
Apache License 2.0, see [LICENSE](https://github.com/voidsu/voidsu/blob/master/LICENSE).