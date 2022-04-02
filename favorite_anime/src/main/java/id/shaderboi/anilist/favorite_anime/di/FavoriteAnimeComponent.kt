package id.shaderboi.anilist.favorite_anime.di

import dagger.Component
import id.shaderboi.anilist.core.di.FavoriteAnimeDynamicFeatureDependencies
import id.shaderboi.anilist.favorite_anime.ui.FavoriteAnimeFragment
import id.shaderboi.anilist.favorite_anime.ui.view_models.FavoriteAnimeViewModel

@Component(dependencies = [FavoriteAnimeDynamicFeatureDependencies::class], modules = [FavoriteModule::class])
interface FavoriteAnimeComponent {
    fun inject(favoriteAnimeFragment: FavoriteAnimeFragment)

//    @Component.Builder
//    interface Builder {
//        fun context(@BindsInstance context: Context): Builder
//        fun appDependencies(favoriteDynamicFeatureDependencies: FavoriteDynamicFeatureDependencies): Builder
//        fun build(): FavoriteComponent
//    }

    @Component.Factory
    interface Factory {
        fun create(favoriteAnimeDynamicFeatureDependencies: FavoriteAnimeDynamicFeatureDependencies): FavoriteAnimeComponent
    }

    fun favoriteAnimeViewModel(): FavoriteAnimeViewModel

//    @Provides
//    fun provideFavoriteViewModel(fragment: Fragment, factory: FavoriteViewModelFactory): FavoriteViewModel =
//        ViewModelProvider(fragment, factory).get(FavoriteViewModel::class.java)
}