package com.example.androidmvvm.ui.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidmvvm.databinding.FragmentMainBinding
import com.example.androidmvvm.domain.model.repo.RepoModel
import com.example.androidmvvm.domain.util.defaultFalse
import com.example.androidmvvm.domain.util.defaultTrue
import com.example.androidmvvm.ui.feature.main.list.RepoAdapter
import com.example.androidmvvm.ui.platform.BaseFragment
import com.example.androidmvvm.ui.util.extension.toast
import com.example.androidmvvm.ui.util.livedata.autoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var repoAdapter by autoCleared<RepoAdapter>()

    override val viewModel: MainViewModel by viewModel()

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
            buildRepoList(repoList)
        }

        firstRun.observe(viewLifecycleOwner) { isFirstRun ->
            buildFirstRun(isFirstRun.defaultTrue())
        }
    }

    private fun buildFirstRun(isFirstRun: Boolean) {
        if (isFirstRun) {
            context?.toast("First Run!!!")
        }
    }

    private fun buildRepoList(repoList: List<RepoModel>) {
        hideRefreshing()
        repoAdapter.submitList(repoList)
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

    override fun onError(message: String) {
        hideRefreshing()
        super.onError(message)
    }
}