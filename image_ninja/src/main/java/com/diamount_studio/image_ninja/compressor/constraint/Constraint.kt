package com.diamount_studio.image_ninja.compressor.constraint

import java.io.File

interface Constraint {
    fun isSatisfied(imageFile: File): Boolean

    fun satisfy(imageFile: File): File
}