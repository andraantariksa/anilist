package id.shaderboi.anilist.ui.common.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.shaderboi.anilist.R
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.core.domain.model.anime_search.Data
import id.shaderboi.anilist.databinding.ListItemAnimeBinding
import id.shaderboi.anilist.ui.home.HomeFragmentDirections
import timber.log.Timber

class AnimeAdapter(
    private val animes: List<Data>,
    private val navController: NavController
) :
    RecyclerView.Adapter<AnimeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: ListItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            ListItemAnimeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val anime = animes[position]
        holder.binding.apply {
            root.setOnClickListener {
                val action = HomeFragmentDirections
                    .actionNavigationHomeMainToNavigationHomeAnime(anime.malId)
                navController.navigate(action)
            }
            root.setBackgroundColor(Color.parseColor("#f6f6f6"))
            imageViewCover.load(anime.images.jpg.largeImageUrl)
            textViewTitle.text = anime.title
            textViewGenre.text = anime.genres.joinToString { it.name }
            textViewDescription.text = anime.synopsis
        }
    }

    override fun getItemCount(): Int = animes.size
}