#!/bin/bash

# 운영체제 확인
OS=$(uname)
printf $OS "\n"

if [ "$OS" == "Darwin" ]; then
    # 맥인 경우
    ~/h2/bin/h2.sh
elif [[ "$OS" == "MINGW"* ]] || [[ "$OS" == "CYGWIN"* ]]; then
    # 윈도우인 경우 (Git Bash나 Cygwin을 사용하는 경우)
    cd "C:\\Program Files (x86)\\H2\\bin\\" && ./h2.bat
else
    echo "지원하지 않는 운영체제입니다."
fi
