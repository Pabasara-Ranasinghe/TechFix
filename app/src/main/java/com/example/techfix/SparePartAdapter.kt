package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * SparePartAdapter
 *
 * Displays spare parts.
 *
 * Management can:
 * - Edit spare parts
 * - Delete spare parts
 */

class SparePartAdapter(

    private val sparePartList:
    MutableList<SparePart>,

    private val onEdit:
        (SparePart) -> Unit,

    private val onDelete:
        (SparePart) -> Unit

) : RecyclerView.Adapter<SparePartAdapter.SparePartViewHolder>() {


    // ==========================================
    // VIEW HOLDER
    // ==========================================

    class SparePartViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val tvName: TextView =
            itemView.findViewById(
                R.id.tvSparePartName
            )

        val tvCategory: TextView =
            itemView.findViewById(
                R.id.tvSparePartCategory
            )

        val tvQuantity: TextView =
            itemView.findViewById(
                R.id.tvSparePartQuantity
            )

        val tvPrice: TextView =
            itemView.findViewById(
                R.id.tvSparePartPrice
            )

        val btnEdit: Button =
            itemView.findViewById(
                R.id.btnEditSparePart
            )

        val btnDelete: Button =
            itemView.findViewById(
                R.id.btnDeleteSparePart
            )
    }


    // ==========================================
    // CREATE VIEW HOLDER
    // ==========================================

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SparePartViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_spare_part,
                    parent,
                    false
                )

        return SparePartViewHolder(view)
    }


    // ==========================================
    // DISPLAY DATA
    // ==========================================

    override fun onBindViewHolder(
        holder: SparePartViewHolder,
        position: Int
    ) {

        val sparePart =
            sparePartList[position]

        holder.tvName.text =
            sparePart.name

        holder.tvCategory.text =
            "Category: ${sparePart.category}"

        holder.tvQuantity.text =
            "Quantity: ${sparePart.quantity}"

        holder.tvPrice.text =
            "Price: Rs. ${sparePart.price}"


        // EDIT

        holder.btnEdit.setOnClickListener {

            onEdit(sparePart)
        }


        // DELETE

        holder.btnDelete.setOnClickListener {

            onDelete(sparePart)
        }
    }


    // ==========================================
    // ITEM COUNT
    // ==========================================

    override fun getItemCount(): Int {

        return sparePartList.size
    }
}