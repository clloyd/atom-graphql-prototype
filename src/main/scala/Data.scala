package data

import sangria.marshalling.playJson._
import play.api.libs.json._
import support.CAPI

object AtomType extends Enumeration {
  val Explainer, Cta, Media = Value
}

case class Atom(
               id: String,
               atomType: AtomType.Value,
               defaultHtml: String,
               data: AtomData
               )


sealed trait AtomData

case class MediaAtomData(title: String) extends AtomData
case class CtaAtomData(url: String) extends AtomData
case class ExplainerAtomData(title: String, body: String) extends AtomData



object Atom {
  def fromCapiJson(json: JsValue): Atom = {

    val atomType = AtomType.withName((json \ "atomType").as[String].capitalize)

    val atomData = atomType match {
      case AtomType.Media => MediaAtomData(
        title = (json \ "data" \ "media" \ "title").as[String]
      )

      case AtomType.Cta => CtaAtomData(
        url = (json \ "data" \ "cta" \ "url").as[String]
      )

      case AtomType.Explainer => ExplainerAtomData(
        title = (json \ "data" \ "explainer" \ "title").as[String],
        body = (json \ "data" \ "explainer" \ "body").as[String]
      )
    }

    Atom(
      id = (json \ "id").as[String],
      atomType = atomType,
      defaultHtml = (json \ "defaultHtml").as[String],
      data = atomData
    )
  }
}

class AtomRepo {
  def getAtom(id: String, atomType: String): Option[Atom] = {
    CAPI.getAtom(id, atomType)
  }
}
