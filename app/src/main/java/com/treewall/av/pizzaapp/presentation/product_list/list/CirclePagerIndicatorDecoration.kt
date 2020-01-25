package com.treewall.av.pizzaapp.presentation.product_list.list

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CirclePagerIndicatorDecoration : RecyclerView.ItemDecoration() {

    private val color = Color.parseColor("#ffffff")

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */

    private val indicatorBottomOffset = (DP * 32).toInt()
    private val indicatorHeight = (DP * 12).toInt()

    /**
     * Indicator stroke width.
     */
    private val indicatorStrokeWidth = DP * 2

    /**
     * Indicator width.
     */
    private val indicatorItemLength = DP * 2
    /**
     * Padding between indicators.
     */
    private val indicatorItemPadding = DP * 24

    /**
     * Some more natural animation interpolation
     */
    private val interpolator = AccelerateDecelerateInterpolator()

    private val paint = Paint()

    init {
        paint.color = color
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = indicatorStrokeWidth
        paint.isAntiAlias = true
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val itemCount = parent.adapter!!.itemCount

        // center horizontally, calculate width and subtract half from center
        val totalLength = indicatorItemLength * itemCount
        val paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = parent.height - indicatorHeight / 2f - indicatorStrokeWidth

        drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, itemCount)


        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        val activePosition = layoutManager!!.findFirstCompletelyVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left
        val width = activeChild.width

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = interpolator.getInterpolation(left * -1 / width.toFloat())

        drawHighlights(canvas, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        paint.style = Paint.Style.STROKE

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawCircle(start, indicatorPosY, indicatorHeight.toFloat() / 2, paint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int
    ) {
        paint.style = Paint.Style.FILL_AND_STROKE

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawCircle(highlightStart, indicatorPosY, indicatorHeight.toFloat() / 2, paint)

        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = indicatorItemLength * progress

            // draw the cut off highlight
            c.drawCircle(
                highlightStart + partialLength,
                indicatorPosY,
                indicatorHeight.toFloat() / 2,
                paint
            )

//            // draw the highlight overlapping to the next item as well
//            if (highlightPosition < itemCount - 1) {
//                highlightStart += itemWidth
//
//                c.drawLine(
//                    highlightStart, indicatorPosY,
//                    highlightStart + partialLength, indicatorPosY, mPaint
//                )
//            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorBottomOffset
    }

    companion object {
        private val DP = Resources.getSystem().displayMetrics.density
    }
}
