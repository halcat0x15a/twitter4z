package twitter4z.api

import org.specs2._
import specification._

import scalaz._
import Scalaz._

import twitter4z.Twitter
import twitter4z.api._
import twitter4z.objects._

class TimelinesSpec extends Specification with Twitter { def is =

  args(sequential=true, stopOnFail=true)                                          ^
  "Timelines Resource Spec".title                                                 ^
                                                                                  p^
  "A example for counted memtions"                                                ^
    "Given the following number: ${3}"                                            ^ number ^
    "Then statuses must have size: ${3}"                                          ^ count(mentions) ^
                                                                                  endp ^
  "A example for home timeline since id"                                          ^
    "Given the following id: ${177403983214813185}"                               ^ id ^
    "Then statuses must not contain status of id"                                 ^ since(homeTimeline) ^
                                                                                  end

  type Auth = Required

  val auth = {
    val props = new java.util.Properties
    props.load(getClass.getResourceAsStream("/test.properties"))
    Required(props)
  }

  val TestUser = "halcat0x15a"

  object number extends Given[Int] {
    def extract(text: String) = extract1(text).toInt
  }

  case class count[A <: Resource[List[Status], Required, A] with parameter.Paging](resource: A) extends Then[Int] {
    def extract(number: Int, text: String) = {
      resource.count(number)().value must be like {
	case Success((_, statuses)) => statuses must have size extract1(text).toInt
      }
    }
  }

  object id extends Given[Long] {
    def extract(text: String) = extract1(text).toLong
  }

  case class since[A <: Resource[List[Status], Required, A] with parameter.Paging](resource: A) extends Then[Long] {
    def extract(id: Long, text: String) = {
      resource.sinceId(id)().value must be like {
	case Success((_, statuses)) => statuses.map(_.id) must not contain id
      }
    }
  }

}
