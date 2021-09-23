package  com.example.submission3.api



import com.example.submission3.BuildConfig.API_KEY
import com.example.submission3.data.DetailsResponse
import com.example.submission3.data.Person
import com.example.submission3.data.ResponsePerson
import retrofit2.Call
import retrofit2.http.*



interface Api {


    @GET("search/users")
    @Headers("Authorization:$API_KEY")
    fun getOnSearch(
        @Query("q") query: String
       ):Call<ResponsePerson>

    @GET("users/{username}")
    @Headers("Authorization:$API_KEY")
    fun getPersonDetails(
        @Path("username") username: String
    ):Call<DetailsResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization:$API_KEY")
    fun getPersonFollowers(
        @Path("username") username: String
    ):Call<ArrayList<Person>>

    @GET("users/{username}/following")
    @Headers("Authorization:$API_KEY")
    fun getPersonFollowing(
        @Path("username") username: String
    ):Call<ArrayList<Person>>

}