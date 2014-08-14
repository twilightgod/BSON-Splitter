# BSON Splitter

Split BSON(mongodump result) in many files of a fixed size.

# Dependencies

- Gradle

```
brew install gradle # for mac os
```

# Generating jar

Run command

```
gradle jar
```

Jar file will be in 'build/lib' folder

# Using jar

For split a file called 'backup.bson' in files of 100MB size run:

```
java jar bson_splitter.java backup.bson 100
```

Will generate files with name "splitted.number.bson", example: splitted.1.bson
