# psql-multi - PostgreSQL queries on multiple databases

[![Build Status](https://img.shields.io/travis/foxylion/psql-multi/master.svg?style=flat-square)](https://travis-ci.org/foxylion/psql-multi)
[![Release](https://img.shields.io/github/release/foxylion/psql-multi.svg?style=flat-square)](https://github.com/foxylion/psql-multi/releases)
![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat-square)

A simple tool to execute a psql query on multiple databases.

You can find the latest version [here](https://github.com/foxylion/psql-multi/releases).

## Usage

```
Usage: java -jar build/libs/psql-multi-all.jar [options]
  Options:
    --exclude, -e
       Exclude databases with the following regex pattern
    --force, -f
       Force to continue when executed query fails on a database
       Default: false
    --help
       Prints this help
       Default: true
    --host, -h
       Hostname including port
       Default: 127.0.0.1:5432
    --include, -i
       Include databases with the following regex pattern
    --pass, -p
       Password to authenticate
       Default: postgres
  * --query, -q
       Command which should be executed
    --results, -r
       When using a SELECT statement as the command results will be printed to
       console
       Default: false
    --user, -u
       User to authenticate
       Default: postgres
```

## Build

Simply use grade.

```
gradle build
```
