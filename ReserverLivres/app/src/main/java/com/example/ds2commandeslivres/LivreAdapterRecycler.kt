package com.example.ds2commandeslivres


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LivreAdapterRecycler(private val context: Context, private val userList : ArrayList<Livres>) : RecyclerView.Adapter<LivreAdapterRecycler.MyViewHolder>() {
    lateinit var mDatabase: DatabaseReference
    object Constants {
        @JvmStatic val FIREBASE_ITEM: String = "Réservations"
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nomLivre : TextView = itemView.findViewById(R.id.tvnomLivre)
        val nomAuteur : TextView = itemView.findViewById(R.id.tvnomAuteur)
        val dateParution : TextView = itemView.findViewById(R.id.tvdateParution)
        val image : ImageView = itemView.findViewById(R.id.tvimage)
        val llContent: LinearLayout = itemView.findViewById(R.id.llContent)
        val ib = itemView.findViewById<ImageButton>(R.id.ib)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.livres_item,
            parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.nomLivre.text = currentitem.nomLivre
        holder.nomAuteur.text = currentitem.nomAuteur
        holder.dateParution.text = currentitem.dateParution

        Glide.with(context)
            .load(currentitem.image)
            .into(holder.image)

        holder.llContent.setOnClickListener {
            val intent1 = Intent(context,detailsdeselements::class.java)

            intent1.putExtra("1", currentitem.nomLivre.toString())
            intent1.putExtra("2", currentitem.nomAuteur.toString())
            intent1.putExtra("3", currentitem.dateParution.toString())
            intent1.putExtra("4", currentitem.details.toString())
            intent1.putExtra("5", currentitem.image)

            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent1);
        }

        holder.ib.setOnClickListener {
        mDatabase = FirebaseDatabase.getInstance().reference
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Voulez vous réserver le livre " + currentitem.nomLivre + " ?")
            alert.setPositiveButton("Confirmer") { dialog, positiveButton -> val reservElmts = ReservElmts.create()
                reservElmts.nomLivreResrv = currentitem.nomLivre
                val nvlivre = mDatabase.child(Constants.FIREBASE_ITEM).child(FirebaseAuth.getInstance().currentUser!!.uid).push()
                reservElmts.livreId = nvlivre.key
                nvlivre.setValue(reservElmts)
                dialog.dismiss()
                Toast.makeText(context, "Livre réservé avec l'ID " + reservElmts.livreId, Toast.LENGTH_SHORT).show() }
            alert.show()
        }
        }
    override fun getItemCount(): Int {
        return userList.size
    }
}

