package com.mominulcse7.bdonor.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mominulcse7.bdonor.databinding.FragmentPostsBinding
import com.mominulcse7.bdonor.viewModel.HomeViewModel
import com.mominulcse7.bdonor.views.adapters.PostA
import com.mominulcse7.bdonor.views.adapters.base.ClickListener
import kotlinx.coroutines.launch

class PostsF : Fragment(), ClickListener {

    private lateinit var binding: FragmentPostsBinding

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var adapter: PostA
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(lif: LayoutInflater, vg: ViewGroup?, sis: Bundle?): View {
        binding = FragmentPostsBinding.inflate(lif, vg, false)
        initiateView()
        observeViewModel()
        setList()
        return binding.root
    }

    private fun initiateView() {

        adapter = PostA()
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        adapter.setAdapterClickListener(this)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }

        }

    }

    private fun observeViewModel() {
        viewModel.postList.observe(viewLifecycleOwner) { data ->
            lifecycleScope.launch {
                adapter.submitData(data)
            }
        }
    }

    private fun setList() {

    }

    override fun onAdapterItemClick(itemView: View, any: Any, position: Int, button: String) {

    }

}