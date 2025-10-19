package com.zagirlek.remote.di

import com.zagirlek.remote.autocomplete.source.RemoteAutocompleteCitySource
import com.zagirlek.remote.extractor.RemoteNewsExtractorSource
import com.zagirlek.remote.news.source.RemoteNewsSource

interface CoreRemoteModule {
    val remoteNewsSource: RemoteNewsSource
    val remoteNewsExtractorSource: RemoteNewsExtractorSource
    val remoteAutocompleteSource: RemoteAutocompleteCitySource
}