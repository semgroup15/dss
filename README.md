# DSS

## Build

* Symlink or copy `jfxrt.jar` to the `lib` directory
* Build with `ant build`


## Commands

### DB

* `ant run -Dcommand=db.init` Create tables


### GSMArena

* `ant run -Dcommand=ga.init` Initialize with minimal device list
* `ant run -Dcommand=ga.expand` Expand current database with details


### Media

* `ant run -Dcommand=media.fetch` Fetch missing device images


### UI

* `ant run -Dcommand=ui` Main UI


## Libraries

Each JAR library under `lib/` should be included in `.classpath` for the Eclipse project to work properly.
