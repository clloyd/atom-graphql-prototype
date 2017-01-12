package support

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request._
import play.api.libs.json._
import data._


object CAPI {
  val client = new OkHttpClient();

  val capiUrl = "ADD A CAPI ADDRESS"


  def getAtom(id: String, atomType: String): Option[Atom] = {
    val req = new Builder()
      .url(s"$capiUrl/atom/$atomType/$id?api-key=test")
      .build()

    val res = client.newCall(req).execute()

    res.code match {
      case 200 => {
        val json = Json.parse(res.body().string())
        val contentJson = (json \ "response" \ atomType).get

        Some(Atom.fromCapiJson(contentJson))
      }
      case c => {
        println(s"failed to get $atomType atom with id $id. response code $c, message ${res.body}")
        None
      }
    }
  }
}
