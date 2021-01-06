package com.timushev.zio.test.macros

import zio.test._

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object Assertion {

  /**
    * Makes a new assertion that focuses in on a field path in a class.
    *
    * {{{
    * hasPath(_.date.year, within(2010, 2020))
    * }}}
    */
  def hasPath[A, B](proj: A => B, assertion: Assertion[B]): Assertion[A] =
    macro AssertionImpl.hasPath_impl[A, B]

}

private class AssertionImpl(val c: blackbox.Context) {

  def hasPath_impl[A, B](
      proj: c.Expr[A => B],
      assertion: c.Expr[Assertion[B]]
  )(implicit A: c.WeakTypeTag[A], B: c.WeakTypeTag[B]): c.Expr[Assertion[A]] = {
    import c.universe._

    object SelectChain {
      def unapply(tree: Tree): Option[(Name, Seq[TermName])] =
        tree match {
          case Select(Ident(termUseName), field: TermName) =>
            Some((termUseName, Seq(field)))
          case Select(tail, field: TermName) =>
            SelectChain.unapply(tail).map(t => t.copy(_2 = t._2 :+ field))
          case _ =>
            None
        }
    }

    proj match {
      case Expr(
            Function(
              List(ValDef(_, termDefName, _, EmptyTree)),
              SelectChain(termUseName, typesFields)
            )
          ) if termDefName.decodedName.toString == termUseName.decodedName.toString =>
        val path = typesFields.map(_.decodedName).mkString("_.", ".", "")
        c.Expr[Assertion[A]](
          q"""
             _root_.zio.test.Assertion.assertionRec[$A,$B](
               "hasPath"
             )(
               _root_.zio.test.AssertionM.Render.param($path),
               _root_.zio.test.AssertionM.Render.param($assertion)
             )(
               $assertion
             )(
               a => _root_.scala.Some($proj(a))
             )"""
        )
      case _ =>
        c.abort(
          c.enclosingPosition,
          s"Illegal field reference ${show(proj.tree)}; please use _.field1.field2... instead"
        )
    }
  }
}
