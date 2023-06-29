package dev.kevalkanpariya.colorapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kevalkanpariya.colorapp.data.data_source.ColorDatabase
import dev.kevalkanpariya.colorapp.data.repository.ColorRepositoryImpl
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import dev.kevalkanpariya.colorapp.domain.use_case.AddColor
import dev.kevalkanpariya.colorapp.domain.use_case.ColorUseCases
import dev.kevalkanpariya.colorapp.domain.use_case.GetColors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): ColorDatabase {
        return Room.databaseBuilder(
            app,
            ColorDatabase::class.java,
            ColorDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideColorRepository(db: ColorDatabase): ColorRepository {
        return ColorRepositoryImpl(db.noteDao)
    }


    @Provides
    @Singleton
    fun provideAddColorUseCase(repository: ColorRepository): AddColor {
        return AddColor(repository)
    }

    @Provides
    @Singleton
    fun provideGetColorsUseCase(repository: ColorRepository): GetColors {
        return GetColors(repository)
    }


    @Provides
    @Singleton
    fun provideColorUseCases(addColor: AddColor, getColors: GetColors): ColorUseCases {
        return ColorUseCases(
            addColor = addColor,
            getColors = getColors
        )
    }


}