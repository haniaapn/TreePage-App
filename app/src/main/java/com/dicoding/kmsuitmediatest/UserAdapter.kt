package com.dicoding.kmsuitmediatest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.kmsuitmediatest.retrofit.DataItem
import com.google.android.material.imageview.ShapeableImageView

class UserAdapter(private val listUser: List<DataItem>) : RecyclerView.Adapter<UserAdapter.ViewHolderUser>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolderUser(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val user = listUser[position]

        holder.bind(user)
    }

    inner class ViewHolderUser(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPhoto: ShapeableImageView = view.findViewById(R.id.img_item_photo)
        private val tvName: TextView = view.findViewById(R.id.tv_name)
        private val tvEmail: TextView = view.findViewById(R.id.tv_email)

        fun bind(user: DataItem) {
            val fullName = "${user.firstName} ${user.lastName}"
            Glide.with(itemView)
                .load(user.avatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgPhoto)
            tvName.text = fullName
            tvEmail.text = user.email

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SecondScreen::class.java)
                intent.putExtra("full_name", fullName)
                itemView.context.startActivity(intent)
            }
        }
    }
}
