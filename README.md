ImageNinja
======
[![](https://jitpack.io/v/diana-uk/ImageNinja.svg)](https://jitpack.io/#diana-uk/ImageNinja)
<p align="center"><img src="https://github.com/diana-uk/ImageNinja/assets/68230600/f361cef8-9682-4044-848a-8c56af16639b" width="40%" /></p>

ImageNinja is a powerful and versatile Android image manipulation library. It offers a wide range of functionalities to enhance and transform images effortlessly. Whether you need to compress, crop, resize, or apply filters to images, ImageNinja has got you covered.

# Gradle
```groovy
dependencies {
    implementation 'com.github.diana-uk:ImageNinja:version_number'
}
```

# Let's unleash the power of ImageNinja!

### Compressing Images
ImageNinjaCompressor allows you to compress large photos into smaller-sized photos while maintaining exceptional image quality.

#### Compress Image File
```kotlin
val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile)
```

#### Compress Image File to a Specific Destination
```kotlin
val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile) {
    destination(myFile)
}
```

### Customizing the Compression

ImageNinja provides flexible options to customize the compression process according to your needs.

#### Using Default Constraints with Custom Modifications
```kotlin
val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile) {
    default(width = 640, format = Bitmap.CompressFormat.WEBP)
}
```

#### Full Custom Constraints
```kotlin
val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile) {
    resolution(1280, 720)
    quality(80)
    format(Bitmap.CompressFormat.WEBP)
    size(2_097_152) // 2 MB
}
```

#### Applying Your Own Custom Constraints
```kotlin
class MyLowerCaseNameConstraint: Constraint {
    override fun isSatisfied(imageFile: File): Boolean {
        return imageFile.name.all { it.isLowerCase() }
    }

    override fun satisfy(imageFile: File): File {
        val destination = File(imageFile.parent, imageFile.name.toLowerCase())
        imageFile.renameTo(destination)
        return destination
    }
}

val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile) {
    constraint(MyLowerCaseNameConstraint()) // your own constraint
    quality(80) // combine with ImageNinja compressor constraint
    format(Bitmap.CompressFormat.WEBP)
}
```

#### Creating Your Own Extensions
```kotlin
fun Compression.lowerCaseName() {
    constraint(MyLowerCaseNameConstraint())
}

val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile) {
    lowerCaseName() // your own extension
    quality(80) // combine with ImageNinja compressor constraint
    format(Bitmap.CompressFormat.WEBP)
}
```

### ImageNinja Harnesses the Power of Kotlin Coroutines!

#### Calling ImageNinja from Coroutine Scopes
```kotlin
// For example, calling from the activity's lifecycle scope
lifecycleScope.launch {
    val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile)
}

// Calling from the global scope
GlobalScope.launch {
    val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile)
}
```

#### Running ImageNinja on the Main Thread
```kotlin
val compressedImageFile = ImageNinjaCompressor.compress(context, actualImageFile, Dispatchers.Main)
```

### Additional Image Transformations

ImageNinja goes beyond compression and offers an array of image manipulation functions. Here are a few examples:

#### Cropping Images
```kotlin
val croppedImage = ImageNinjaCropper.crop(context, originalImageFile, cropRect)
```

#### Resizing Images
```kotlin
val resizedImage = ImageNinjaResizer.resize(context, originalImageFile, targetWidth, targetHeight)
```

#### Applying Image Filters
```kotlin
val filteredImage = ImageNinjaFilter.applyFilter(context, originalImageFile, filterType)
```

#### Rotating Images
```kotlin
val rotatedImage = ImageNinjaRotator.rotate(context, originalImageFile, rotationAngle)
```

### Discover the Power of ImageNinja!

Explore the full range of ImageNinja's functionalities and transform your images with ease. From compression to cropping, resizing, and applying filters, ImageNinja empowers you to unleash your creativity and create stunning visuals effortlessly.

Check out the [sample app](https://github.com/diana-uk/ImageNinja/tree/master/app) to see ImageNinja in action!

---

ImageNinja - Master the Art of Image Manipulation
