@echo off
echo Compilando Mini Tibia...
if not exist bin mkdir bin
javac src/*.java -d bin

if %ERRORLEVEL% EQU 0 (
    echo Compilacao bem-sucedida!
    echo Executando jogo...
    java -cp bin Game
) else (
    echo Erro na compilacao
    exit /b 1
)
pause
