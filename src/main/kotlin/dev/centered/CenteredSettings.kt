package dev.centered

import com.intellij.openapi.components.Service
import com.intellij.openapi.application.ApplicationManager

@Service(Service.Level.APP)
class CenteredSettings {
  var minSidePaddingPx: Int = 24
  var extraGutterAllowancePx: Int = 32

  companion object {
    fun get(): CenteredSettings =
      ApplicationManager.getApplication().getService(CenteredSettings::class.java)
  }
}
