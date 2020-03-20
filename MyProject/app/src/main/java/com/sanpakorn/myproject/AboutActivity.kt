package com.sanpakorn.myproject


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 */
class AboutActivity : Fragment() {

    private var nametxt : String = ""
    private var imgtxt : String = ""

    fun newInstance(name: String,img: String): AboutActivity {

        val fragment = AboutActivity()
        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("img", img)
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            nametxt = bundle.getString("name").toString()
            imgtxt = bundle.getString("img").toString()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.about_activity, container, false)
        val imgVi : ImageView = view.findViewById(R.id.about_image)
        val nameTxt : TextView = view.findViewById(R.id.about_name)

        Glide.with(activity!!.baseContext)
            .load(imgtxt)
            .into(imgVi)

        nameTxt.setText(nametxt)


        // Inflate the layout for this fragment

        return view
    }


}
