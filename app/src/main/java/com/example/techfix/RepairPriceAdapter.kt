package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepairPriceAdapter(
    private val repairPrices: MutableList<RepairPrice>
) : RecyclerView.Adapter<RepairPriceAdapter.RepairPriceViewHolder>() {

    class RepairPriceViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvServiceName: TextView =
            itemView.findViewById(R.id.tvServiceName)

        val tvDeviceType: TextView =
            itemView.findViewById(R.id.tvDeviceType)

        val tvDescription: TextView =
            itemView.findViewById(R.id.tvDescription)

        val tvPrice: TextView =
            itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepairPriceViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_repair_price,
                parent,
                false
            )

        return RepairPriceViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RepairPriceViewHolder,
        position: Int
    ) {

        val repairPrice = repairPrices[position]

        holder.tvServiceName.text =
            repairPrice.serviceName

        holder.tvDeviceType.text =
            repairPrice.deviceType

        holder.tvDescription.text =
            repairPrice.description

        holder.tvPrice.text =
            "Rs. ${repairPrice.price}"
    }

    override fun getItemCount(): Int {
        return repairPrices.size
    }

    fun addRepairPrice(repairPrice: RepairPrice) {

        repairPrices.add(repairPrice)

        notifyItemInserted(
            repairPrices.size - 1
        )
    }
}