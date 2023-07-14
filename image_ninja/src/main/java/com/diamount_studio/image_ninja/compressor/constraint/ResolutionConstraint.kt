package com.diamount_studio.image_ninja.compressor.constraint

import android.graphics.BitmapFactory
import com.diamount_studio.image_ninja.calculateInSampleSize
import com.diamount_studio.image_ninja.compressor.constraint.Compression
import com.diamount_studio.image_ninja.compressor.constraint.Constraint
import com.diamount_studio.image_ninja.decodeSampledBitmapFromFile
import com.diamount_studio.image_ninja.determineImageRotation
import com.diamount_studio.image_ninja.overWrite
import java.io.File

class ResolutionConstraint(private val width: Int, private val height: Int) : Constraint {

    override fun isSatisfied(imageFile: File): Boolean {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, this)
            calculateInSampleSize(this, width, height) <= 1
        }
    }

    override fun satisfy(imageFile: File): File {
        return decodeSampledBitmapFromFile(imageFile, width, height).run {
            determineImageRotation(imageFile, this).run {
                overWrite(imageFile, this)
            }
        }
    }
}

fun Compression.resolution(width: Int, height: Int) {
    constraint(ResolutionConstraint(width, height))
}