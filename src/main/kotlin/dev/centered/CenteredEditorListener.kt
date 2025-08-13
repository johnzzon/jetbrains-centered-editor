package dev.centered

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.ui.components.JBViewport
import javax.swing.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.*
import kotlin.math.max

class CenteredEditorListener : EditorFactoryListener {

  override fun editorCreated(event: EditorFactoryEvent) {
    applyCentering(event.editor)
    attachResizeListener(event.editor)
  }

  private fun attachResizeListener(editor: Editor) {
    val ex = editor as? EditorEx ?: return
    val scrollPane = ex.scrollPane
    
    val key = "dev.centered.resizeListenerInstalled"
    if (scrollPane.getClientProperty(key) == true) return

    scrollPane.addComponentListener(object : ComponentAdapter() {
      override fun componentResized(e: ComponentEvent?) {
        applyCentering(editor)
      }
    })
    scrollPane.putClientProperty(key, true)
  }

  private fun applyCentering(editor: Editor) {
    val ex = editor as? EditorEx ?: return
    val scrollPane = ex.scrollPane
    val contentComponent = ex.contentComponent as? JComponent ?: return
    
    // Walk up the component hierarchy to find the right container to center
    var targetContainer: Container? = scrollPane.parent
    var availableWidth = 0
    
    // Go up several levels to find a container with meaningful width
    for (i in 0..5) {
      targetContainer = targetContainer?.parent ?: break
      availableWidth = targetContainer.width
      if (availableWidth > 0) break
    }
    
    val container = targetContainer as? JComponent ?: return
    if (availableWidth <= 0) return

    val settings = CenteredSettings.get()

    // Calculate target content width based on font metrics  
    val fm = contentComponent.getFontMetrics(contentComponent.font)
    val targetColumns = 80
    val charW = fm.charWidth('M').coerceAtLeast(6)
    val gutterWidth = ex.gutterComponentEx?.width ?: 0
    val targetContentWidth = targetColumns * charW + gutterWidth + settings.extraGutterAllowancePx

    // Calculate centering padding - use double the side padding as left padding only
    val totalPadding = max(0, availableWidth - targetContentWidth)
    val leftPadding = max(settings.minSidePaddingPx, totalPadding / 2)
    
    // Apply only left border to center content while keeping scrollbar at right edge
    scrollPane.border = BorderFactory.createEmptyBorder(0, leftPadding, 0, 0)
    
    // Clear other borders
    scrollPane.setViewportBorder(null)
    contentComponent.border = null
    
    // Force layout update
    container.revalidate()
    container.repaint()
  }
}
