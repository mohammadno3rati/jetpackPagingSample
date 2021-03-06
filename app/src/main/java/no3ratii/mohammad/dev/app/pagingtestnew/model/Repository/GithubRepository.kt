/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no3ratii.mohammad.dev.app.pagingtestnew.model.Repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import no3ratii.mohammad.dev.app.pagingtestnew.data.GithubPagingSource
import no3ratii.mohammad.dev.app.pagingtestnew.model.Repo
import no3ratii.mohammad.dev.app.pagingtestnew.model.connection.api.GithubService

/**
 * Repository class that works with local and remote data sources.
 */
@ExperimentalCoroutinesApi
class GithubRepository(private val service: GithubService) {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        return Pager(
                config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    GithubPagingSource(
                        service,
                        query
                    )
                }
        ).flow
    }

    suspend fun test(): List<Repo> {
        val response = service.searchRepos("apiQuery", 1, NETWORK_PAGE_SIZE)
        return response.items
    }

    companion object {
         const val NETWORK_PAGE_SIZE = 50
    }
}
