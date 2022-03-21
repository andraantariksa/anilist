package id.shaderboi.anilist.ui.common.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.shaderboi.anilist.core.domain.model.anime.Anime
import id.shaderboi.anilist.databinding.ListItemAnimeBinding

class AnimeAdapter(
    private val animes: List<Anime>
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
            root.setBackgroundColor(Color.parseColor("#f6f6f6"))
            imageViewCover.load(anime.data.images.jpg.smallImageUrl)
            textViewTitle.text = anime.data.title
            textViewGenre.text = anime.data.genres.joinToString { it.name }
            textViewDescription.text = anime.data.synopsis
        }
    }

    override fun getItemCount(): Int = animes.size
}