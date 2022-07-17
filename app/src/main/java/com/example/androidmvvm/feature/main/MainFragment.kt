package com.example.androidmvvm.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.androidmvvm.core.error.Failure
import com.example.androidmvvm.core.extension.toast
import com.example.androidmvvm.core.livedata.autoCleared
import com.example.androidmvvm.core.platform.BaseFragment
import com.example.androidmvvm.databinding.FragmentMainBinding
import com.example.androidmvvm.feature.main.list.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    private var repoAdapter by autoCleared<RepoAdapter>()

    override val viewModel: MainViewModel by viewModels()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViewModel() = with(viewModel) {
        repoList.observe(viewLifecycleOwner) { repoList ->
            hideRefreshing()
            repoAdapter.submitList(repoList)
        }
    }

    private fun hideRefreshing() {
        viewBinding.swrRepos.isRefreshing = false
    }

    private fun initViews() = with(viewBinding) {
        buildRepoList()
        buildSwipeToRefresh()
    }

    private fun buildSwipeToRefresh() = with(viewBinding) {
        swrRepos.setOnRefreshListener {
            viewModel.searchRepos("Android", isRefreshing = true)
        }
    }

    private fun buildRepoList() = with(viewBinding) {
        repoAdapter = RepoAdapter(
            onItemClickAction = { repo ->
                context?.toast(repo.id.toString())
            },
        )
        rvRepos.adapter = repoAdapter
    }

    override fun onError(failure: Failure) {
        super.onError(failure)
        hideRefreshing()
    }
}