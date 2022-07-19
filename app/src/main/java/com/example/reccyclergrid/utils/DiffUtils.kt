package com.example.reccyclergrid.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.reccyclergrid.models.Person

class DiffUtils(
    private val newList: MutableList<Person>,
    private val oldList: MutableList<Person>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}