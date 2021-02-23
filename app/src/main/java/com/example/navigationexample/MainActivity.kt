package com.example.navigationexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration       //This is being used to make search and home fragment independent or in same level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //This will help Search & Home Fragment to be at same level
        //If want the hambarger icon to appear in toolbar for navigation drawer, we need to pass draw_layout also.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment),
            draw_layout
        )

        setSupportActionBar(toolbar)  //This is the extra line that is required in toolbar, but not required for action bar
        setupActionBarWithNavController(navController, appBarConfiguration)  //By passing appBarConfiguration, we are declaring Search & Home Fragment to be at same level
                                                                            //Now there will be no back navigation in toolbar of search fragment


        //For setting the bottom navigation
        bottom_nav.setupWithNavController(navController)
        //For setting the navigation drawer
        nav_view.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    //In menu if Terms&Condition is clicked, it will navigate to TermsFragment with the help of navgraph global navigate with some our own animations
    //In menu if Settings is clicked, then It will navigate to SettingsFragment normally using onNavDestinationSelected, with hard coded animations

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.termsAndConditions){
             val action = NavGraphDirections.actionGlobalTermsFragment()
             navController.navigate(action)
            true
        }else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //To show the hambarger icon transition work properly in navigation drawer, we need to pass (appBarConfiguration) here
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}