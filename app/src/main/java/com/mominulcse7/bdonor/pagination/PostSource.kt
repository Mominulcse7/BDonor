package com.mominulcse7.bdonor.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mominulcse7.bdonor.model.PostModel
import com.mominulcse7.bdonor.network.ApiService
import javax.inject.Inject

class PostSource @Inject constructor(private val api: ApiService) : PagingSource<Int, PostModel>() {
    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        return state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        val position = params.key ?: 1

        var prevKey: Int? = null
        if (position != 1)
            prevKey = position - 1

        val nextKey = position + 1

        return try {
            val response = api.getPostList(position)
            LoadResult.Page(data = response.list, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}