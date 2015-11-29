# psql-execute

Simple tool to execute a query on multiple databases.

## Usage

```
Usage: java -jar build/libs/psql-execute-all.jar [options]
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
       Default: postgre
```

## Build

Simply use grade.

```
gradle build
```