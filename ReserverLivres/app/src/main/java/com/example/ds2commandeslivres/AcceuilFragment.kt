package com.example.ds2commandeslivres


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import android.view.Menu
import android.widget.ProgressBar
import android.widget.RelativeLayout

import com.google.firebase.database.*
import kotlin.collections.ArrayList


class AcceuilFragment : Fragment() {

    private lateinit var dbref : DatabaseReference
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: LivreAdapterRecycler

    private lateinit var livreArrayList : ArrayList<Livres>
    private lateinit var tempArrayList : ArrayList<Livres>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_acceuil, container, false)
        setHasOptionsMenu(true)
        livreArrayList = arrayListOf<Livres>()
        tempArrayList = arrayListOf<Livres>()

        recyclerDashboard = view.findViewById(R.id.livreList)
        layoutManager = LinearLayoutManager(activity)

        livreArrayList = arrayListOf<Livres>()
        tempArrayList = arrayListOf<Livres>()

        dbref = FirebaseDatabase.getInstance().getReference("Livres")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tempArrayList.clear()
                if (snapshot.exists()){
                    livreArrayList.clear()
                    for (userSnapshot in snapshot.children){
                        val livre = userSnapshot.getValue(Livres::class.java)
                        livreArrayList.add(livre!!)
                    }
                    tempArrayList.addAll(livreArrayList)
                    recyclerAdapter = LivreAdapterRecycler(activity as Context,tempArrayList)
                    recyclerDashboard.adapter = recyclerAdapter
                    recyclerDashboard.layoutManager = layoutManager

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_item,menu)
        val item = menu.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val  searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    livreArrayList.forEach{
                        if(it.nomLivre?.lowercase(Locale.getDefault())?.contains(searchText)!!){
                            tempArrayList.add(it)
                        }
                    }
                    recyclerDashboard.adapter!!.notifyDataSetChanged()
                }else{
                    tempArrayList.clear()
                    tempArrayList.addAll(livreArrayList)
                    recyclerDashboard.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu,inflater)
    }
}