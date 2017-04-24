package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}

object SampleRoute extends App {

  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  val route: Route =
    pathPrefix("user") {
      get {
        path(IntNumber) { id =>
          complete(s"Hello user $id")
        }
      } ~
        post {
          parameter("email".as[String], "password") { (email, password) =>
            complete(s"New user with email $email and password $password")
          } ~
          parameters("phone".as[Int], "password") { (phone, password) =>
            complete(s"New user with phone $phone and password $password")
          } ~
          parameters("fingerprint", "password".?) { (fingerprint, password) =>
            password match {
              case Some(p) =>
                complete(s"New user with fingerprint $fingerprint and password $p")
              case _ =>
                complete(s"New user with fingerprint $fingerprint")
            }
          }
        }
    } ~ pathSingleSlash {
      complete("My awesome API is working!")
    }

  Http().bindAndHandleAsync(Route.asyncHandler(route), "localhost", 8080)
    .onComplete {
      case Success(_) => println("server started")
      case Failure(e) => println("binding failed")
    }

}
