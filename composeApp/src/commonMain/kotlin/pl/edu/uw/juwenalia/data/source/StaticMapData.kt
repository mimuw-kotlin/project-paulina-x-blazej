package pl.edu.uw.juwenalia.data.source

import io.github.dellisd.spatialk.geojson.Position
import pl.edu.uw.juwenalia.data.model.MapPoint
import pl.edu.uw.juwenalia.data.model.MapPointCategory

object StaticMapData {
    const val FESTIVAL_PLACE_LATITUDE = 52.220692
    const val FESTIVAL_PLACE_LONGITUDE = 21.043575
    const val FESTIVAL_PLACE_DEFAULT_ZOOM = 16.2
    const val MAP_POINT_FOCUS_ZOOM = 17.0

    val mapPoints =
        listOf(
            MapPoint(
                id = 0,
                name = "Pizza",
                position = Position(21.042585106672163, 52.221027780636376),
                category = MapPointCategory.FOOD,
                additionalInfo = "W ofercie Margherita, Capriciosa, Pepperoni i wiele innych!"
            ),
            MapPoint(
                id = 1,
                name = "Burrito",
                position = Position(21.042551008161468, 52.22083380788041),
                category = MapPointCategory.FOOD,
                additionalInfo = "Pyszne meksykańskie burrito, również w wersji wegańskiej!"
            ),
            MapPoint(
                id = 2,
                name = "Zapiekanki",
                position = Position(21.04313971699338, 52.221013935659386),
                category = MapPointCategory.FOOD,
                additionalInfo = "Chrupiące zapiekanki robione w 5 minut na Twoich oczach!"
            ),
            MapPoint(
                id = 3,
                name = "Churros",
                position = Position(21.042880601098886, 52.22102501046206),
                category = MapPointCategory.FOOD,
                additionalInfo = "Masz ochotę na coś słodkiego? Churrosy czekają na Ciebie!"
            ),
            MapPoint(
                id = 4,
                name = "WC męskie",
                position = Position(21.041949808468672, 52.22067484841307),
                category = MapPointCategory.WC,
                additionalInfo = "W tym miejscu znajdziesz toaletę dla mężczyzn."
            ),
            MapPoint(
                id = 5,
                name = "WC damskie",
                position = Position(21.041865602707617, 52.22010196536283),
                category = MapPointCategory.WC,
                additionalInfo = "W tym miejscu znajdziesz toaletę dla kobiet."
            ),
            MapPoint(
                id = 6,
                name = "Żywiec",
                position = Position(21.042846772273975, 52.22045381042847),
                category = MapPointCategory.BEER,
                additionalInfo = "W tym miejscu możesz kupić piwo Żywiec oraz wielorazowy kubeczek."
            ),
            MapPoint(
                id = 7,
                name = "Żywiec",
                position = Position(21.043529282128247, 52.22015515045433),
                category = MapPointCategory.BEER,
                additionalInfo = "W tym miejscu możesz kupić piwo Żywiec oraz wielorazowy kubeczek."
            ),
            MapPoint(
                id = 8,
                name = "Żywiec",
                position = Position(21.042298942495677, 52.220308793940205),
                category = MapPointCategory.BEER,
                additionalInfo = "W tym miejscu możesz kupić piwo Żywiec oraz wielorazowy kubeczek."
            ),
            MapPoint(
                id = 9,
                name = "Woda",
                position = Position(21.042225324104663, 52.219672341289964),
                category = MapPointCategory.WATER,
                additionalInfo = "Pamiętaj o nawodnieniu! W tym miejscu znajdziesz darmową wodę."
            ),
            MapPoint(
                id = 10,
                name = "mBank",
                position = Position(21.04277586215909, 52.219671860059066),
                category = MapPointCategory.EXHIBITORS,
                additionalInfo = "Zapoznaj się z ofertą mBanku!"
            ),
            MapPoint(
                id = 11,
                name = "Red Bull",
                position = Position(21.043086093912166, 52.21966642979993),
                category = MapPointCategory.EXHIBITORS,
                additionalInfo = "Odwiedź stoisko Red Bulla i zgarnij fantastyczne gadżety!"
            ),
            MapPoint(
                id = 12,
                name = "Good Lood",
                position = Position(21.043400757545754, 52.219671860058725),
                category = MapPointCategory.EXHIBITORS,
                additionalInfo = "Tutaj czekają na Ciebie pyszne lody prosto od Good Lood!"
            )
        )
}