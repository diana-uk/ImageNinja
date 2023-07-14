package com.diamount_studio.image_ninja.compressor.constraint

import com.diamount_studio.image_ninja.compressor.constraint.Compression
import com.diamount_studio.image_ninja.compressor.constraint.Constraint
import java.io.File

class DestinationConstraint(private val destination: File) : Constraint {
    override fun isSatisfied(imageFile: File): Boolean {
        return imageFile.absolutePath == destination.absolutePath
    }

    override fun satisfy(imageFile: File): File {
        return imageFile.copyTo(destination, true)
    }
}

fun Compression.destination(destination: File) {
    constraint(DestinationConstraint(destination))
}