package com.jaredrummler.cyanea.inflator

import android.util.AttributeSet
import android.view.View
import com.jaredrummler.cyanea.Cyanea
import java.util.Collections

class CyaneaViewFactory(val cyanea: Cyanea, vararg processors: CyaneaViewProcessor<View>) {

  private val processors = hashSetOf<CyaneaViewProcessor<View>>()

  init {
    Collections.addAll(this.processors, *processors)
  }

  internal fun onViewCreated(view: View, attrs: AttributeSet): View {
    for (processor in processors) {
      try {
        if (processor.shouldProcessView(view)) {
          processor.process(view, attrs, cyanea)
        }
      } catch (e: Exception) {
        Cyanea.log(TAG, "Error processing view", e)
      }
    }
    return view
  }

  companion object {
    private const val TAG = "CyaneaViewFactory"
  }

}