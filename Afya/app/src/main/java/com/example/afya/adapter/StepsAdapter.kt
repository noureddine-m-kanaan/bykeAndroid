package com.example.afya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.R
import com.example.afya.data.model.Step

class StepsAdapter(private val steps: List<Step>) : RecyclerView.Adapter<StepsAdapter.StepViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]
        holder.bind(step)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    inner class StepViewHolder(stepView: View) : RecyclerView.ViewHolder(stepView) {
        private val stepTextView: TextView = stepView.findViewById(R.id.tv_step)

        fun bind(step: Step) {
            stepTextView.text = step.nom_etape
        }
    }
}