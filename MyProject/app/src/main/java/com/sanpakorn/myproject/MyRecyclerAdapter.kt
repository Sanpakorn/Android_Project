package com.sanpakorn.myproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

class MyRecyclerAdapter(context: Context,fm: FragmentManager, val dataSource: JSONArray) : RecyclerView.Adapter<MyRecyclerAdapter.Holder>() {

    private val thiscontext: Context = context
    private val fragment: FragmentManager = fm



    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var name: TextView
        lateinit var image: ImageView

        fun Holder(){

            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            name = View.findViewById<View>(R.id.name) as TextView
            image = View.findViewById<View>(R.id.imgV) as ImageView

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recy_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Holder()

        holder.name.setText(dataSource.getJSONObject(position).getString("name").toString())

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        holder.layout.setOnClickListener {

            Toast.makeText(thiscontext, holder.name.text.toString(), Toast.LENGTH_SHORT)
                .show()


            val name:String = dataSource.getJSONObject(position).getString("name").toString()
            val img:String = dataSource.getJSONObject(position).getString("image").toString()

            val item_selected = AboutActivity().newInstance(name,img)
            val transaction : FragmentTransaction = fragment.beginTransaction()
            transaction.replace(R.id.layout, item_selected,"item_selected")
            transaction.addToBackStack("item_selected")
            transaction.commit()
            Toast.makeText(thiscontext,holder.name.text.toString(),Toast.LENGTH_SHORT).show()

        }

    }



}