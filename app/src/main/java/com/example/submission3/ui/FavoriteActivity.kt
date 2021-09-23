package com.example.submission3.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.data.Person
import com.example.submission3.database.FavoriteUser
import com.example.submission3.databinding.ActivityFavoriteBinding
import com.example.submission3.util.PersonAdapter
import com.example.submission3.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = PersonAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object: PersonAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Person) {
                showSelectedPerson(data)
            }
        })

        binding.apply {
            rvGithub.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvGithub.setHasFixedSize(true)
            rvGithub.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this,{
            if (it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })


    }

    private fun mapList(it: List<FavoriteUser>): ArrayList<Person> {
        val listUser = ArrayList<Person>()
        for (user in it){
            val userMapped = Person(
                user.login,
                user.id,
                user.avatar_url
            )
            listUser.add(userMapped)

        }
        return listUser

    }

    private fun showSelectedPerson(person: Person) {
        val i = Person(
            person.login,
            person.id,
            person.avatar_url
        )
        val intentWithExtraData = Intent(this@FavoriteActivity, PersonDetails::class.java)
        intentWithExtraData.putExtra(PersonDetails.EXTRA_PERSON,i)
        startActivity(intentWithExtraData)

    }

}