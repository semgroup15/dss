# DSS

## Build

* Build with `ant build`

## Commands

### DB

* `ant run -Dcommand=db.migrate` Migrate


### DSS

* `ant run -Dcommand=dss.input` Input client
* `ant run -Dcommand=dss.output` Output client


### GSMArena

* `ant run -Dcommand=interop.init` Initialize with minimal device list
* `ant run -Dcommand=interop.expand` Expand current database with details


## Libraries

Each JAR library under `lib/` should be included in `.classpath` for the Eclipse project to work properly.
