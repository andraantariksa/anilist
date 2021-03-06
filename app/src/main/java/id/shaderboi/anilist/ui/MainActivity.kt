package id.shaderboi.anilist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment =
            binding.fragmentContainerViewMain.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        binding.bottomNavigationViewMain.setOnItemReselectedListener { item ->
            val selectedMenuItemNavGraph = navController.graph.findNode(item.itemId) as NavGraph
            navController.popBackStack(selectedMenuItemNavGraph.startDestinationId, false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.fragmentContainerViewMain.findNavController()
        return navController.popBackStack() || super.onSupportNavigateUp()
    }

    override fun onStop() {
        _binding = null
        super.onStop()
    }
}