package id.shaderboi.anilist.core.data.data_source.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import id.shaderboi.anilist.core.data.exception.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) {
            throw NoNetworkException()
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return networkCapabilities.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false
            activeNetworkInfo.run {
                return (type == ConnectivityManager.TYPE_WIFI ||
                        type == ConnectivityManager.TYPE_MOBILE)
            }
        }
    }
}