package com.guilherme.exerciciomenukotlin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.activity.addCallback
import com.google.android.material.navigation.NavigationView
import com.guilherme.exerciciomenukotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.systemBars())
        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        fragmentManager = supportFragmentManager

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_cart -> openFragment(CartFragment())
                R.id.botton_discount -> openFragment(DescontosFragment())
                R.id.bottom_menu -> openFragment(MenuFragment())
            }
            true
        }

        openFragment(HomeFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Adicionar", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(this){
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_carrinho -> openFragment(CartFragment())
            R.id.nav_datas -> openFragment(CalendarFragment())
            R.id.nav_nacionais -> openFragment(NacionalFragment())
            R.id.nav_oferta -> Toast.makeText(this, "Ofertas", Toast.LENGTH_SHORT).show()
            R.id.nav_populares -> Toast.makeText(this, "Populares", Toast.LENGTH_SHORT).show()
            R.id.nav_internacionais -> Toast.makeText(this, "Internacionais", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}