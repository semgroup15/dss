# DSS

## Build

* Build with `ant build`

## Commands

### DB

* `ant run -Dcommand=db.init` Create tables


### GSMArena

* `ant run -Dcommand=ga.init` Initialize with minimal device list
* `ant run -Dcommand=ga.expand` Expand current database with details


### DSS

* `ant run -Dcommand=dss.input` Input client
* `ant run -Dcommand=dss.output` Output client


### Media

* `ant run -Dcommand=media.fetch` Fetch missing device images


## Libraries

Each JAR library under `lib/` should be included in `.classpath` for the Eclipse project to work properly.
