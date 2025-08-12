package dev.centered

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.ex.EditorEx
import javax.swing.JComponent
import javax.swing.border.EmptyBorder
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import kotlin.math.max

class CenteredEditorListener : EditorFactoryListener {

  override fun editorCreated(event: EditorFactoryEvent) {
    applyCentering(event.editor)
    attachResizeListener(event.editor)
  }

  private fun attachResizeListener(editor: Editor) {
    val comp = editor.contentComponent as? JComponent ?: return
    val parent = comp.parent ?: return

    val key = "dev.centered.resizeListenerInstalled"
    val swingParent = parent as? JComponent ?: return
    if (swingParent.getClientProperty(key) == true) return

    swingParent.addComponentListener(object : ComponentAdapter() {
      override fun componentResized(e: ComponentEvent?) {
        applyCentering(editor)
      }
    })
    swingParent.putClientProperty(key, true)
  }

  private fun applyCentering(editor: Editor) {
    val ex = editor as? EditorEx ?: return
    val comp = ex.contentComponent as? JComponent ?: return
    val viewport = comp.parent ?: return

    val viewWidth = (viewport.width.takeIf { it > 0 } ?: comp.width)
    if (viewWidth <= 0) return

    val settings = CenteredSettings.get()

    // Simple, stable: center around ~120 columns of 'M' width
    val fm = comp.getFontMetrics(comp.font)
    val targetColumns = 120
    val charW = fm.charWidth('M').coerceAtLeast(6)
    val desired = (targetColumns * charW) + settings.extraGutterAllowancePx

    val space = viewWidth - desired
    val side = max(settings.minSidePaddingPx, space / 2)

    comp.border = EmptyBorder(0, side, 0, side)
    comp.revalidate()
    comp.repaint()
  }
}
