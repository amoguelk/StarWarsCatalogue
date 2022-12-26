package com.amog.starwarscatalogue.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amog.starwarscatalogue.R
import com.amog.starwarscatalogue.databinding.CharacterElementBinding
import com.amog.starwarscatalogue.model.Character
import com.amog.starwarscatalogue.view.activities.MainActivity

class Adapter(private val context : Context, private val data : ArrayList<Character>):
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: CharacterElementBinding): RecyclerView.ViewHolder(view.root) {
        val tvName = view.tvName
        val tvHeight = view.tvHeight
        val tvBirthYear = view.tvBirthYear
        val tvGender = view.tvGender
        val ivMore = view.ivMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterElementBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.tvName.text = data[i].name
        holder.tvHeight.text = context.getString(R.string.height_txt, data[i].height)
        holder.tvBirthYear.text = context.getString(R.string.birthYear_txt, data[i].birthYear)
        holder.tvGender.text = context.getString(R.string.gender_txt, data[i].gender)
        holder.ivMore.setOnClickListener {
            if (context is MainActivity) context.menuClick(data[i])
        }
    }

    override fun getItemCount(): Int = data.size
}