package com.adnanjalloul.androidtest_11_2020.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(
    private val users: ArrayList<User>
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var listener: IUserItemSelectedListener? = null

    fun setOnUserItemSelectedListener(listenerI: IUserItemSelectedListener) {
        this.listener = listenerI

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textView_id.text = user.id.toString()
            itemView.textView_name.text = user.name
            itemView.textView_email.text = user.email
            itemView.textView_phone.text = user.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.linearLayout_userInfo.setOnClickListener {
            listener?.onItemClicked(position, users[position])
        }
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }

    interface IUserItemSelectedListener {
        fun onItemClicked(position: Int, user: User)
    }
}