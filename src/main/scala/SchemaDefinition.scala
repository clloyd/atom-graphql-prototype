import sangria.execution.deferred.Fetcher
import sangria.schema._
import data._

import scala.concurrent.Future

/**
 * Defines a GraphQL schema for the current project
 */
object SchemaDefinition {
  /**
    * Resolves the lists of characters. These resolutions are batched and
    * cached for the duration of a query.
    */

  val AtomTypeEnum = EnumType(
    "AtomTypeData",
    Some("The specific Atom Type"),
    List(
      EnumValue("MEDIA",
        value = AtomType.Media,
        description = Some("Media Atom")),
      EnumValue("CTA",
        value = AtomType.Cta,
        description = Some("Call to Action Atom")),
      EnumValue("EXPLAINER",
        value = AtomType.Explainer,
        description = Some("Blue Box Atom"))))

  val MediaAtomDataType = ObjectType(
    name = "MediaAtomData",
    description = "A Media Atom",
    fields[AtomRepo, MediaAtomData](
      Field("title", StringType,
        Some("The title of the media atom"),
        resolve = _.value.title
      )
    )
  )

  val CtaAtomDataType = ObjectType(
    name = "CtaAtomData",
    description = "A CTA Atom",
    fields[AtomRepo, CtaAtomData](
      Field("url", StringType,
        Some("The url of the cta"),
        resolve = _.value.url
      )
    )
  )

  val ExplainerAtomDataType = ObjectType(
    name = "ExplainerAtomData",
    description = "An Explainer Atom",
    fields[AtomRepo, ExplainerAtomData](
      Field("title", StringType,
        Some("The title of the explainer"),
        resolve = _.value.title
      ),
      Field("body", StringType,
        Some("The body of the explainer"),
        resolve = _.value.body
      )
    )
  )

  val AtomData = UnionType(
    "AtomDataType",
    types = List(MediaAtomDataType, CtaAtomDataType, ExplainerAtomDataType)
  )


  val Atom = ObjectType(
      "Atom",
      "A Generic Guardian Atom",
      fields[AtomRepo, Atom](
        Field("id", StringType,
          Some("The id of the atom."),
          resolve = _.value.id),
        Field("atomType", AtomTypeEnum,
          Some("The atom type"),
          resolve = _.value.atomType
        ),
        Field("defaultHtml", StringType,
          Some("A html rendering of the atom for fallback purposes"),
          resolve = _.value.defaultHtml
        ),
        Field("data", AtomData,
          Some("The atom data"),
          resolve = _.value.data
        )
      )
  )


  val ID = Argument("id", StringType, description = "id of the atom")
  val ATOMTYPE = Argument("atomType", StringType, description = "type of the atom")
  val QUERYSTRING = Argument("query", StringType, description = "text to search for")

  val AtomQuery = ObjectType(
    "Query", fields[AtomRepo, Unit](
      Field("atom", OptionType(Atom),
        arguments = ID :: ATOMTYPE :: Nil,
        resolve = ctx => ctx.ctx.getAtom(ctx arg ID, ctx arg ATOMTYPE)),
      Field("search", ListType(Atom),
        arguments = QUERYSTRING :: Nil,
        resolve = ctx => ctx.ctx.searchAtoms(ctx arg QUERYSTRING)
      )
    ))

  val AtomSchema = Schema(AtomQuery)
}

