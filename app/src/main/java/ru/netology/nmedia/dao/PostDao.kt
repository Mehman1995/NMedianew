package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.nmedia.entity.DraftEntity


import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun update(id: Long, content: String)

    fun save(post: PostEntity) = if (post.id == 0L) insert(post) else update(post.id, post.content)

    @Query(
        """
            UPDATE PostEntity SET 
                likesCount = likesCount + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 else 1 END
                WHERE id = :id
        """
    )
    fun likedById(id: Long)


    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)


    @Query(
        """
        UPDATE PostEntity SET
        sharesCount = sharesCount + 1,
        share = 1
        WHERE id = :id
    """)
    fun shareById(id: Long)

    @Query("DELETE FROM DraftEntity")
    fun deleteDraft()

    @Insert
    fun insertDraft(draft: DraftEntity?)

    fun saveDraft(draft: String?) {
        if (draft == null) {
            deleteDraft()
        } else {
            var id = 0L
            val countId = ++id // Как сделать, чтобы id был всегда разный TODO
            val draftEntity = DraftEntity(id = countId, content = draft)
            insertDraft(draftEntity)
        }
    }


    @Query("SELECT content FROM DraftEntity ")
    fun getDraft(): String?

}
