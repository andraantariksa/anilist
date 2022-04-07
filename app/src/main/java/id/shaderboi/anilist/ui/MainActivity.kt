package id.shaderboi.anilist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.anilist.core.util.Theme
import id.shaderboi.anilist.core.util.preference.AppPreferenceStore
import id.shaderboi.anilist.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var appPreferenceStore: AppPreferenceStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            appPreferenceStore.preference().collectLatest { appSettings ->
                val themeAppCompat = when(appSettings.theme) {
                    Theme.Default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    Theme.Light -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(themeAppCompat)
            }
        }

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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}