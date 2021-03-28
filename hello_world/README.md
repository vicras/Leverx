# Hello world project 
Simple program that provide opportunities to output in stdout information about system variables
# How to use:
### Linux users
To use this program:
- Download project archive
- Open in terminal root folder
- make make.sh executable:
    > chmod u+x make.sh

- run it: 
    > ./make.sh

### For others make similar actions:
1.  Create similar folder structure:

![Folder structure before make.sh run](https://github.com/vicras/Leverx/blob/master/hello_world/output/start_tree.png)

2. Compile project: 
    <code>
    javac -d ./bin -sourcepath ./src ./src/com/vicras/Main.java
    -d where to place generated class files
    -sourcepath where to find input source files (current if not defined)
    </code>

3. Run project: 
    > java -classpath ./bin com/vicras/Main
    > 
    > -classpath class search path of directories and zip/jar files

4. Generate javadoc: 
    > javadoc -d ./docs/javadoc -charset utf-8  -sourcepath ./src -subpackages com.vicras
    > 
    > -d where to place generated files
    > 
    > -charset define charset
    > 
    > -sourcepath where to find input source files (current if not defined)
    > 
    > -subpackages Generates documentation from source files in the specified packages

5. To run jar file: 
    > java -jar ./artifacts/my.jar
    > 
    > -jar executes a program encapsulated in a JAR file

# Output example
![Output example](https://github.com/vicras/Leverx/blob/master/hello_world/output/output.png)

# Folder structure
![Folder structure after make.sh run](https://github.com/vicras/Leverx/blob/master/hello_world/output/end_tree.png)
