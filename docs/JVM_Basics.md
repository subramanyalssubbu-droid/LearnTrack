# JVM basics

**JDK (Java Development Kit):** Tools for writing and building Java programs. It includes the `javac` compiler and the runtime needed to execute programs.

**JRE (Java Runtime Environment):** Components needed to run compiled Java programs, including the JVM and supporting libraries. Modern JDK distributions include these runtime components; a separate JRE install is not required for this project.

**JVM (Java Virtual Machine):** The engine that executes compiled Java bytecode on a computer. The JVM manages memory and translates/compiles bytecode into instructions the operating system and processor can use.

**Bytecode:** Platform-independent instructions stored in `.class` files after `javac` compiles a `.java` source file.

**Write once, run anywhere:** A Java program is compiled to bytecode instead of directly to instructions for one specific operating system. The same `.class` files can generally run on Windows, Linux or macOS when a compatible JVM and required libraries are available. This does not mean every Java program is automatically portable if it depends on platform-specific features.
