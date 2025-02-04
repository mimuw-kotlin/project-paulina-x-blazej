package pl.edu.uw.juwenalia.di

import dev.jordond.compass.geolocation.Geolocator
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.edu.uw.juwenalia.data.repository.DefaultMapPointRepository
import pl.edu.uw.juwenalia.data.repository.DefaultNewsRepository
import pl.edu.uw.juwenalia.data.repository.DefaultTicketRepository
import pl.edu.uw.juwenalia.data.repository.MapPointRepository
import pl.edu.uw.juwenalia.data.repository.NewsRepository
import pl.edu.uw.juwenalia.data.repository.TicketRepository
import pl.edu.uw.juwenalia.ui.home.HomeViewModel
import pl.edu.uw.juwenalia.ui.map.MapViewModel
import pl.edu.uw.juwenalia.ui.tickets.TicketsViewModel

val appModule =
    module {
        // Singletons
        single { Geolocator() }

        // Data sources

        // Repositories
        singleOf(::DefaultNewsRepository) { bind<NewsRepository>() }
        singleOf(::DefaultMapPointRepository) { bind<MapPointRepository>() }
        singleOf(::DefaultTicketRepository) { bind<TicketRepository>() }

        // View models
        viewModelOf(::HomeViewModel)
        viewModelOf(::MapViewModel)
        viewModelOf(::TicketsViewModel)
    }