package pl.edu.uw.juwenalia.di

import dev.jordond.compass.geolocation.Geolocator
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.edu.uw.juwenalia.data.repository.DefaultMapPointRepository
import pl.edu.uw.juwenalia.data.repository.MapPointRepository
import pl.edu.uw.juwenalia.presentation.map.MapViewModel

val appModule =
    module {
        singleOf(::DefaultMapPointRepository) { bind<MapPointRepository>() }
        single { Geolocator() }
        viewModelOf(::MapViewModel)
    }