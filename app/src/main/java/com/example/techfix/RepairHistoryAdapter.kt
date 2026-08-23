package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * RepairHistoryAdapter
 *
 * This adapter displays completed repair records
 * inside the Repair History RecyclerView.
 */

class RepairHistoryAdapter(
    private val historyList: List<RepairHistory>
) : RecyclerView.Adapter<RepairHistoryAdapter.HistoryViewHolder>() {

    /*
     * ViewHolder
     *
     * Stores references to the TextViews
     * inside item_repair_history.xml.
     */
    class HistoryViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        // Device type
        val tvDeviceType: TextView =
            itemView.findViewById(R.id.tvHistoryDeviceType)

        // Device model
        val tvModel: TextView =
            itemView.findViewById(R.id.tvHistoryModel)

        // Problem description
        val tvProblem: TextView =
            itemView.findViewById(R.id.tvHistoryProblem)

        // Repair date
        val tvDate: TextView =
            itemView.findViewById(R.id.tvHistoryDate)

        // Repair status
        val tvStatus: TextView =
            itemView.findViewById(R.id.tvHistoryStatus)
    }

    /*
     * Creates one repair history card.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_repair_history,
                parent,
                false
            )

        return HistoryViewHolder(view)
    }

    /*
     * Displays the repair data in the card.
     */
    override fun onBindViewHolder(
        holder: HistoryViewHolder,
        position: Int
    ) {

        // Get the repair record for this position
        val repair = historyList[position]

        // Display device type
        holder.tvDeviceType.text =
            repair.deviceType

        // Display device model
        holder.tvModel.text =
            "Device: ${repair.deviceModel}"

        // Display problem
        holder.tvProblem.text =
            "Problem: ${repair.problem}"

        // Display repair date
        holder.tvDate.text =
            "Repair Date: ${repair.preferredDate}"

        // Display current status
        holder.tvStatus.text =
            repair.status
    }

    /*
     * Returns the number of repair records.
     */
    override fun getItemCount(): Int {
        return historyList.size
    }
}