package com.example.ds2commandeslivres


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity , drawerLayout, R.string.ouvrir, R.string.fermer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true

            previousMenuItem = it

            when(it.itemId){
                R.id.accueil -> {
                    openDashboard()

                    drawerLayout.closeDrawers()
                }
                R.id.reservations -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        ReservationsFragment()
                    )
                        .commit()

                    supportActionBar?.title = "Livres réservés"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        ProfilFragment()
                    )
                        .commit()

                    supportActionBar?.title = "Profil"
                    drawerLayout.closeDrawers()
                }
                R.id.Apropos -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        AproposFragment()
                    )
                        .commit()

                    supportActionBar?.title = "A propos"
                    drawerLayout.closeDrawers()
                }
                R.id.deconnexion -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this@MainActivity, loginActivity::class.java)

                    )

                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.frame,
            AcceuilFragment()
        )
        transaction.commit()

        supportActionBar?.title = "Bibliothèque"
        navigationView.setCheckedItem(R.id.accueil)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is AcceuilFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}