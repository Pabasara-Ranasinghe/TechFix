package com.example.techfix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyBookingAdapter(
    private val bookingList: MutableList<MyBooking>
) : RecyclerView.Adapter<MyBookingAdapter.BookingViewHolder>() {


    class BookingViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val tvDevice =
            itemView.findViewById<TextView>(
                R.id.tvBookingDevice
            )

        val tvModel =
            itemView.findViewById<TextView>(
                R.id.tvBookingModel
            )

        val tvProblem =
            itemView.findViewById<TextView>(
                R.id.tvBookingProblem
            )

        val tvDate =
            itemView.findViewById<TextView>(
                R.id.tvBookingDate
            )

        val tvStatus =
            itemView.findViewById<TextView>(
                R.id.tvBookingStatus
            )
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_my_booking,
                    parent,
                    false
                )

        return BookingViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: BookingViewHolder,
        position: Int
    ) {

        val booking =
            bookingList[position]

        holder.tvDevice.text =
            booking.deviceType

        holder.tvModel.text =
            "Model: ${booking.deviceModel}"

        holder.tvProblem.text =
            "Problem: ${booking.problem}"

        holder.tvDate.text =
            "Preferred Date: ${booking.preferredDate}"

        holder.tvStatus.text =
            "Status: ${booking.status}"
    }


    override fun getItemCount(): Int {

        return bookingList.size
    }
}