package id.shaderboi.anilist.ui.common.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.shaderboi.anilist.R
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.databinding.ItemAnimeBinding
import id.shaderboi.anilist.databinding.ItemGenreBinding
import id.shaderboi.anilist.ui.home.HomeFragmentDirections

class AnimeAdapter(
    private val animes: List<AnimeData>,
    private val navController: NavController,
    private val onClickAnime: (AnimeData, Int, View) -> Unit
) :
    RecyclerView.Adapter<AnimeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            ItemAnimeBinding.inflate(
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
            root.setOnClickListener { view ->
                onClickAnime(anime, position, view)
            }
            root.setBackgroundColor(Color.parseColor("#f6f6f6"))
            imageViewCover.load(anime.images.jpg.largeImageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_image_24)
            }
            textViewTitle.text = anime.title
            textViewType.text = anime.type
            textViewYear.text = anime.year?.toString() ?: "?"
            textViewEpisode.text = "${anime.episodes ?: "?"} ep"
            textViewScore.text = anime.score?.toString() ?: "??.?"
            textViewGenre.text = anime.genres.joinToString(",") { it.name }
            textViewDescription.text = anime.synopsis
        }
    }

    override fun getItemCount(): Int = animes.size
}