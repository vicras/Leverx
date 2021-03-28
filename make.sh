#!/bin/bash

mkdir bin
mkdir artifacts
mkdir docs
mkdir docs/javadoc

echo compiling...
javac -d ./bin -sourcepath ./src ./src/com/vicras/Main.java

echo run from compiled classes...
java -classpath ./bin com/vicras/Main

echo generating javadoc...
javadoc -d ./docs/javadoc -charset utf-8  -sourcepath ./src -subpackages com.vicras

echo generating executable jar...
jar -cvmf ./src/META-INF/MANIFEST.MF ./artifacts/my.jar -C ./bin .

echo run from jar...
java -jar ./artifacts/my.jar 
