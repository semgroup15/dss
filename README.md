# DSS

## Build

* Build with `ant build`
* Migrate the database with `ant run -Dcommand=migrate`
* Start input client with `ant run -Dcommand=input`
* Start output client with `ant run -Dcommand=output`

## Libraries

Each JAR library under `lib/` should be included in `.classpath` for the Eclipse project to work properly.
