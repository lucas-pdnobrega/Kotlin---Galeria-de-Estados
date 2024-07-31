package com.example.galeriadeestados

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryApp(this)
        }
    }
}

@Composable
fun GalleryApp(context: Context) {
    val imagePath = arrayOf("asn.webp", "guernica.webp", "tgwtpe.webp")
    val title = arrayOf("A Starry Night", "Guernica", "Girl with a Pearl Earring")
    val artist = arrayOf("Vincent Van Gogh (1889)", "Pablo Picasso (1937)", "Johannes Vermeer (1665)")

    Gallery(context, imagePath, title, artist)
}

@Composable
fun Gallery(context: Context, imagePath: Array<String>, title: Array<String>, artist: Array<String>, modifier: Modifier = Modifier) {
    var idx by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Surface {
            val bitmap = loadBitmapFromAssets(context, imagePath[idx])
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Artwork Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(text = title[idx], modifier = Modifier.weight(1f))
            Text(text = artist[idx], modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                idx = if (idx > 0) idx - 1 else imagePath.size - 1
            }, modifier = Modifier.weight(1f)) {
                Text(text = "Previous")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                idx = if (idx < imagePath.size - 1) idx + 1 else 0
            }, modifier = Modifier.weight(1f)) {
                Text(text = "Next")
            }
        }
    }
}

fun loadBitmapFromAssets(context: Context, filePath: String): android.graphics.Bitmap? {
    return try {
        context.assets.open(filePath).use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GalleryApp(context = LocalContext.current)
}
