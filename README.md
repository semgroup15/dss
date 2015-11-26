# DSS

## Build

* Build with `ant build`


## Commands

`ant run -Dcommand=<command>`


### DB

* `db.init` Create tables


### GSMArena

* `ga.init` Initialize with minimal device list
* `ga.expand` Expand current database with details


### Media

* `media.fetch` Fetch missing device images


### Randomizer

* `rand.reviews` Randomize reviews
* `rand.prices` Randomize prices


### UI

* `ui` Main UI


## Libraries

Each JAR library under `lib/` should be included in `.classpath` for the Eclipse project to work properly.
