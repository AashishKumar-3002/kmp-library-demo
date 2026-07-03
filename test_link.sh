#!/bin/bash
set -x
./gradlew :kmp-merchant-app:clean
./gradlew :kmp-merchant-app:embedAndSignAppleFrameworkForXcode
