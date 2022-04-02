package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.card_post.*
import kotlinx.android.synthetic.main.card_post.view.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.view.*
import ru.netology.nmedia.adapter.PostCallback
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.enumeration.RetryType
import ru.netology.nmedia.R.string.new_posts


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        val bundle = Bundle()

        val adapter = PostsAdapter(object : PostCallback {

            override fun onLike(post: Post) {
                if (!post.likedByMe) viewModel.likeById(post.id) else viewModel.unlikeById(post.id)
            }


            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }

                val shareIntent = Intent.createChooser(intent, getString(R.string.share_post))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)


            }

            override fun edit(post: Post) {
                viewModel.edit(post)
                bundle.putString("content", post.content)
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, bundle)
            }

            override fun onVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                val videoIntent = Intent.createChooser(intent, getString(R.string.video_chooser))
                startActivity(videoIntent)
            }

            override fun onPost(post: Post) {
                val id = post.id
                bundle.putLong("id", id)

                findNavController().navigate(R.id.action_feedFragment_to_singlePost, bundle)
            }

        })

        binding.list.adapter = adapter
        binding.list.animation = null // отключаем анимацию


        viewModel.data.observe(viewLifecycleOwner, { state ->
            val listComparison = adapter.itemCount < state.posts.size
            adapter.submitList(state.posts) {
                if (listComparison) binding.list.scrollToPosition(0)
            }
            binding.emptyText.isVisible = state.empty
        } )

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry_loading) {
                        when (state.retryType) {
                            RetryType.SAVE -> viewModel.retrySave(state.retryPost)
                            RetryType.REMOVE -> viewModel.removeById(state.retryId)
                            RetryType.LIKE -> viewModel.likeById(state.retryId)
                            RetryType.UNLIKE -> viewModel.unlikeById(state.retryId)
                            else -> viewModel.refreshPosts()
                        }
                    }
                    .show()
            }
        }

        viewModel.newerCount.observe(viewLifecycleOwner) {
            with(binding.newEntry) {
                if (it > 0) {
                    text = "${getString(new_posts)} $it"
                    visibility = View.VISIBLE
                }
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshPosts()
            binding.newEntry.visibility = View.GONE
        }

        binding.newEntry.setOnClickListener {
            binding.newEntry.visibility = View.GONE
            viewModel.loadNewPosts()
        }
//
//        binding.retryButton.setOnClickListener {
//            viewModel.loadPosts()
//        }


        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }

        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        binding.swiperefresh.setOnRefreshListener{
            viewModel.loadPosts()
            swiperefresh.isRefreshing = false
        }


        return binding.root
    }

}




