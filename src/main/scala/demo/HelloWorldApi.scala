package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.util.{Failure, Success}

object HelloWorldApi extends App {

  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  def handler(request: HttpRequest): Future[HttpResponse] =
    Future.successful(HttpResponse(entity = "Hello world!"))

  Http().bindAndHandleAsync(handler, "localhost", 8080)
    .onComplete {
      case Success(_) => println("server started")
      case Failure(e) => println("binding failed")
    }
}