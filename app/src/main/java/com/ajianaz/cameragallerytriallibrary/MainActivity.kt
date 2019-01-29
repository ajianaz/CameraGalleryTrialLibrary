package com.ajianaz.cameragallerytriallibrary

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.DefaultCallback
import android.content.Intent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_main.*
import java.io.File





class MainActivity : AppCompatActivity() {
    //image pick code
    private val IMAGE_PICK_CODE = 1000
    //Permission code
    private val PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            klikGetImage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun klikGetImage(){
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                //permission already granted
                pickImage()
            }
        }
        else{
            //system OS is < Marshmallow
            pickImage()
        }
    }

    private fun pickImage() {
        EasyImage.openChooserWithGallery(this, "Pilih Gambar", 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Glide
                    .with(baseContext)
                    .load(imageFile)
                    .into(main_imageView)
            }

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

//            fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
//                //Handle the images
//                onPhotosReturned(imagesFiles)
//            }

        })
    }

}
