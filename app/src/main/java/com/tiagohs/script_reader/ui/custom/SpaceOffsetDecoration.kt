package com.tiagohs.script_reader.ui.custom


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceOffsetDecoration(
        private val offset: Int,
        private val offsetPosition: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dataSize = state.itemCount
        val position = parent.getChildAdapterPosition(view)

        when (offsetPosition) {
            TOP, LEFT -> { offsetOnStart(dataSize, position, outRect) }
            BOTTOM, RIGHT -> { offsetOnEnd(dataSize, position, outRect) }
        }
    }

    private fun offsetOnEnd(dataSize: Int, position: Int, outRect: Rect) {
        if (dataSize > 0 && position == dataSize - 1) {
            when (offsetPosition) {
                BOTTOM -> { outRect.set(0, 0, 0, offset) }
                RIGHT -> { outRect.set(0, 0, offset, 0) }
            }
        }
    }

    private fun offsetOnStart(dataSize: Int, position: Int, outRect: Rect) {
        if (dataSize > 0 && position == 0) {
            when (offsetPosition) {
                TOP -> { outRect.set(0, offset, 0, 0) }
                LEFT -> { outRect.set(offset, 0, 0, 0) }
            }
        }
    }

    companion object {
        const val BOTTOM = 1
        const val TOP = 2
        const val LEFT = 3
        const val RIGHT = 4
    }
}