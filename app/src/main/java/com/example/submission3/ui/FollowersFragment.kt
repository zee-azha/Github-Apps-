package com.example.submission3.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.databinding.FragmentFollowersBinding
import com.example.submission3.util.PersonAdapter
import com.example.submission3.viewmodel.FollowersViewModel


class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private lateinit var binding : FragmentFollowersBinding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: PersonAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(PersonDetails.EXTRA_PERSON).toString()

        binding = FragmentFollowersBinding.bind(view)

        adapter = PersonAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.adapter = adapter
        }
        binding.progressCircular.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if(it !=null){
                adapter.setList(it)
                binding.progressCircular.visibility = View.GONE
            }
        })



    }
}