package com.example.ds2commandeslivres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class detailsdeselements : AppCompatActivity() {

    lateinit var tvfirstName: TextView
    lateinit var tvlastName: TextView
    lateinit var tvage: TextView
    lateinit var tvimage: ImageView
    lateinit var btnAddToFav: Button
    lateinit var txtBookDesc: TextView
    lateinit var toolbar: Toolbar

    private lateinit var dbref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailsdeselements)

        tvfirstName = findViewById(R.id.tvfirstName)
        tvlastName = findViewById(R.id.tvlastName)
        tvage = findViewById(R.id.tvage)
        tvimage = findViewById(R.id.tvimage)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnAddToFav)

        val fn = intent.getStringExtra("1")
        val ln = intent.getStringExtra("2")
        val age = intent.getStringExtra("3")
        val dt = intent.getStringExtra("4")
        val img = intent.getStringExtra("5")

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Détails des livres"

        tvfirstName .setText("$fn")
        tvlastName.setText("$ln")
        tvage.setText("$age")
        txtBookDesc.setText("$dt")
        Glide.with(this)
            .load("$img")
            .into(tvimage)

        btnAddToFav.setOnClickListener {
            dbref = FirebaseDatabase.getInstance().reference
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Voulez vous réserver le livre " + tvfirstName.text.toString() + " ?")

            alert.setPositiveButton("Confirmer") { dialog, positiveButton -> val reservElmts = ReservElmts.create()
                reservElmts.nomLivreResrv = tvfirstName.text.toString()
                val nvlivre = dbref.child(LivreAdapterRecycler.Constants.FIREBASE_ITEM).child(
                    FirebaseAuth.getInstance().currentUser!!.uid).push()
                reservElmts.livreId = nvlivre.key

                nvlivre.setValue(reservElmts)
                dialog.dismiss()
                Toast.makeText(this, "Livre réservé avec l'ID " + reservElmts.livreId, Toast.LENGTH_SHORT).show() }
            alert.show()
        }
    }
}

