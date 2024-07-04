package com.example.shortformandroid.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.PostPhotoAdapter
import com.example.shortformandroid.databinding.FragmentPostBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.util.RequestPermissionClass
import com.example.shortformandroid.view.activity.MainActivity

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var parentActivity: MainActivity

    private val postList = ArrayList<DataModel.Post>()
    private val postPhotoAdapter by lazy { PostPhotoAdapter(requireContext(), postList) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        binding.postPhotoRv.adapter = postPhotoAdapter

        val perm = RequestPermissionClass(parentActivity)
        if (!perm.isLocationPermitted()) perm.requestStorage()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postTopPhotoBtn.isActivated = true

        addPhoto()

        binding.postTopAlbumBtn.setOnClickListener {
            binding.postTopPhotoBtn.isActivated = false
            binding.postTopAlbumBtn.isActivated = true
            addPhoto()
        }
        binding.postTopPhotoBtn.setOnClickListener {
            binding.postTopPhotoBtn.isActivated = true
            binding.postTopAlbumBtn.isActivated = false
            addPhoto()
        }
    }

    private fun addPhoto() {
        val imgArray = listOf<Int?>(R.drawable.feed1,R.drawable.feed2,R.drawable.feed3,
            R.drawable.feed4,R.drawable.feed5,R.drawable.feed6,
            R.drawable.feed7,R.drawable.feed8,R.drawable.feed9)

        postList.clear()
        repeat(12) {
            postList.add(DataModel.Post(imgArray.random(),false))
        }

        postPhotoAdapter.notifyDataSetChanged()
    }
}