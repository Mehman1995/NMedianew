package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import ru.netology.nmedia.entity.DraftEntity


import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insert(post: List<PostEntity>)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun update(id: Long, content: String)

    suspend fun save(post: PostEntity) = if (post.id == 0L) insert(post) else update(post.id, post.content)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    suspend fun removeById(id: Long)

 @Query(
        """
           UPDATE PostEntity SET
               `likesCount` = `likesCount` + 1,
               likedByMe = 1
           WHERE id = :id AND likedByMe = 0;
        """,
    )
   suspend fun likedById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               `likesCount` = `likesCount` - 1,
               likedByMe = 0
           WHERE id = :id AND likedByMe = 1;
        """,
    )
    suspend fun unlikedById(id: Long)

}