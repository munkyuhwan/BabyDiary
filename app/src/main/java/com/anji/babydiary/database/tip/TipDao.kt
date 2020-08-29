package com.anji.babydiary.database.shopping

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tip:Tip)

    @Query("SELECT * FROM tbl_tip")
    fun selectAll():LiveData<List<Tip>>

    /*
    @Query("SELECT * " +
            "INNER JOIN tbl_profile AS profile ON tip.user_idx = profile.idx\n" +
            " FROM tbl_tip AS tip ")
    fun selectAllWithProfile():LiveData<List<Tip>>

     */


    @Query("DELETE FROM tbl_tip")
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