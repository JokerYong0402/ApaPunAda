package com.example.apapunada.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apapunada.data.dao.FeedbackDao
import com.example.apapunada.data.dao.FoodDetailsDao
import com.example.apapunada.data.dao.MenuItemDao
import com.example.apapunada.data.dao.NutritionFactsDao
import com.example.apapunada.data.dao.OrderDao
import com.example.apapunada.data.dao.OrderDetailsDao
import com.example.apapunada.data.dao.UserDao
import com.example.apapunada.data.dao.VoucherDao
import com.example.apapunada.data.dao.WaitlistDao
import com.example.apapunada.data.dataclass.Feedback
import com.example.apapunada.data.dataclass.FoodDetails
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.NutritionFacts
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.OrderDetails
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.dataclass.Voucher
import com.example.apapunada.data.dataclass.Waitlist

@Database(
    entities = [
        Feedback::class,
        FoodDetails::class,
        MenuItem::class,
        NutritionFacts::class,
        Order::class,
        OrderDetails::class,
        User::class,
        Voucher::class,
        Waitlist::class
   ],
    version = 7
)
abstract class ApaPunAdaDatabase: RoomDatabase() {

    abstract fun feedbackDao(): FeedbackDao
    abstract fun foodDetailsDao(): FoodDetailsDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun nutritionFactsDao(): NutritionFactsDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailsDao(): OrderDetailsDao
    abstract fun userDao(): UserDao
    abstract fun voucherDao(): VoucherDao
    abstract fun waitlistDao(): WaitlistDao
    companion object {
        @Volatile
        private var Instance: ApaPunAdaDatabase? = null

        fun getDatabase(context: Context): ApaPunAdaDatabase {
            return Instance ?:synchronized(this) {
                Room.databaseBuilder(context, ApaPunAdaDatabase::class.java, "apapunada_database")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}