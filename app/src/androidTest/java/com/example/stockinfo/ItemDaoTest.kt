package com.example.stockinfo

 import android.content.Context
 import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stockinfo.db.StockInfo
import com.example.stockinfo.db.StockInfoDao
import com.example.stockinfo.db.StockInfoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var stockInfoDao: StockInfoDao
    private lateinit var stockInfoDatabase: StockInfoDatabase

//    data class StockInfo(
//        @PrimaryKey(autoGenerate = true)
//        val id: Int = 0,
//        val stockID: Long,
//        val stockName: String
//    )
    private var item1 = StockInfo(1, 2330, "台積電")
    private var item2 = StockInfo(2, 2885, "元大金")


    //在這個函式中，您要使用記憶體內資料庫，但不將該資料庫保存在磁碟上。
    // 做法是使用 inMemoryDatabaseBuilder() 函式，
    // 這是因為相關資訊不必保存，而是要在程序終止時刪除。
    // 此外，您也會透過 .allowMainThreadQueries() 在主執行緒中執行 DAO 查詢，專門用於測試。
    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()

        stockInfoDatabase = Room.inMemoryDatabaseBuilder(context, StockInfoDatabase::class.java)
        .allowMainThreadQueries()
        .build()

        stockInfoDao = stockInfoDatabase.stockInfoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        stockInfoDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertItemToDb() = runBlocking()
    {
        addOneItemToDb()
        val allItems = stockInfoDao.getAllStockInfos().first()
        assertEquals(allItems[0], item1)
    }

    //測試insert
    private suspend fun addOneItemToDb(){
        stockInfoDao.insert(item1)
    }

    private suspend fun addTwoItemToDb(){
        stockInfoDao.insert(item1)
        stockInfoDao.insert(item2)
    }

    //測試update
    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updateItemInDb() = runBlocking()
    {
        addTwoItemToDb()
        stockInfoDao.update(StockInfo(1, 2330, "台積電_改"))
        stockInfoDao.update(StockInfo(2, 2885, "元大金_改"))
        val allItems = stockInfoDao.getAllStockInfos().first()
        assertEquals(allItems[0], StockInfo(1, 2330, "台積電_改"))
        assertEquals(allItems[1], StockInfo(2, 2885, "元大金_改"))
    }

    //測試delete


    //測試insert

}