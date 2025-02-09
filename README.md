[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/M0kyOMLZ)

# JuweAppka

## Autorzy

- Błażej Wilkoławski (@Buarzej on GitHub)
- Paulina Kubera (@c00kiepreferences on GitHub)

## Opis

JuweAppka to aplikacja mobilna przeznaczona na system Android (i w najbliższej przyszłości na iOS)
będąca aplikacją festiwalu muzycznego "Juwenalia UW".

## Funkcje

- **Feed z aktualnościami**
    - Aktualne newsy i ogłoszeni artyści
    - Pobieranie danych z serwera (automatycznie przy otworzeniu aplikacji lub na żądanie poprzez
      odświeżenie feedu) i zapisywanie ich do lokalnego cache'a
    - Harmonogram koncertów
- **Mapa festiwalu**
    - Automatyczna lokalizacja użytkownika przez GPS
    - Pinezki na terenie festiwalu, wraz z możliwością ich kliknięcia w celu uzyskania większej
      liczby szczegółów
    - Filtrowanie i wyszukiwanie punktów na mapie
- **Katalog biletów**
    - Wgrywanie własnych plików biletów (dowolny plik w formacie PNG) do aplikacji
    - Otwieranie i usuwanie wgranych biletów
- **UI/UX**
    - Pełna obsługa trybu jasnego i ciemnego oraz adaptacyjnych kolorów na Androidzie
    - Adaptacyjny wygląd UI obsługujący większe i mniejsze ekrany
    - Podstawowe testy UI
- **Architektura aplikacji**
    - Model MVVM oraz najlepsze praktyki dot. architektury aplikacji w Kotlinie/Compose
      Multiplatform
    - Aplikacja była testowana wyłącznie na Androidzie. Pierwotnie planowana obsługa iOS na ten
      moment nie działa z powodu braku możliwości sprzętowych do jej testowania (problemy z XCode).
      Mimo to, wszystkie używane przez nas biblioteki obsługują obie platformy, co powinno znacząco
      ułatwić wprowadzenie obsługi iOS w niedalekiej przyszłości. Cały kod powstawał z myślą o
      łatwym wprowadzeniu obsługi urządzeń z iOS-em.

Dane wgrane na ten moment do aplikacji są w większości placeholderowe, mające na celu jedynie
zaprezentowanie wszystkich funkcjonalności.

## Pierwotny plan

W pierwszej części zamierzamy zaprojektować i zaimplementować pełny UI aplikacji (wspólny dla obu
systemów, Androida i iOS-a). Znacząca większość funkcjonalności nie będzie jeszcze działać, a
zawartość UI będzie bazowała na tymczasowych placeholderach imitujących prawdziwe treści.

W drugiej części zamierzamy zaimplementować następujące funkcjonalności:

- harmonogram koncertów i innych wydarzeń (✅) towarzyszących w dniach festiwalu (❌)
- lista aktualnie ogłoszonych artystów (✅) oraz możliwość odsłuchania ich najpopularniejszych
  utworów bezpośrednio w aplikacji (❌)
- funkcja kupna biletów w serwisie zewnętrznym (✅) i możliwość wgrania ich do aplikacji w postaci
  pliku PDF (✅ — ale jako plik PNG)
- interaktywna mapa terenu festiwalowego (✅)
- obsługa powiadomień push z najnowszymi ogłoszeniami (❌)

## Biblioteki

- Kotlin Multiplatform (https://kotlinlang.org/docs/multiplatform.html)
- Compose Multiplatform (https://www.jetbrains.com/compose-multiplatform/)
- KotlinX Serialization (https://github.com/Kotlin/kotlinx.serialization)
- Koin (https://insert-koin.io/)
- MapLibre Compose (https://sargunv.github.io/maplibre-compose/)
- Compass (https://compass.jordond.dev/docs)
- FileKit (https://github.com/vinceglb/FileKit)
- Ktor (https://ktor.io/)
