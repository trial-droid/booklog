package com.example.cahierdetexte

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var dateTimeText: TextView
    private lateinit var classSpinner: Spinner
    private lateinit var studentListLayout: LinearLayout
    private lateinit var lessonTitle: EditText
    private lateinit var chapterTitle: EditText
    private lateinit var subtitleTitle: EditText
    private lateinit var notesEdit: EditText
    private lateinit var saveButton: Button

    private val students = mutableListOf("Ali", "Sara", "Mohamed", "Fatima")
    private val selectedAbsents = mutableListOf<String>()

    private var selectedDateTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dateTimeText = findViewById(R.id.dateTimeText)
        classSpinner = findViewById(R.id.classSpinner)
        studentListLayout = findViewById(R.id.studentListLayout)
        lessonTitle = findViewById(R.id.lessonTitle)
        chapterTitle = findViewById(R.id.chapterTitle)
        subtitleTitle = findViewById(R.id.subtitleTitle)
        notesEdit = findViewById(R.id.notesEdit)
        saveButton = findViewById(R.id.saveButton)

        // Remplir la date/heure actuelle
        updateDateTimeDisplay()

        dateTimeText.setOnClickListener {
            pickDateTime()
        }

        // Remplir la liste des élèves
        loadStudentList()

        // Sauvegarde
        saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun updateDateTimeDisplay() {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        dateTimeText.text = sdf.format(selectedDateTime.time)
    }

    private fun pickDateTime() {
        val now = selectedDateTime
        DatePickerDialog(this, { _, year, month, day ->
            TimePickerDialog(this, { _, hour, minute ->
                selectedDateTime.set(year, month, day, hour, minute)
                updateDateTimeDisplay()
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true).show()
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun loadStudentList() {
        studentListLayout.removeAllViews()
        for (student in students) {
            val checkBox = CheckBox(this)
            checkBox.text = student
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedAbsents.add(student)
                } else {
                    selectedAbsents.remove(student)
                }
            }
            studentListLayout.addView(checkBox)
        }
    }

    private fun saveData() {
        val data = """
            Classe: ${classSpinner.selectedItem}
            Date & Heure: ${dateTimeText.text}
            Absents: ${selectedAbsents.joinToString(", ")}
            Titre de la leçon: ${lessonTitle.text}
            Chapitre: ${chapterTitle.text}
            Sous-titre: ${subtitleTitle.text}
            Notes: ${notesEdit.text}
        """.trimIndent()

        Toast.makeText(this, "Enregistré:\n$data", Toast.LENGTH_LONG).show()
        // Ici tu pourras ajouter la sauvegarde en PDF plus tard
    }
}
