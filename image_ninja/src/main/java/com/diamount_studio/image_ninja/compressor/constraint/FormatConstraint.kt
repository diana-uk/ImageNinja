package com.diamount_studio.image_ninja.compressor.constraint

import android.graphics.Bitmap
import com.diamount_studio.image_ninja.compressFormat
import com.diamount_studio.image_ninja.compressor.constraint.Compression
import com.diamount_studio.image_ninja.compressor.constraint.Constraint
import com.diamount_studio.image_ninja.loadBitmap
import com.diamount_studio.image_ninja.overWrite
import java.io.File

class FormatConstraint(private val format: Bitmap.CompressFormat) : Constraint {

    override fun isSatisfied(imageFile: File): Boolean {
        return format == imageFile.compressFormat()
    }

    override fun satisfy(imageFile: File): File {
        return overWrite(imageFile, loadBitmap(imageFile), format)
    }
}

fun Compression.format(format: Bitmap.CompressFormat) {
    constraint(FormatConstraint(format))
}