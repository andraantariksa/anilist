package id.shaderboi.anilist.ui.util

sealed class ResourceState<T, E> {
    class Error<T, E>(val error: E): ResourceState<T, E>()
    class Loaded<T, E>(val data: T): ResourceState<T, E>()
    class Loading<T, E>(): ResourceState<T, E>()
}