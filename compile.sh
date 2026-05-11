#!/bin/bash

echo "Compilando Mini Tibia..."
mkdir -p bin
javac src/*.java -d bin

if [ $? -eq 0 ]; then
    echo "✓ Compilação bem-sucedida!"
    echo "Executando jogo..."
    java -cp bin Game
else
    echo "✗ Erro na compilação"
    exit 1
fi
