# ZIO test macros

This micro-library lets you use a more concise ZIO assertion syntax for class fields:
```scala
import com.timushev.zio.test.macros.Assertion._

assert(book)(hasPath(_.author.name, isNonEmptyString))
```
Comparing to the default `hasField` combinator, `hasPath` does not make you duplicate the field name: 
```scala
assert(book)(hasField("author.name", _.author.name, isNonEmptyString))
```

A compiler macro captures the field names for you, so in case of a failure the error message is still informative:
```scala
// com.timushev.zio.test.macros.Assertion.hasPath
Book(Writer()) did not satisfy hasPath(_.author.name, isNonEmptyString())

// zio.test.Assertion.hasField
Book(Writer()) did not satisfy hasField("author.name", _.author.name, isNonEmptyString())
```

## Usage

The library is cross-built for Scala 2.12 and 2.13, both JVM and Scala.js 1.0 platforms. It is not published to Maven Central yet.

## Credits

Macro implementation is inspired by [Monocle](https://github.com/optics-dev/monocle).
