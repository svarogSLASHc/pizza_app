package com.treewall.av.pizzaapp.presentation.product_list.list

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min


class CenterLayout : LinearLayoutManager {

    private val shrinkAmount = 0.15f
    private val shrinkDistance = 0.9f

    constructor(context: Context) : super(context)
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        centerItems()
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            super.scrollVerticallyBy(dy, recycler, state).also {
                verticalItemsCenter()
            }
        } else {
            0
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == HORIZONTAL) {
            super.scrollHorizontallyBy(dx, recycler, state).also {
                horizontalItemsCenter()
            }
        } else {
            0
        }
    }

    private fun centerItems() {
        if (orientation == HORIZONTAL)
            horizontalItemsCenter()
        else
            verticalItemsCenter()
    }

    private fun horizontalItemsCenter() {
        val midpoint = width / 2f
        val d0 = 0f
        val d1 = shrinkDistance * midpoint
        val s0 = 1f
        val s1 = 1f - shrinkAmount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
            val d = min(d1, abs(midpoint - childMidpoint))
            val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
            child.scaleX = scale
            child.scaleY = scale
        }
    }

    private fun verticalItemsCenter() {
        val midpoint = height / 2f
        val d0 = 0f
        val d1 = shrinkDistance * midpoint
        val s0 = 1f
        val s1 = 1f - shrinkAmount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMidpoint = (getDecoratedBottom(child!!) + getDecoratedTop(child)) / 2f
            val d = min(d1, abs(midpoint - childMidpoint))
            val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}