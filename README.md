[![Build Status](https://travis-ci.org/alangalvino/BSON-Splitter.png)](https://api.travis-ci.org/alangalvino/BSON-Splitter)
[![GitHub version](https://badge.fury.io/gh/alangalvino%2FBSON-Splitter.svg)](http://badge.fury.io/gh/alangalvino%2FBSON-Splitter)

# BSON Splitter

Split BSON(mongodump result) in many files of a fixed size.

# Lastest jar

[BSON Splitter Jar 0.1.3](https://github.com/alangalvino/BSON-Splitter/raw/develop/bson_splitter.jar)


# Using jar

## Running jar

To split a file called 'backup.bson', in files of 100MB size run:

```
java -jar bson_splitter.java backup.bson 100
```

Will generate files(<= 100MB) named "splitted.number.bson", example: splitted.1.bson

## Specifing output path and file name

You can change the output path and file name by the following command:

```
java -jar bson_splitter.java backup.bson 100 my/output/path my-file-name
```

Will generate files(<= 100MB) named "my-file-name.number.bson", example: my-file-name.1.bson. In folder 'my/output/path'.

## Example

For a backup.bson with 300MB, after running:

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
