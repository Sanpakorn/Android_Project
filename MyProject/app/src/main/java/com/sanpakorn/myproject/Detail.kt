package com.sanpakorn.myproject


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.layout_listview.*
import org.json.JSONObject



/**
 * A simple [Fragment] subclass.
 */
class Detail : Fragment() {
        var PhotoURL : String = ""
        var Name : String = ""

        fun newInstance(url: String,name : String): Detail {

            val profile = Detail()
            val bundle = Bundle()
            bundle.putString("PhotoURL", url)
            bundle.putString("Name", name)
            profile.setArguments(bundle)

            return profile
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val view = inflater.inflate(R.layout.fragment_detail, container, false)

            val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
            val tvName = view.findViewById(R.id.tv_name) as TextView
            val login_button2 = view.findViewById(R.id.login_button2) as Button
            val login_button3 = view.findViewById(R.id.login_button3) as Button

            val jsonString : String = loadJsonFromAsset("netflix.json", activity!!.baseContext).toString()
            val json = JSONObject(jsonString)
            val jsonArray = json.getJSONArray("netflix")

            val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)

            val mRootRef = FirebaseDatabase.getInstance().getReference()
            val mUsersRef = mRootRef.child("netflix")


            //ตั้งค่า Layout
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
            recyclerView.layoutManager = layoutManager

            //ตั้งค่า Adapter
            val adapter = MyRecyclerAdapter(activity!!.baseContext,activity!!.supportFragmentManager,jsonArray)
            recyclerView.adapter = adapter

            Glide.with(activity!!.baseContext)
                .load(PhotoURL)
                .into(ivProfilePicture)

            tvName.setText(Name)

            login_button2.setOnClickListener{


                val builder = AlertDialog.Builder(this.context)
                builder.setMessage("ต้องการออกจากระบบใช่หรือไม่?")
                builder.setPositiveButton("ใช่") { dialog, id ->
                    Toast.makeText(
                        this.context,
                        "ขอบคุณครับ", Toast.LENGTH_SHORT
                    ).show()
                    LoginManager.getInstance().logOut()
                    activity!!.supportFragmentManager.popBackStack()
                }
                builder.setNegativeButton("ไม่ใช่",
                    DialogInterface.OnClickListener{ dialog, which ->
                        dialog.dismiss();
                    })

                builder.show()

            }

            login_button3.setOnClickListener {
                mUsersRef.child("SpongBob").setValue("Pattrick")
                Toast.makeText(
                    this.context,
                    "Add data complete", Toast.LENGTH_SHORT
                ).show()
            }
            // Inflate the layout for this fragment

            return view

        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            

            val bundle = arguments
            if (bundle != null) {
                PhotoURL = bundle.getString("PhotoURL").toString()
                Name = bundle.getString("Name").toString()

            }

        }

    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    }

