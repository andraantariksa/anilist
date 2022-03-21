package id.shaderboi.anilist.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.anilist.R
import id.shaderboi.anilist.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navController =
            binding.fragmentContainerViewMain.getFragment<NavHostFragment>().navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
//        setupActionBarWithNavController(
//            navController,
//            AppBarConfiguration(
//                setOf(
//                    R.id.navigation_home,
//                    R.id.navigation_misc
//                )
//            )
//        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.fragmentContainerViewMain.findNavController()
        return navController.popBackStack() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}