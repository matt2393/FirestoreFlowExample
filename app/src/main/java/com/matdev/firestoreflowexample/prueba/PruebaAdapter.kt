package com.matdev.firestoreflowexample.prueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matdev.firestoreflowexample.R
import com.matdev.firestoreflowexample.databinding.ItemRecyclerBinding
import com.matdev.firestoreflowexample.model.entities.Prueba

class PruebaAdapter(var arrayPrueba: ArrayList<Prueba> = arrayListOf()): RecyclerView.Adapter<PruebaAdapter.PruebaViewHolder>() {
    inner class PruebaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecyclerBinding.bind(itemView)
        fun bind(prueba: Prueba) {
            binding.text.text = prueba.texto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PruebaViewHolder =
        PruebaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler, parent, false)
        )

    override fun onBindViewHolder(holder: PruebaViewHolder, position: Int) {
        val prueba = arrayPrueba[position]
        holder.bind(prueba)
    }

    override fun getItemCount(): Int = arrayPrueba.size

    fun getIndex(prueba: Prueba): Int {
        var index = -1
        arrayPrueba.forEachIndexed { i, p ->
            if(prueba.id == p.id) {
                index = i
                return@forEachIndexed
            }
        }
        return index
    }
}