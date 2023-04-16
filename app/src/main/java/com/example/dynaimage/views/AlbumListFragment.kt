package com.example.dynaimage.views

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynaimage.AlbumApplication
import com.example.dynaimage.R
import com.example.dynaimage.databinding.FragmentAlbumListBinding
import com.example.dynaimage.model.AlbumModelItem
import com.example.dynaimage.utils.TwoPaneOnBackPressedCallback
import com.example.dynaimage.viewmodel.AlbumListViewModel
import com.example.dynaimage.views.AlbumAdapter.Companion.albums
import java.util.ArrayList
import javax.inject.Inject

class AlbumListFragment : Fragment(), AlbumAdapter.RecordSelectListener {

    private lateinit var binding: FragmentAlbumListBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var currentIndex: Int = 0
    lateinit var adapter: AlbumAdapter

    private val viewModel by lazy {
        viewModelFactory.create(
            AlbumListViewModel::class.java
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbumApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        adapter = AlbumAdapter(this@AlbumListFragment)
        setupRecyclerView(adapter)
        viewModel.checkIfDbIsEmpty()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            TwoPaneOnBackPressedCallback(binding.twoPaneLayout)
        )
        observeData()
    }

    private fun observeData() {
        viewModel.noDataStatusLiveData.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.fetchPhotoAlbum()
            } else {
                callDatabaseToFetchData()
            }
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.albumLiveData.observe(viewLifecycleOwner) {
            viewModel.setProgressVisibility(false)
            appendListToRecyclerView(it)
        }
    }

    private fun callDatabaseToFetchData() {
        viewModel.catchPhotosPageWise(currentIndex)
    }

    private fun setupRecyclerView(aAdapter: AlbumAdapter) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = aAdapter
        }
    }

    override fun onRecordSelect(albumModel: AlbumModelItem) {
        findNavController().navigate(
            R.id.detailedFragment,
            bundleOf("ID" to albumModel.id, "TITLE" to albumModel.title, "URL" to albumModel.url)
        )
    }

    override fun loadMoreDataToRecyclerView(indexPosition: Int) {
        currentIndex = indexPosition
        callDatabaseToFetchData()
    }

    private fun appendListToRecyclerView(lst: List<AlbumModelItem>?) {
        adapter.appendList(lst)
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("ALBUMS", albums as ArrayList<AlbumModelItem>)
    }

    // A method on the Fragment that owns the SlidingPaneLayout,
    // called by the adapter when an item is selected.
    fun openDetails(albumModel: AlbumModelItem) {
        findNavController().navigate(
            // Assume the itemId is the android:id of a destination in
            // the graph.
            R.id.detailedFragment,
            bundleOf("ID" to albumModel.id, "TITLE" to albumModel.title, "URL" to albumModel.url)
        )
        binding.twoPaneLayout.open()
    }
}