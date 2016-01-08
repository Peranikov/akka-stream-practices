// refer: https://github.com/typesafehub/activator-akka-stream-scala/blob/master/src/main/scala/sample/stream/BasicTransformation.scala

package stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

object BasicTransformation extends App {
  implicit val system = ActorSystem("Sys")
  import system.dispatcher

  implicit val materializer = ActorMaterializer()

  val text =
    """|Lorem Ipsum is simply dummy text of the printing and typesetting industry.
      |Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
      |when an unknown printer took a galley of type and scrambled it to make a type
      |specimen book.""".stripMargin

  Source.fromIterator(() => text.split("\\s").iterator)
    .map(_.toUpperCase)
    .runForeach(println)
    .onComplete(_ => system.shutdown())
}
