# jRAT Remover
Removes any instance of jRAT on your machine running Windows, Mac OS X, Linux, FreeBSD, OpenBSD, Solaris (any system running an XDG compliant desktop environment)

## How to run
Download Remover.jar and double click it or execute it using java -jar Remover.jar

## How to build

Compile from source into an runnable JAR with [pyjar](https://github.com/redpois0n/pyjar)

Needs dependency [oslib](https://github.com/redpois0n/oslib)

Merge oslib/src and jrat-remover/src and put pyjar.py in the parent directory of the downloaded source and run

```
python pyjar.py [--jdk /path/to/jdk/bin/] --input src --output remover.jar --mainclass se.jrat.remover.Main
```

*--jdk argument is only needed if javac isn't in your __$PATH__*

Then you can either double click the JAR or run it using

```
java -jar remover.jar
```