@echo off
set jarfile=c:\Daniel\programs\quote\quote.jar
set javapath=c:\a\jdk1.8\bin\java

%javapath% -cp %jarfile% com.ispnote.tools.quote.Process %*
