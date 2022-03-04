package com.example.ds2commandeslivres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth



class ProfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)


        val email = view.findViewById<TextView>(R.id.txtemail)
        val id = view.findViewById<TextView>(R.id.txtid)
        val emailactuel = FirebaseAuth.getInstance().currentUser!!.email
        val idactuel = FirebaseAuth.getInstance().currentUser!!.uid
        email.text = "Email : $emailactuel"
        id.text = "Id : $idactuel"
        return view
    }

}
