package com.example.submission3.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.data.Person
import com.example.submission3.databinding.ActivityPersonDetailsBinding
import com.example.submission3.util.SectionPagerAdapter
import com.example.submission3.viewmodel.DetailsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonDetails : AppCompatActivity() {

    private lateinit var binding: ActivityPersonDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    companion object {
        const val EXTRA_PERSON = "extra_person"
        
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private var _isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person
        val username = person.login
        val id = person.id
        val avatar = person.avatar_url

        val bundle = Bundle()
        bundle.putString(EXTRA_PERSON, username)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.setOnDetails(username)
        viewModel.getOnDetails().observe(this,{
            if(it !=null)
                binding.apply {
                    Glide.with(this@PersonDetails)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgAvatar)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    tvRepository.text = it.public_repos.toString()
                }
        })


        viewModel.getToastObserver().observe(this, { message->
            run {

                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        )

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.favoriteToggle.isChecked =true
                        _isChecked = true
                    }else{
                        binding.favoriteToggle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.favoriteToggle.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                viewModel.insert(username,id,avatar)
                showToast(getString(R.string.insert_data))
            }else{
                viewModel.remove(id)
                showToast(getString(R.string.delete_data))
            }
            binding.favoriteToggle.isChecked = _isChecked
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this,bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showToast(string: String) {
        Toast.makeText(this,string, Toast.LENGTH_LONG).show()
    }
}