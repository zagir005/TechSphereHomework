package com.zagirlek.remote.di

import com.zagirlek.remote.autocomplete.source.MockRemoteAutocompleteCitySource
import com.zagirlek.remote.autocomplete.source.RemoteAutocompleteCitySource
import com.zagirlek.remote.extractor.MockNewsExtractorSource
import com.zagirlek.remote.extractor.RemoteNewsExtractorSource
import com.zagirlek.remote.news.source.MockNewsSource
import com.zagirlek.remote.news.source.RemoteNewsSource

class MockCoreRemoteModule: CoreRemoteModule {
    override val remoteNewsSource: RemoteNewsSource by lazy {
        MockNewsSource()
    }

    override val remoteNewsExtractorSource: RemoteNewsExtractorSource by lazy {
        MockNewsExtractorSource()
    }

    override val remoteAutocompleteSource: RemoteAutocompleteCitySource by lazy {
        MockRemoteAutocompleteCitySource()
    }
}