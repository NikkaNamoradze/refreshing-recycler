package com.example.reccyclergrid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.GridLayoutManager
import com.example.reccyclergrid.R
import com.example.reccyclergrid.adapter.PersonsAdapter
import com.example.reccyclergrid.databinding.ActivityMainBinding
import com.example.reccyclergrid.models.Person
import com.example.reccyclergrid.models.personsList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter: PersonsAdapter by lazy {
        PersonsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        init()
        refreshRecycler()
        modifyPerson()
    }

    private fun init() {
        binding.apply {
            personsRecycler.layoutManager = GridLayoutManager(applicationContext, 2)
            personsRecycler.adapter = adapter
        }
        addPerson()
        adapter.setData(personsList)
    }


    private fun refreshRecycler() {
        binding.refreshRecycler.setOnRefreshListener {
            adapter.setData(personsList)
            binding.refreshRecycler.isRefreshing = false
        }
        cleaner()
    }

    private fun addPerson() {
        binding.btnAdd.setOnClickListener {
            val newID = binding.etID.text.toString()
            val newName = binding.etName.text.toString()
            val newLastName = binding.etLastName.text.toString()
            var gender = true
            if (binding.rbFemale.isChecked) {
                gender = false
            }

            if(newID.length < 11){
                Toast.makeText(this, "პირადი ნომერი > 10 ციფრი", Toast.LENGTH_SHORT).show()
            }else{
                val newPerson = Person(newID.toInt(), newName, newLastName, gender)
                adapter.addPerson(newPerson)
                adapter.notifyItemInserted(personsList.size - 1)
                cleaner()
            }

        }
    }

    private fun modifyPerson() {
        adapter.modifyPerson = {
            buildDialog()
        }
    }

    private fun buildDialog() {
        val dialog = LayoutInflater.from(this).inflate(R.layout.modify_person_dialog, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialog)
        val alertDialog = dialogBuilder.show()

        dialog.findViewById<AppCompatButton>(R.id.btnModify).setOnClickListener {
            val personID = dialog.findViewById<AppCompatEditText>(R.id.modifyID).text.toString()
            val personName =
                dialog.findViewById<AppCompatEditText>(R.id.modifyName).text.toString()
            val personLastName =
                dialog.findViewById<AppCompatEditText>(R.id.modifyLastName).text.toString()

            val genderFemale = dialog.findViewById<AppCompatRadioButton>(R.id.modifyRbFemale)
            var gender = true
            if (genderFemale.isChecked) {
                gender = false
            }

            val newPerson = Person(personID.toInt(), personName, personLastName, gender)

            if (isAccountExist(newPerson)) {
                personsList.forEach {
                    if (it.id == personID.toInt()) {
                        personsList[personsList.indexOf(it)] = newPerson
                        adapter.notifyItemChanged(personsList.indexOf(it))
                        alertDialog.dismiss()
                    }
                }
            } else {
                Toast.makeText(this, "ასეთი ადამიანი არ არსებობს", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isAccountExist(userInfo: Person): Boolean {
        var isExist = false
        for (user in personsList) {
            if (user.id == userInfo.id) {
                isExist = true
                break
            }
        }
        return isExist
    }

    private fun cleaner() {
        binding.apply {
            etID.text?.clear()
            etName.text?.clear()
            etLastName.text?.clear()
            rbFemale.isChecked = false
            rbMale.isChecked = false
        }
    }
}