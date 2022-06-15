package id.shaderboi.anilist.ui.common.adapters

import androidx.recyclerview.widget.DiffUtil
import id.shaderboi.anilist.core.domain.model.anime.Anime

val animeDataDiffUtil = object : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.malId == newItem.malId
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }

}