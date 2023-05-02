package com.example.imageapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private var isLastImageClick = false
    private val REQUEST_CODE = 1010101
    private val IMAGE_RESULT_CODE = 121
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<MaterialButton>(R.id.btnClickPhoto).setOnClickListener {
            requestForPermission()

        }

    }

    private fun takeImageFromCamera() {
        val intent=Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,IMAGE_RESULT_CODE)
    }

    private fun requestForPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
            )
        } else {
            takeImageFromCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
takeImageFromCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==IMAGE_RESULT_CODE){
            if(resultCode== RESULT_OK){
                if(data!=null){
                    val clickedImage=data.extras?.get("data") as Bitmap?
                    if(!isLastImageClick){
                        isLastImageClick=true
                        findViewById<ImageView>(R.id.oldImage).setImageBitmap(clickedImage)
                    }else
                    {
                        findViewById<ImageView>(R.id.newImage).setImageBitmap(clickedImage)
                    }
                }
            }
        }
    }
}