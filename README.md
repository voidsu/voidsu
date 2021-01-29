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

## Container

### Docker
```shell script
docker run --rm -it -p 8080:8080 registry.hub.docker.com/voidsu/voidsu:0.3.0
```

### Podman
```shell script
podman run --rm -it -p 8080:8080 registry.hub.docker.com/voidsu/voidsu:0.3.0
```

```shell script
cd /tmp && \
curl -o voidsu.yml https://raw.githubusercontent.com/voidsu/container/master/voidsu.yml && \
mkdir voidsu && \
podman play kube voidsu.yml
```

## Build

```shell script
./gradlew[.bat] quarkusBuild
```

## API
### JSON lookup
#### Request
```
POST /lookup
```

#### Data
```
{
    "address": "void.su",
    "port": "443",
    "domain_name": "void.su"
}
```

#### curl
```shell script
curl -i -H "Content-Type: application/json" -H "X-Request-ID: 5fb3612904ba9a3339a33a90a8e92133" -d '{"address":"void.su","port":"443","domain_name":"void.su"}' -X POST http://127.0.0.1:8080/lookup
```

#### Response
```http request
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 148

{
    "distinguished_name": "CN=void.su",
    "match": true,
    "not_after": 1609660977,
    "not_before": 1601884977,
    "remains": 72,
    "server_name": "void.su",
    "validity": true
}
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
