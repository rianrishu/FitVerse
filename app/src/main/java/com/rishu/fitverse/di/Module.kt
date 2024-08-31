package com.rishu.fitverse.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rishu.fitverse.BuildConfig
import com.rishu.fitverse.data.api.local.datasource.DataStorePreferencesDataSource
import com.rishu.fitverse.data.api.local.datasource.PreferencesDataSource
import com.rishu.fitverse.data.api.local.datasource.SharedPreferencesDataSource
import com.rishu.fitverse.data.api.remote.harperdb.Api
import com.rishu.fitverse.data.api.remote.harperdb.AuthInterceptor
import com.rishu.fitverse.data.model.mapper.UserMapper
import com.rishu.fitverse.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun providesSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("FitVerseSharedPref", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun providesFirebaseStorage() = Firebase.storage.reference

    @Provides
    @Singleton
    @Named("sharedPref")
    fun providesSharedPreferencesDataSource(sharedPreferences: SharedPreferences): PreferencesDataSource =
        SharedPreferencesDataSource(sharedPreferences)

    @Provides
    @Singleton
    @Named("prefDataStore")
    fun providesDataStorePreferencesDataSource(dataStore: DataStore<Preferences>): PreferencesDataSource =
        DataStorePreferencesDataSource(dataStore)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor() = AuthInterceptor()

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        callFactory: Call.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .callFactory(callFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun providesUserMapper(): UserMapper = UserMapper()

    @Provides
    fun providesContext(@ApplicationContext context: Context): Context = context
}
