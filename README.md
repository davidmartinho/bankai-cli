# Bankai CLI

Command line client for the [bankai framework](https://github.com/ist-dsi/bankai/).

## Installation

For each submodule, do a `mvn clean install` (yep, a parent pom would come in handy)

then `mvn clean compile assembly:single` the bankai-cli.

Put an alias on your shell for the genereted jar, like so:

`alias bankai="java -jar location/of/jar/bankai-cli-<X.X.X>-jar-with-dependencies.jar"`

and then go ahead and type `bankai help` for a list of commands.
