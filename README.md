# SnapHS-API

Pokémon Snap High Score API

![GitHub](https://img.shields.io/github/license/reizuseharu/SnapHS-API)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/reizuseharu/SnapHS-API)
![GitHub top language](https://img.shields.io/github/languages/top/reizuseharu/SnapHS-API)

![GitHub issues](https://img.shields.io/github/issues-raw/reizuseharu/SnapHS-API)
![GitHub pull requests](https://img.shields.io/github/issues-pr-raw/reizuseharu/SnapHS-API)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=reizuseharu_SnapHS-API&metric=alert_status)](https://sonarcloud.io/dashboard?id=reizuseharu_SnapHS-API)

---

## Setup

- Run `docker-compose up -d` to kick off database
- Build with Gradle by running `gradle clean build`
- Run Spring App using IDE or `java -jar build/libs/snaphs-api-0.0.1-pre-alpha.jar`

## Launch Locally

```bash
./ngrok http -region=us -hostname=hs-pkmnsnap.ngrok.io 8089 > /dev/null & 
```
