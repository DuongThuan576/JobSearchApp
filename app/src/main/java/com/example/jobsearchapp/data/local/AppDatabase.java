package com.example.jobsearchapp.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.jobsearchapp.data.models.Application;
import com.example.jobsearchapp.data.models.FavoriteJob;
import com.example.jobsearchapp.data.models.Job;
import com.example.jobsearchapp.data.models.User;

@Database(entities = {User.class, Job.class, Application.class, FavoriteJob.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract JobDao jobDao();
    public abstract ApplicationDao applicationDao();
    public abstract FavoriteJobDao favoriteJobDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "jobsearch_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            
            // Lệnh xóa sạch dữ liệu (Bạn có thể xóa dòng này sau khi đã dọn dẹp xong)
            //instance.clearAllTables();
            
            // Nạp lại dữ liệu mẫu mặc định
            instance.seedData();
        }
        return instance;
    }

    private void seedData() {
        if (jobDao().getAllJobs().isEmpty()) {
            jobDao().insertJob(new Job(0, "Senior UX Designer", "$3,500", "Hồ Chí Minh", "Google", "Toàn thời gian", true));
            jobDao().insertJob(new Job(0, "Product Manager", "$2,800", "Hà Nội", "Spotify", "Toàn thời gian", false));
            jobDao().insertJob(new Job(0, "Data Scientist", "$4,000", "Đà Nẵng", "Microsoft", "Toàn thời gian", false));
        }
    }
}