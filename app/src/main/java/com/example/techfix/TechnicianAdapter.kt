package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TechnicianAdapter(
    private val technicianList: MutableList<Technician>,
    private val onEdit: (Technician) -> Unit,
    private val onDelete: (Technician) -> Unit
) : RecyclerView.Adapter<TechnicianAdapter.TechnicianViewHolder>() {

    class TechnicianViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val tvName: TextView =
            itemView.findViewById(R.id.tvTechnicianName)

        val tvSpecialization: TextView =
            itemView.findViewById(R.id.tvTechnicianSpecialization)

        val tvPhone: TextView =
            itemView.findViewById(R.id.tvTechnicianPhone)

        val tvAvailability: TextView =
            itemView.findViewById(R.id.tvTechnicianAvailability)

        val btnEdit: Button =
            itemView.findViewById(R.id.btnEditTechnician)

        val btnDelete: Button =
            itemView.findViewById(R.id.btnDeleteTechnician)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TechnicianViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_technician,
                parent,
                false
            )

        return TechnicianViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TechnicianViewHolder,
        position: Int
    ) {

        val technician = technicianList[position]

        holder.tvName.text =
            technician.name

        holder.tvSpecialization.text =
            "Specialization: ${technician.specialization}"

        holder.tvPhone.text =
            "Phone: ${technician.phone}"

        holder.tvAvailability.text =
            "Availability: ${technician.availability}"

        holder.btnEdit.setOnClickListener {
            onEdit(technician)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(technician)
        }
    }

    override fun getItemCount(): Int {
        return technicianList.size
    }
}