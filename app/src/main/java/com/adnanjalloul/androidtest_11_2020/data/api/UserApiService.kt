import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import io.reactivex.Single

interface UserApiService {
    fun getUsers(): Single<List<User>>

    fun getPhotoAlbum(userId: Int): Single<List<Photo>>
}
