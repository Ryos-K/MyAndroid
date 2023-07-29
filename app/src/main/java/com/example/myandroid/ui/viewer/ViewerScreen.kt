package com.example.myandroid.ui.viewer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter


@Composable
fun ViewerScreen() {
    var uri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri = it
        }

    Column(modifier = Modifier.padding(16.dp)) {
        if(uri != null) Image(painter = rememberAsyncImagePainter(uri), contentDescription = "")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Select")
        }

    }
}