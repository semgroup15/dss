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

* `ant run -Dcommand=gsmarena.quicksearch` Load initial device list
* `ant run -Dcommand=gsmarena.device` Load full data for existing devices


## Libraries

Each JAR library under `lib/` should be included in `.classpath` for the Eclipse project to work properly.
