package com.example.ds2commandeslivres

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ReservationAdapterRecycler(private val context: Context?, private val resList : ArrayList<ReservElmts>) : RecyclerView.Adapter<ReservationAdapterRecycler.MyViewHolder>() {

    private lateinit var mDatabase : DatabaseReference
    object Constants {
        @JvmStatic val FIREBASE_ITEM: String = "Réservations"
    }
    private fun onItemDelete(objectId: String?) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Réservations")
        val itemReference = mDatabase.child(FirebaseAuth.getInstance().currentUser!!.uid).child(objectId!!)
        itemReference.removeValue()
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tvnomLivreresrv: TextView = itemView.findViewById(R.id.tvnomLivreresrv)
        val tvsupprimer = itemView.findViewById<ImageButton>(R.id.tvsupprimer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.reserv_livres,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = resList[position]
        holder.tvnomLivreresrv.text = currentitem.nomLivreResrv
        holder.tvsupprimer.setOnClickListener{
            onItemDelete(currentitem.livreId)
        }
    }

    override fun getItemCount(): Int {
            return resList.size
        }
}

