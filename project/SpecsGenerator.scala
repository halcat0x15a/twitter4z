import sbt._

object SpecsGenerator extends Generator {

  type Result = List[String ~ List[String]]

  def parser = rep((name <~ colon ~ Typo ~ eol ~ method ~ space ~ resourceUrl ~ eol ~ auth ~ eol) ~ (parameters <~ eol) <~ parameters ~ eol)

  def generate(dir: File, resource: File): Seq[File] = {
    val testData = new java.util.Properties
    testData.load(new java.io.FileReader(resource / "test.properties"))
    listFiles(resource / "api") map { f =>
      val name = toUpperCamel(f.name)
      val specs = parseAll(f) map {
	case name ~ require => {
	  val args = require.map {
	    case Or(s, _) => s
	    case s => s
	  }.map(testData.getProperty(_, "")).filter("" !=).mkString(", ")
	  """  "%s" should {
    "be success" in {
      %s(%s) must be isSuccess
    }
  }""".format(name, name, args)
	}
      }
      val source = """package twitter4z.api

import org.specs2.mutable._

import twitter4z.Twitter._

class %sSpec extends Specification {

  implicit def tokens = readTokens("%s")

%s
}
""".format(name, testData.getProperty("file", "test"), specs)
      write(dir / "twitter4z" / "api" / (name + ".scala"), source)
    }
  }

}
