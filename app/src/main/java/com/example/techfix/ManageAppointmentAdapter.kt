package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * ManageAppointmentAdapter
 *
 * Displays customer repair appointments inside
 * the management appointment RecyclerView.
 *
 * Each appointment contains:
 *
 * - Device type
 * - Device model
 * - Problem
 * - Preferred date
 * - Current status
 *
 * The UPDATE STATUS button will be used to
 * change the appointment status.
 */

class ManageAppointmentAdapter(

    // List of appointments
    private val appointmentList: MutableList<Appointment>,

    // Function called when management clicks
    // the UPDATE STATUS button
    private val onUpdateStatusClick:
        (Appointment) -> Unit

) : RecyclerView.Adapter<ManageAppointmentAdapter.AppointmentViewHolder>() {


    // ==========================================
    // VIEW HOLDER
    // ==========================================

    class AppointmentViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        // Device type
        val tvDeviceType: TextView =
            itemView.findViewById(
                R.id.tvManageDeviceType
            )

        // Device model
        val tvDeviceModel: TextView =
            itemView.findViewById(
                R.id.tvManageDeviceModel
            )

        // Problem
        val tvProblem: TextView =
            itemView.findViewById(
                R.id.tvManageProblem
            )

        // Preferred date
        val tvDate: TextView =
            itemView.findViewById(
                R.id.tvManageDate
            )

        // Status
        val tvStatus: TextView =
            itemView.findViewById(
                R.id.tvManageStatus
            )

        // Update status button
        val btnUpdateStatus: Button =
            itemView.findViewById(
                R.id.btnUpdateAppointmentStatus
            )
    }


    // ==========================================
    // CREATE VIEW HOLDER
    // ==========================================

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_manage_appointment,
                    parent,
                    false
                )

        return AppointmentViewHolder(view)
    }


    // ==========================================
    // DISPLAY APPOINTMENT DATA
    // ==========================================

    override fun onBindViewHolder(
        holder: AppointmentViewHolder,
        position: Int
    ) {

        // Get current appointment
        val appointment =
            appointmentList[position]

        // Display device type
        holder.tvDeviceType.text =
            appointment.deviceType

        // Display device model
        holder.tvDeviceModel.text =
            appointment.deviceModel

        // Display problem
        holder.tvProblem.text =
            "Problem: ${appointment.problem}"

        // Display preferred date
        holder.tvDate.text =
            "Preferred Date: ${appointment.preferredDate}"

        // Display status
        holder.tvStatus.text =
            "Status: ${appointment.status}"

        // Update status button
        holder.btnUpdateStatus.setOnClickListener {

            onUpdateStatusClick(
                appointment
            )
        }
    }


    // ==========================================
    // ITEM COUNT
    // ==========================================

    override fun getItemCount(): Int {

        return appointmentList.size
    }
}