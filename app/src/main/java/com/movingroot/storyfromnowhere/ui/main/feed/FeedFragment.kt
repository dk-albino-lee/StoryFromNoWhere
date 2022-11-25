package com.movingroot.storyfromnowhere.ui.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.movingroot.storyfromnowhere.BaseApplication
import com.movingroot.storyfromnowhere.databinding.FragmentFeedBinding
import com.movingroot.storyfromnowhere.ui.base.BaseFragment
import com.movingroot.storyfromnowhere.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FeedFragment : BaseFragment() {
    private val binding: FragmentFeedBinding get() = _binding!! as FragmentFeedBinding
    private val viewModel: FeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        bindViewModel()
        setObservers()
        setView()
        loadData()
    }

    override fun bindViewModel() {
        super.bindViewModel()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            (binding.feedRecycler.adapter as PostAdapter).submitList(posts)
        }
    }

    private fun setView() {
        binding.feedRecycler.adapter = PostAdapter()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            if (!isFirstLoaded()) {
                loadPosts()
                return@launch
            }
            BaseApplication.getInstance().getDataStoreModule()
                .storeValue(Constants.DataStoreConst.HAS_BEEN_LOADED, true)
        }
    }

    private suspend fun isFirstLoaded(): Boolean {
        return BaseApplication.getInstance().getDataStoreModule()
            .retrieveBoolean(Constants.DataStoreConst.HAS_BEEN_LOADED)
            .first()
    }

    private fun loadPosts() {
        viewModel.getPosts()
    }
}
