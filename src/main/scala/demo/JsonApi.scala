package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

import scala.util.{Failure, Success}

case class User(id: Int, name: Option[String], phone: Option[Int], password: String)

trait JsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val userFormat = jsonFormat4(User)
}

object JsonApi extends App with JsonProtocol {

  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  val route: Route =
    pathPrefix("user") {
      get {
        path(IntNumber) { id =>
          complete(User(id, Option("Juan"), None, "1234"))
        }
      }
    }

  Http().bindAndHandleAsync(Route.asyncHandler(route), "localhost", 8080)
    .onComplete {
      case Success(_) => println("server started")
      case Failure(e) => println("binding failed")
    }

}

