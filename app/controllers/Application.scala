package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import models.Movie
import play.api.mvc.BodyParsers
import play.api.libs.json.JsError

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Hello Play Framework"))
  }
  
  def movie = Action {
    Ok(views.html.movie("Hello Play Framework"))
  }
  
  def listMovies = Action {
	  val json = Json.toJson(Movie.list)
	  Ok(json)
  }
  
//  def saveMovie(movie: Movie) = {
//    Movie.save(movie)
//    val json = Json.toJson(movie)
//	  Ok(json)
//  }
// 
  
  def deleteMovie(id:Long) = Action { request =>
    Movie.delete(id) match{
      case Left(error) => 
	      BadRequest(Json.obj("status" ->"KO", "message" -> error))
      case Right(_)=>
	      Ok(Json.obj("status" ->"OK" ))  	    
    }
    
  }
  
  
  def updateMovie(id:Long) = Action(BodyParsers.parse.json) { request =>
    val placeResult = request.body.validate[Movie]
    placeResult.fold(
	    errors => {
	      BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
	    },
	    movie => { 
	      Movie.update(movie)
	      val json = Json.toJson(movie)
	      Ok(json) 
	    }
	  )
    
  }
  
  def getMovie(id:Long)=Action { request =>
    val ourmovie=Movie.get(id)
    ourmovie match{
      case None =>  BadRequest(Json.obj("status" ->"KO", "message" -> s"missing movie for this id $id"))
      case Some(movie)=>
      	val json = Json.toJson(movie)
      	Ok(json)
    }
  }
  
  def saveMovie = Action(BodyParsers.parse.json) { request =>

	  val placeResult = request.body.validate[Movie]
	  placeResult.fold(
	    errors => {
	      BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
	    },
	    movie => { 
	      Movie.save(movie)
	      Ok(Json.obj("status" ->"OK", "message" -> ("Title '"+movie.title+"' saved.") ))  
	    }
	  )
  }
}