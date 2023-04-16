package com.example.dynaimage.views

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import com.example.dynaimage.MainActivity
import com.example.dynaimage.R
import com.example.dynaimage.databinding.ActivityTextConvertBinding
import com.example.dynaimage.model.JsonTextData
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TextConvertActivity : AppCompatActivity() {

    private lateinit var actContentResolver: ContentResolver
    private lateinit var binding: ActivityTextConvertBinding
    // Request code for selecting a PDF document.
    val PICK_FILE = 1
    var fileDataObj : List<JsonTextData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextConvertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actContentResolver = contentResolver

        binding.btnUploadFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"

                // Optionally, specify a URI for the file that should appear in the
                // system file picker when it loads.
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, true)
            }

            startActivityForResult(intent, PICK_FILE)
        }
        binding.btnSpainish.setOnClickListener {
            checkWordAsPerLanguage("spainish")
        }
        binding.btnHindi.setOnClickListener {
            checkWordAsPerLanguage("hindi")
        }
        binding.btnIrish.setOnClickListener {
            checkWordAsPerLanguage("Irish")
        }
        binding.btnRussian.setOnClickListener {
            checkWordAsPerLanguage("Russian")
        }
        binding.btnTelugu.setOnClickListener {
            checkWordAsPerLanguage("telugu")
        }
        binding.btnNext.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun checkWordAsPerLanguage(lang: String) {
        val data = fileDataObj?.find { lg->
            lg.language.equals(lang, true)
        }
        binding.txtHelloWorld.text = data?.word ?: getString(R.string.str_hello_world)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_FILE
            && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            binding.txtFileName.text = resultData?.data?.path
            fileDataObj = null
            resultData?.data?.also { uri ->
                try {
                    val plainText = readTextFromUri(uri)
                    fileDataObj = GsonBuilder().create().fromJson(plainText, Array<JsonTextData>::class.java).toList()
                } catch (ex: Exception) {
                    Log.d("TAG", "onActivityResult: ${ex.message}")
                } finally {
                    if (fileDataObj.isNullOrEmpty()) {
                        Toast.makeText(this, "Please select appropriate json file", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        actContentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }
}