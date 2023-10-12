package com.example.notes.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.notes.domain.Note
import com.example.notes.ui.viewmodel.MainViewModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    note:Note = Note(1, "-------","------"),
    viewModel: MainViewModel
){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if(!note.imageUri.isNullOrEmpty()){
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = Uri.parse(note.imageUri))
                        .build()
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(top = 24.dp, start =24.dp, end=24.dp),
            text = note.title,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        note.dateUpdated?.let {
            Text(
                modifier = Modifier.padding(12.dp),
                text = it,
                color = Color.Gray
            )
        }
        Text(
            modifier = Modifier.padding(12.dp),
            text = note.note
        )
    }
}