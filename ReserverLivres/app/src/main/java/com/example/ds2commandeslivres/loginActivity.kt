package com.example.ds2commandeslivres

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val valider = findViewById<Button>(R.id.Valider)
        val email =findViewById<EditText>(R.id.email)
        val mdp =findViewById<EditText>(R.id.mdp)

        valider.setOnClickListener{

            when {
                TextUtils.isEmpty(email.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(this@loginActivity,"Veuillez saisir votre email.",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(mdp.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(this@loginActivity,"Veuillez saisir votre mot de passe.",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val emaildb = email.text.toString().trim{ it <= ' '}
                    val mdpdb = mdp.text.toString().trim{ it <= ' '}
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emaildb, mdpdb)
                        .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(this@loginActivity,"Connexion réussite !",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@loginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this@loginActivity,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()

                        }
                        }
                    }
            }
        }
    }
}