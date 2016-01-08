package stream

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, ClosedShape}

object FlowGraphSample extends App {
  implicit val system = ActorSystem("Sys")

  implicit val materializer = ActorMaterializer()

  val text =
    """|Lorem Ipsum is simply dummy text of the printing and typesetting industry.
      |Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
      |when an unknown printer took a galley of type and scrambled it to make a type
      |specimen book.""".stripMargin

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[Unit] =>
    import GraphDSL.Implicits._

    val in = Source.fromIterator(() => text.split("\\s").iterator)
    val out = Sink.foreach[String](println)
    val upper = Flow[String].map(_.toUpperCase)

    in ~> upper ~> out
    ClosedShape
  })

  g.run()
}
