package com.example.shortformandroid.view.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shortformandroid.adapter.PostPhotoAdapter
import com.example.shortformandroid.databinding.FragmentPostBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.util.OnAdapterItemSingleClick
import com.example.shortformandroid.util.RequestPermissionClass
import com.example.shortformandroid.util.RequestPermissionClass.Companion.PERMISSION_CODE_READ_EXTERNAL_STORAGE
import com.example.shortformandroid.view.activity.MainActivity
import kotlinx.coroutines.*

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var parentActivity: MainActivity

    private val postList = ArrayList<DataModel.Post>()
    private val postPhotoAdapter by lazy { PostPhotoAdapter(requireContext(), postList) }

    val perm by lazy { RequestPermissionClass(parentActivity)}

    private var lastIndex = 20

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context

        if (!perm.isStoragePermissionGranted()) perm.requestStorage()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        binding.postPhotoRv.adapter = postPhotoAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postTopPhotoBtn.isActivated = true

        postPhotoAdapter.setOnMoreClickListener(object : OnAdapterItemSingleClick() {
            override fun onSingleClick(v: View?, position: Int) {
                Log.i("testtest", "more click position : $position")
                MainScope().launch {
                    postList.removeAt(position)
                    postPhotoAdapter.notifyItemRemoved(position)

                    withContext(Dispatchers.Main) {
                        delay(700)
                        addPhoto()
                    }
                }
            }
        })

        addPhoto()
    }

    private fun addPhoto() {
        val storageImgArray = getAllShownImages()
        storageImgArray.forEachIndexed { index, data ->
            postList.add(DataModel.Post(data, false, isLast =  false))
            lastIndex++
        }

        postList.add(DataModel.Post(null, false, isLast =  true))
        lastIndex++
        postPhotoAdapter.notifyDataSetChanged()
        binding.postPhotoRv.smoothScrollToPosition(lastIndex)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addPhoto()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAllShownImages(): MutableList<String> {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = parentActivity.contentResolver.query(uri, projection, null, null, null)
        val imagePaths = ArrayList<String>()

        Log.d("testtest","lastIndex is $lastIndex")

        cursor?.use { c ->
            val columnIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (c.moveToNext()) {
                val imagePath = c.getString(columnIndex)
                if (imagePaths.size < 100) imagePaths.add(imagePath) else break
            }
        }

        cursor?.close()
        return imagePaths.subList(lastIndex - 1, lastIndex + 19)
    }
}