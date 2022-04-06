package id.shaderboi.anilist.ui.common.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.shaderboi.anilist.R
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData
import id.shaderboi.anilist.databinding.ItemAnimeBinding

class AnimePagingAdapter(
    private val onClickAnime: (AnimeData, Int, View) -> Unit,
    diffCallback: DiffUtil.ItemCallback<AnimeData>
) :
    PagingDataAdapter<AnimeData, AnimePagingAdapter.RecipeViewHolder>(diffCallback) {

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
        getItem(position)?.let { anime ->
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
                textViewGenre.text = anime.genres.joinToString(", ") { it.name }
                textViewDescription.text = anime.synopsis
            }
        }
    }
}

