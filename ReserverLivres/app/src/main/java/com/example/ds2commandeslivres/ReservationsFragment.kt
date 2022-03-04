package com.example.ds2commandeslivres

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class ReservationsFragment : Fragment()  {

    private lateinit var dbref : DatabaseReference
    object Constants {
        @JvmStatic val FIREBASE_ITEM: String = "Réservations"
    }


    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: ReservationAdapterRecycler

    private lateinit var livreArrayList : ArrayList<ReservElmts>
    private lateinit var tempArrayList : ArrayList<ReservElmts>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservations, container, false)
        setHasOptionsMenu(true)
        livreArrayList = arrayListOf<ReservElmts>()
        tempArrayList = arrayListOf<ReservElmts>()
        //ajoutee2
        recyclerDashboard = view.findViewById(R.id.reservationList)
        layoutManager = LinearLayoutManager(activity)
        //
        livreArrayList = arrayListOf<ReservElmts>()
        tempArrayList = arrayListOf<ReservElmts>()

        dbref = FirebaseDatabase.getInstance().getReference("Réservations").child(FirebaseAuth.getInstance().currentUser!!.uid)
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                tempArrayList.clear()
                if (snapshot.exists()){
                    livreArrayList.clear()
                    for (userSnapshot in snapshot.children){
                        val livre = userSnapshot.getValue(ReservElmts::class.java)
                        livreArrayList.add(livre!!)
                    }
                    tempArrayList.addAll(livreArrayList)
                    recyclerAdapter = ReservationAdapterRecycler(activity as Context?,tempArrayList)

                    recyclerDashboard.adapter = recyclerAdapter
                    recyclerDashboard.layoutManager = layoutManager
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return view
    }
}