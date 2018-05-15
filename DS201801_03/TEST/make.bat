@echo off

if "%1" == "" goto :all
if "%1" == "all" goto :all
if "%1" == "clean" goto :clean
goto :error

:all
echo javac Main.java
javac Main.java
echo java Main
java Main
goto :end

:clean
echo rm -f *.class y.out
del *.class y.out
goto :end

:error
echo make: *** No rule to make target `%1'.  Stop.

:end
