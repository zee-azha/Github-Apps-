package com.example.submission3.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.databinding.FragmentFollowingBinding
import com.example.submission3.util.PersonAdapter
import com.example.submission3.viewmodel.FollowingViewModel


class FollowingFragment : Fragment(R.layout.fragment_following) {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: PersonAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(PersonDetails.EXTRA_PERSON).toString()
        binding = FragmentFollowingBinding.bind(view)

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        adapter = PersonAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }

        binding.progressCircular.visibility = View.VISIBLE

        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                binding.progressCircular.visibility = View.GONE
            }
        })
    }
}