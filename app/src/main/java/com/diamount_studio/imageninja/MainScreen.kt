package com.diamount_studio.imageninja

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diamount_studio.image_ninja.loadBitmap
import java.io.File
import java.lang.Math.log10
import java.text.DecimalFormat
import kotlin.math.pow

@Composable
fun MainScreen(
    actualImage: File?,
    compressedImage: File?,
    onChooseImage: () -> Unit,
    onCompressImage: () -> Unit,
    onCustomCompressImage: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                start = dimensionResource(R.dimen.activity_horizontal_margin),
                end = dimensionResource(R.dimen.activity_horizontal_margin),
                top = dimensionResource(R.dimen.activity_vertical_margin),
                bottom = dimensionResource(R.dimen.activity_vertical_margin)
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ImageView(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                bitmap = actualImage?.let {
                    loadBitmap(it) },
                contentDescription = "Actual Image"
            )

            ImageView(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                bitmap = compressedImage?.let { loadBitmap(it) },
                contentDescription = "Compressed Image"
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextView(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = stringResource(R.string.actual_image_label)
            )

            TextView(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                text = stringResource(R.string.compressed_image_label)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextView(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                text = stringResource(R.string.actual_size_label)
            )


            TextView(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                text = if (compressedImage != null) {
                    getReadableFileSize(compressedImage.length())
                } else {
                    stringResource(R.string.compressed_size_label)
                }
            )
        }


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = onChooseImage
        ) {
            Text(text = stringResource(R.string.choose_image_button_label))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = onCompressImage
        ) {
            Text(text = stringResource(R.string.compress_image_button_label))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = onCustomCompressImage
        ) {
            Text(text = stringResource(R.string.custom_compress_button_label))
        }
    }
}


@Composable
fun ImageView(modifier: Modifier, bitmap: Bitmap?, contentDescription: String) {
    bitmap?.asImageBitmap()?.let {
        Image(
        bitmap = it,
        modifier = modifier
            .aspectRatio(1f)
            .padding(bottom = 4.dp),
        contentDescription = contentDescription
    )
    }
}

@Composable
fun TextView(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        text = text,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )
}

private fun getReadableFileSize(size: Long): String {
    if (size <= 0) {
        return "0"
    }
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (kotlin.math.log10(size.toDouble()) / kotlin.math.log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
}
@Preview
@Composable
fun PreviewMainActivityLayout() {
    MainScreen(
        actualImage = null,
        compressedImage = null,
        onChooseImage = {},
        onCompressImage = {},
        onCustomCompressImage = {}
    )
}
