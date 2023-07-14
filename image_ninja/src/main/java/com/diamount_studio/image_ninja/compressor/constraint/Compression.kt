package com.diamount_studio.image_ninja.compressor.constraint

import com.diamount_studio.image_ninja.compressor.constraint.Constraint

class Compression {
    internal val constraints: MutableList<Constraint> = mutableListOf()

    fun constraint(constraint: Constraint) {
        constraints.add(constraint)
    }
}