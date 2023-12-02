package mohammad.toriq.mymovies.data.source.remote

import mohammad.toriq.mymovies.data.source.remote.dto.APIService
import okhttp3.Interceptor
import okhttp3.Response

/***
 * Created By Mohammad Toriq on 30/11/2023
 */
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url
            .newBuilder()
            .build()
        val request = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Authorization","bearer "+APIService.TOKEN)
            .build()
        return chain.proceed(request)
    }
}