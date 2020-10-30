# Voidsu

![voidsu-logotype](https://repository-images.githubusercontent.com/301179117/f4549580-14cc-11eb-9c63-3319c14221b9)

<p align="center">
<a href="https://discord.gg/33bAHde"><img src="https://img.shields.io/static/v1?logo=discord&label=&message=Discord&color=36393f&style=flat-square" alt="Discord"></a>
<a href="https://github.com/voidsu/voidsu/releases/latest"><img src="https://img.shields.io/github/release/voidsu/voidsu.svg?style=flat-square" alt="Releases"></a>
<a href="https://hub.docker.com/r/voidsu/voidsu"><img src="https://img.shields.io/static/v1?label=Docker&message=Hub&color=2496ED&logo=docker&logoColor=white&style=flat-square" alt="DockerHub"></a>
<a href="https://github.com/voidsu/voidsu/blob/master/LICENSE"><img src="https://img.shields.io/github/license/voidsu/voidsu.svg?style=flat-square" alt="License"></a>
</p>

## Overview
Voidsu is a digital certificates diagnostic tools.
It resolves and analyses X509 certificates from any remote address via the SSL/TLS protocols.

Build your certificate monitoring service using embedded Java application!

## Container usage
```shell script
podman run --rm -it -p 8080:8080 registry.hub.docker.com/voidsu/voidsu:0.2.0

docker run --rm -it -p 8080:8080 registry.hub.docker.com/voidsu/voidsu:0.2.0
```

## API
### JSON lookup
#### Request
```
GET /lookup
```
```shell script
curl -i -H "Accept: application/json" "http://127.0.0.1:8080/lookup?address=213.248.63.19&port=443&serverName=void.su"
```
#### Response
```http request
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/json;charset=UTF-8
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
```shell script
curl -H "Accept: text/plain;version=0.0.4" "http://127.0.0.1:8080/lookup?address=213.248.63.19&port=443&serverName=void.su"
```
#### Response
```
not_after{server_name="void.su:443"} 1609660977 1603403377476
not_before{server_name="void.su:443"} 1601884977 1603403377476
remains{server_name="void.su:443"} 72 1603403377476
validity{server_name="void.su:443"} 1 1603403377476
match{server_name="void.su:443"} 1 1603403377476
```
## Thanks
![Java Profiler, .NET Profiler, Continuous Performance Monitoring | YourKit](https://www.yourkit.com/images/yklogo.png)

YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).

## License
Apache License 2.0, see [LICENSE](https://github.com/voidsu/voidsu/blob/master/LICENSE).
