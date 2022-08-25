#!/usr/bin/env bash
./gradlew clean
./gradlew bootJar
docker build -t virtuex/miyu-kms-manager:v1.0.0 .
docker push virtuex/miyu-kms-manager:v1.0.0
