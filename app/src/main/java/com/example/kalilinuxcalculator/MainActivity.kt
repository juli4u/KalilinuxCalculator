package com.example.kalilinuxcalculator

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var popupDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val spinner1: Spinner = findViewById(R.id.spinner)
        val spinner2: Spinner = findViewById(R.id.spinner2)
        val outputTextView: TextView = findViewById(R.id.output)
        val submitButton: Button = findViewById(R.id.buttonsubmit)
        val clearButton: Button = findViewById(R.id.buttonclear)

        val adapter1 = ArrayAdapter.createFromResource(this, R.array.first_spinner_options, android.R.layout.simple_spinner_item)
        val adapter2 = ArrayAdapter.createFromResource(this, R.array.second_spinner_options, android.R.layout.simple_spinner_item)

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapter1
        spinner2.adapter = adapter2

        val instructButton: ImageButton = findViewById(R.id.instructbutton)

        instructButton.setOnClickListener {
            showPopup()
        }

        fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption1 = parent?.getItemAtPosition(position).toString()
                showToast("Selected option : $selectedOption1")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption2 = parent?.getItemAtPosition(position).toString()
                showToast("Selected option : $selectedOption2")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        fun getDescription(selectedOption1: String, selectedOption2: String): String {
            return when {
                selectedOption1 == "ip" && selectedOption2 == "a" ->
                    "ip - show / manipulate routing, network devices, interfaces and tunnels.\na - all."
                selectedOption1 == "ls" && selectedOption2 == "-la" ->
                    "ls - list directory contents.\n-l - list directory contents.\n a - all."
                selectedOption1 == "man" && selectedOption2 == "sudo" ->
                    "man - it is system manual for terminal emulator in kali linux.\nsudo - allows a permitted user to execute a command as the superuser or another user."
                else -> {
                    showToast("Error: Invalid selection combination.")
                    ""
                }
            }
        }

        submitButton.setOnClickListener {
            val selectedOption1 = spinner1.selectedItem.toString()
            val selectedOption2 = spinner2.selectedItem.toString()
            val description = getDescription(selectedOption1, selectedOption2)
            if (description.isNotEmpty()) {
                val result = "$selectedOption1 $selectedOption2\n\n$description"
                outputTextView.text = result
            }
        }

        clearButton.setOnClickListener {
            spinner1.setSelection(0)
            spinner2.setSelection(0)
            outputTextView.text = ""
            showToast("Cleared selections and output.")
        }
    }

    private fun showPopup() {
        var popupDialog = Dialog(this)

        popupDialog?.setContentView(R.layout.popupwindow)


        val closeButton: Button = popupDialog?.findViewById(R.id.closeButton) ?: return


        closeButton.setOnClickListener {

            popupDialog?.dismiss()
        }


        popupDialog?.show()
    }
}
