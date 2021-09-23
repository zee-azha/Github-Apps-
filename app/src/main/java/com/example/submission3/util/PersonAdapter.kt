package com.example.submission3.util


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submission3.data.Person
import com.example.submission3.databinding.PersonItemBinding



class PersonAdapter:RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    private val list= ArrayList<Person>()

    private var onItemClickCallback: OnItemClickCallback? =null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PersonViewHolder {
        val binding = PersonItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class PersonViewHolder(private val binding: PersonItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(person: Person){
            with(binding){
                Glide.with(itemView.context)
                    .load(person.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgPerson)
                tvName.text = person.login
                itemView.setOnClickListener {onItemClickCallback?.onItemClicked(person)
                }
            }
        }
    }

    fun setList(persons: ArrayList<Person>){
        list.clear()
        list.addAll(persons)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Person)
    }
}