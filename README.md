# BSON Splitter

Split BSON(mongodump result) in many files of a fixed size.

# Last jar

[BSON Splitter Jar 0.1.0](https://github.com/alangalvino/BSON-Splitter/raw/develop/bson_splitter.jar)


# Using jar

## Running jar

For split a file called 'backup.bson' in files of 100MB size run:

```
java -jar bson_splitter.java backup.bson 100
```

## Example

For a backup.bson with 300MB, after run:

```
java -jar bson_splitter.java backup.bson 100
```

The result should be:

```
$ ls -la

backup.bson        300MB
splitted.1.bson    100MB
splitted.2.bson    100MB
splitted.3.bson    100MB
```

Will generate files with name "splitted.number.bson", example: splitted.1.bson

# Generating jar

## Dependencies

- Gradle

```
brew install gradle # for mac os
```

## Run command for generating jar

```
gradle jar
```

Jar file will be in 'build/lib' folder
