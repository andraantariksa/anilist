package id.shaderboi.anilist.ui.common.adapters

import androidx.recyclerview.widget.DiffUtil
import id.shaderboi.anilist.core.domain.model.common.anime.AnimeData

val animeDataDiffUtil = object : DiffUtil.ItemCallback<AnimeData>() {
    override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
        return oldItem.malId == newItem.malId
    }

    override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
        return oldItem == newItem
    }

}