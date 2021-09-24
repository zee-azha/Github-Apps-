package com.example.submission3.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.data.Person
import com.example.submission3.databinding.ActivityMainBinding
import com.example.submission3.util.PersonAdapter
import com.example.submission3.util.SettingPreferences
import com.example.submission3.viewmodel.DataStoreViewModel
import com.example.submission3.viewmodel.MainViewModel
import com.example.submission3.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PersonAdapter
    private lateinit var list: ArrayList<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        adapter = PersonAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : PersonAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Person) {
                showSelectedPerson(data)
            }
        })

        binding.apply {
            rvGithub.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithub.setHasFixedSize(true)
            rvGithub.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isEmpty()) {
                        list.clear()
                        return false
                    } else {
                        searchPerson()
                    }
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return false
                }
            })
        }

        viewModel.getOnSearch().observe(this, {
            if (it != null) {
                adapter.setList(it)
                binding.progressCircular.visibility = View.GONE
            }
        })

        viewModel.getToastObserver().observe(this, { message ->
            run {

                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun searchPerson() {
        binding.apply {
            val query = searchView.query.toString()
            progressCircular.visibility = View.VISIBLE
            viewModel.setOnSearch(query)
        }
    }

    private fun showSelectedPerson(person: Person) {
        val i = Person(
            person.login,
            person.id,
            person.avatar_url
        )
        val intentWithExtraData = Intent(this@MainActivity, PersonDetails::class.java)
        intentWithExtraData.putExtra(PersonDetails.EXTRA_PERSON, i)
        startActivity(intentWithExtraData)
    }

    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val switchTheme = menu.findItem(R.id.darkMode).actionView as SwitchMaterial
        val favoriteButton = menu.findItem(R.id.favorite_user)
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.searchView.setBackgroundColor(Color.GREY)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        } else  {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}

