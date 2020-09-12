package com.anji.babydiary.database.tip

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anji.babydiary.database.tip.TipWithUser
import com.anji.babydiary.database.tip.Tips

@Dao
interface TipsDao {

    @Insert
    fun insert(tip: Tips)

    @Query("SELECT * FROM tbl_tips")
    fun selectAll():LiveData<List<Tips>>


    @Transaction
    @Query("SELECT * FROM tbl_tips")
    fun selectWithUser():List<TipWithUser>

    /*
    @Query("SELECT * " +
            "INNER JOIN tbl_profile AS profile ON tip.user_idx = profile.idx\n" +
            " FROM tbl_tip AS tip ")
    fun selectAllWithProfile():LiveData<List<Tip>>

     */

    @Query("SELECT * FROM tbl_tips WHERE tip_category= :cat")
    fun selectByCategory(cat:String):List<TipWithUser>

    @Query("DELETE FROM tbl_tips")
    fun deleteAll()


    /*
    data class TipUser(
        var idx:Long,
        var text:String,
        var user_idx:Long,
        var user:String,
        var cnt:String,
        var imgDir: String,
        var category:String,
        var user_img:String,
        var user_name:String
    )

     */

}