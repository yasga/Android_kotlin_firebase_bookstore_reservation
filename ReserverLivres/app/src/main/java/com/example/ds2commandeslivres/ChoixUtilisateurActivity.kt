package com.example.ds2commandeslivres

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ChoixUtilisateurActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_utilisateur)
        val prof = findViewById<ImageButton>(R.id.prof)
        val etud = findViewById<ImageButton>(R.id.etud)

        prof.setOnClickListener{
            val intent = Intent(this@ChoixUtilisateurActivity, loginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }
        etud.setOnClickListener{
            val intent = Intent(this@ChoixUtilisateurActivity, loginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }
    }
}