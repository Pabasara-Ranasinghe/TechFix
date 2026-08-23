package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * AppointmentAdapter
 *
 * This adapter is responsible for:
 * 1. Receiving appointment data.
 * 2. Creating appointment cards.
 * 3. Displaying the appointment information
 *    inside the RecyclerView.
 */

class AppointmentAdapter(
    private val appointments: List<Appointment>
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    /*
     * ViewHolder
     *
     * Holds references to the TextViews
     * inside item_appointment.xml.
     */
    class AppointmentViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val tvDeviceType: TextView =
            itemView.findViewById(R.id.tvAppointmentDeviceType)

        val tvModel: TextView =
            itemView.findViewById(R.id.tvAppointmentModel)

        val tvProblem: TextView =
            itemView.findViewById(R.id.tvAppointmentProblem)

        val tvDate: TextView =
            itemView.findViewById(R.id.tvAppointmentDate)

        val tvStatus: TextView =
            itemView.findViewById(R.id.tvAppointmentStatus)
    }

    /*
     * Creates a new appointment card.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_appointment,
                parent,
                false
            )

        return AppointmentViewHolder(view)
    }

    /*
     * Displays appointment data inside the card.
     */
    override fun onBindViewHolder(
        holder: AppointmentViewHolder,
        position: Int
    ) {

        val appointment = appointments[position]

        holder.tvDeviceType.text =
            appointment.deviceType

        holder.tvModel.text =
            "Device: ${appointment.deviceModel}"

        holder.tvProblem.text =
            "Problem: ${appointment.problem}"

        holder.tvDate.text =
            "Preferred Date: ${appointment.preferredDate}"

        holder.tvStatus.text =
            appointment.status
    }

    /*
     * Returns the number of appointments.
     */
    override fun getItemCount(): Int {
        return appointments.size
    }
}