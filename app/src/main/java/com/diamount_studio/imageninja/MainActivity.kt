package com.diamount_studio.imageninja

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.diamount_studio.image_ninja.compressor.ImageNinjaCompressor
import com.diamount_studio.image_ninja.compressor.constraint.format
import com.diamount_studio.image_ninja.compressor.constraint.quality
import com.diamount_studio.image_ninja.compressor.constraint.resolution
import com.diamount_studio.image_ninja.compressor.constraint.size
import com.diamount_studio.imageninja.ui.theme.ImageNinjaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class MainActivity : ComponentActivity() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private var actualImage: File? by mutableStateOf(null)
    private var compressedImage: File? by mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageNinjaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        actualImage = actualImage,
                        compressedImage = compressedImage,
                        onChooseImage = { chooseImage() },
                        onCompressImage = { compressImage() },
                        onCustomCompressImage = { customCompressImage() }
                    )
                }
            }
        }
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun compressImage() {
        actualImage?.let { imageFile ->
            lifecycleScope.launch {
                // Default compression
                compressedImage = ImageNinjaCompressor.compress(this@MainActivity, imageFile)
            }
        } ?: showError("Please choose an image!")
    }

    private fun customCompressImage() {
        actualImage?.let { imageFile ->
            lifecycleScope.launch {
                // Custom compression
                compressedImage = ImageNinjaCompressor.compress(this@MainActivity, imageFile) {
                    resolution(1280, 720)
                    quality(80)
                    format(Bitmap.CompressFormat.WEBP)
                    size(2_097_152) // 2 MB
                }
            }
        } ?: showError("Please choose an image!")
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!")
                return
            }
            try {
                actualImage = data.data?.let {
                    FileUtil.from(this, it)?.also {
                        clearImage()
                    }
                }
            } catch (e: IOException) {
                showError("Failed to read picture data!")
                e.printStackTrace()
            }
        }
    }

    private fun clearImage() {
        actualImage = null
        compressedImage = null
    }
    @Composable
    private fun setCompressedImage(context: ComponentActivity) {
        val compressedImage = compressedImage
        if (compressedImage != null) {
            LaunchedEffect(context) {
                Toast.makeText(context, "Compressed image saved in ${compressedImage.path}", Toast.LENGTH_LONG).show()
                Log.d("Compressor", "Compressed image saved in ${compressedImage.path}")
            }
        }
    }
    private suspend fun loadBitmap(context: Context, file: File): Bitmap {
        return withContext(Dispatchers.IO) {
            BitmapFactory.decodeFile(file.absolutePath)
        }
    }
    }



    private fun showError(context: ComponentActivity, errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImageNinjaTheme {
        Greeting("Android")
    }
}