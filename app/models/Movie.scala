package models


import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Movie(title:String, director:String, releaseYear: Int, genre: String, id: Option[Long]);

object Movie {
  
  
  
  var list: List[Movie] = {
    List(
      Movie(
        "The Room", "Tommy Wiseau", 2003, "comedy",Some(0l)
      ),
      Movie(
        "Avengers", "Tony B.", 2003, "comedy",Some(1l)
      )
    )
  }
    
  var currentid=list.map(_.id).flatten.sortBy(-_).head
  
  private def nextId():Long={
    currentid+=1
    currentid
  }
  
  def save(movie: Movie) = {   
    list = list ::: List(movie.copy( id=Some(nextId())))
  }
  
  def get(id:Long)={
    Movie.list.filter(movie => movie.id.exists(_==id)).headOption
  }
  
  def delete(id:Long):Either[String,Unit]={
    val newlist=list.filter(_.id.get!=id)

    if( newlist.size==list.size) Left("element no present")
    else{
      list=newlist;
      Right(())
    }    
  }
  
  def update(movie: Movie) ={
    list = movie:: list.filter(_.id!=movie.id);
  }
  
  
  
  implicit val movieWrites: Writes[Movie] = (
	  (JsPath \ "title").write[String] and
	  (JsPath \ "director").write[String] and
	  (JsPath \ "releaseYear").write[Int] and
	  (JsPath \ "genre").write[String] and
	  (JsPath \ "_id").writeNullable[Long]
  )(unlift(Movie.unapply))

  implicit val placeReads: Reads[Movie] = (
  	(JsPath \ "title").read[String] and
	  (JsPath \ "director").read[String] and
	  (JsPath \ "releaseYear").read[Int] and
	  (JsPath \ "genre").read[String] and
	  (JsPath \ "_id").readNullable[Long]
  )(Movie.apply _)

}