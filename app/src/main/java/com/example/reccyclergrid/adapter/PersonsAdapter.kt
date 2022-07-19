package com.example.reccyclergrid.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reccyclergrid.databinding.FemalePersonItemBinding
import com.example.reccyclergrid.databinding.MalePersonItemBinding
import com.example.reccyclergrid.models.Person
import com.example.reccyclergrid.utils.DiffUtils

class PersonsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var deletePerson: ((Person) -> Unit)? = null
    var modifyPerson: ((Person) -> Unit)? = null

    private var personsList = mutableListOf<Person>()

    companion object {
        const val MALE_ITEM = 1
        const val FEMALE_ITEM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MALE_ITEM) {
            MaleViewHolder(
                MalePersonItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            FemaleViewHolder(
                FemalePersonItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MaleViewHolder) {
            holder.onBind()
        } else if (holder is FemaleViewHolder) {
            holder.onBind()
        }
    }

    override fun getItemViewType(position: Int) = if (personsList[position].gender) {
        MALE_ITEM
    } else {
        FEMALE_ITEM
    }

    override fun getItemCount() = personsList.size

    inner class MaleViewHolder(private val binding: MalePersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun onBind() {
            val model = personsList[adapterPosition]
            binding.apply {
                tvMaleItemId.text = "პ.ნ: " + model.id.toString()
                tvMaleItemName.text = "სახელი: " + model.name
                tvMaleItemLastName.text = "გვარი: " + model.lastName
                root.setOnLongClickListener {
                    deletePerson?.invoke(model)
                    personsList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    true
                }
                root.setOnClickListener {
                    modifyPerson?.invoke(model)
                }
            }
        }
    }

    inner class FemaleViewHolder(private val binding: FemalePersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun onBind() {

            val model = personsList[adapterPosition]
            binding.apply {
                tvFemaleItemId.text = "პ.ნ: " + model.id.toString()
                tvFemaleItemName.text = "სახელი: " + model.name
                tvFemaleItemLastName.text = "გვარი: " + model.lastName
                root.setOnLongClickListener {
                    deletePerson?.invoke(model)
                    personsList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    true
                }
                root.setOnClickListener {
                    modifyPerson?.invoke(model)
                }
            }
        }

    }

    fun setData(list: MutableList<Person>) {
        val diffUtils = DiffUtils(personsList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        personsList = list
        diffResult.dispatchUpdatesTo(this)
    }

    fun addPerson(newPerson: Person) {
        personsList.add(newPerson)
        notifyItemInserted(personsList.lastIndex)
    }


}