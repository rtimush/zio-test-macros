package com.timushev.zio.test.macros

import com.timushev.zio.test.macros.Assertion._
import zio.test.Assertion._
import zio.test.Assertion.Render._
import zio.test._
import zio.test.AssertionM.RenderParam

object AssertionSpec extends DefaultRunnableSpec {

  private case class Foo(bar: Bar)
  private case class Bar(baz: Int)

  val spec: ZSpec[Environment, Failure] =
    suite("AssertionSpec")(
      suite("hasPath")(
        test("works for a first-level field") {
          assert(hasPath[Bar, Int](_.baz, equalTo(1)).run(Bar(1)).failures)(isNone) &&
          assert(hasPath[Bar, Int](_.baz, equalTo(2)).run(Bar(1)).failures)(isSome)
        },
        test("works for a second-level field") {
          assert(hasPath[Foo, Int](_.bar.baz, equalTo(1)).run(Foo(Bar(1))).failures)(isNone)
          assert(hasPath[Foo, Int](_.bar.baz, equalTo(2)).run(Foo(Bar(1))).failures)(isSome)
        },
        test("captures field name for a first-level field") {
          assert(hasPath[Bar, Int](_.baz, equalTo(2)).render)(
            equalTo(function("hasPath", List(List(param("_.baz"), param(equalTo(2))))))
          )
        },
        test("captures field name for a second-level field") {
          assert(hasPath[Foo, Int](_.bar.baz, equalTo(2)).render)(
            equalTo(function("hasPath", List(List(param("_.bar.baz"), param(equalTo(2))))))
          )
        }
      )
    )

}
