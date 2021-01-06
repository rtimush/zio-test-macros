package com.timushev.zio.test.macros

import com.timushev.zio.test.macros.Assertion._
import zio.test.Assertion._
import zio.test._

object ExampleSpec extends DefaultRunnableSpec {

  private case class Book(author: Writer)
  private case class Writer(name: String)

  private val conanDoyle = Writer("Arthur Conan Doyle")
  private val book       = Book(conanDoyle)

  val spec: ZSpec[Environment, Failure] =
    suite("Example")(
      suite("simple fields")(
        test("hasField") {
          assert(conanDoyle)(hasField("name", _.name, isNonEmptyString))
        },
        test("hasPath") {
          assert(conanDoyle)(hasPath(_.name, isNonEmptyString))
        }
      ),
      suite("nested fields")(
        test("hasField") {
          assert(book)(hasField("author.name", _.author.name, isNonEmptyString))
        },
        test("hasPath") {
          assert(book)(hasPath(_.author.name, isNonEmptyString))
        }
      )
    )

}
