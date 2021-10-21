package com.example.myrecyclerviewapp

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecyclerviewapp.model.CatUiModel
import com.example.myrecyclerviewapp.model.ListItemUiModel
import com.example.myrecyclerviewapp.viewholder.CatViewHolder
import com.example.myrecyclerviewapp.viewholder.ListItemViewHolder
import com.example.myrecyclerviewapp.viewholder.TitleViewHolder
import java.lang.IllegalArgumentException

private const val VIEW_TYPE_TITLE = 0
private const val VIEW_TYPE_CAT  = 1

class ListItemsAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<ListItemViewHolder>() {
    val swipeToDeleteCallback = SwipeToDeleteCallback()

    private val listData = mutableListOf<ListItemUiModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: List<ListItemUiModel>) {
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(position :Int,item : ListItemUiModel){
        listData.add(position , item)
        notifyItemInserted(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_TITLE -> {
            val view = layoutInflater.inflate(R.layout.item_title, parent, false)
            TitleViewHolder(view)
        }
        VIEW_TYPE_CAT -> {
            val view = layoutInflater.inflate(R.layout.item_cat, parent, false)
            CatViewHolder(view, imageLoader, object : CatViewHolder.OnClickListener {
                override fun onClick(catData: CatUiModel) {
                    onClickListener.onItemClicked(catData)
                }
            })

        }
        else -> throw IllegalArgumentException("Unknown view type requested : $viewType")
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) = holder.bindData(listData[position])

    override fun getItemCount(): Int = listData.size

    override fun getItemViewType(position: Int) =
        when (listData[position]) {
            is ListItemUiModel.Title -> VIEW_TYPE_TITLE
            is ListItemUiModel.Cat -> VIEW_TYPE_CAT
        }

    interface OnClickListener {
        fun onItemClicked(catData: CatUiModel)

    }

    inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or
                ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = if (viewHolder is CatViewHolder) {
            makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) or makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        } else 0

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }

    }

}