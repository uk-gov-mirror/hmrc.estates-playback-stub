/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import javax.inject.Inject
import play.api.mvc.{AnyContent, ControllerComponents, Request, Result}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import utils.JsonUtils._

import scala.concurrent.Future

class StubBaseController @Inject()()(implicit cc: ControllerComponents)
  extends BackendController(cc) with HeaderValidator {

  private val utrRegex = "^[0-9]{10}$".r

  def jsonResult(utr: String, status: Status) (implicit request: Request[AnyContent]): Future[Result] = {
    val path = s"/resources/$utr.json"
    Future.successful(Ok(jsonFromFile(path)).
      withHeaders(request.headers.get(CORRELATION_ID_HEADER).
        map((CORRELATION_ID_HEADER, _)).toSeq: _*))
  }

  def isUtrValid(utr:String): Boolean = {
    utrRegex.findFirstIn(utr).isDefined
  }

}
