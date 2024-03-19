package com.example.notes.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.notes.R
import com.example.notes.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog(
    viewModel: MainViewModel,
    onDismissDialog: () -> Unit = {},
    onConfirmDialog: () -> Unit = {}
){
    val state by viewModel.noteState.collectAsState()

    val multipleMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
       viewModel.setImageUris(
            uris.map { it.toString() }
       )
    }

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(state.color)
            ),
            modifier = Modifier.fillMaxSize(),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                //Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    //
                    Icon(
                        modifier = Modifier.clickable { onConfirmDialog() },
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = stringResource(R.string.dialog_add_btn_cancel)
                    )

                    //
                    IconButton(onClick = { viewModel.setTogglePin(state.isPinned) }) {
                        if(state.isPinned){
                            Icon(
                                painter = painterResource(id = R.drawable.keep_24px),
                                contentDescription = stringResource(R.string.dialog_add_btn_pin)
                            )
                        }else{
                            Icon(
                                painter = painterResource(id = R.drawable.keep_off_24px),
                                contentDescription = stringResource(R.string.dialog_add_btn_unpin)
                            )
                        }
                    }
                }
                //Fields
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.dialog_add_input_title)) },
                    value = state.title,
                    onValueChange = { viewModel.setTitle(it) },
                    placeholder = { Text(text = stringResource(R.string.dialog_add_input_title))}
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.dialog_add_input_note)) },
                    value = state.note,
                    onValueChange ={ viewModel.setNote(it) },
                    placeholder = { Text(text = stringResource(R.string.dialog_add_placehold_note))},
                    minLines = 5
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Colors"
                )
                ColorPicker(
                    onColorClick = { viewModel.setColor(it)}
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Images"
                    )
                    //Add button
                    Box(
                        modifier = Modifier.clickable {
                            multipleMediaLauncher.launch(
                                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }.clip(CircleShape).background(color = MaterialTheme.colorScheme.tertiary).padding(7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.dialog_add_img)
                        )
                    }
                }
                ImagePicker(
                    imageUris = state.imageUris
                )
            }
        }
    }

}

@Preview
@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    imageUris: List<String> = emptyList()
){
    //List of images
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(imageUris) {
            AsyncImage(
                modifier = Modifier
                    .width(200.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(4.dp)),
                placeholder = painterResource(R.drawable.baseline_camera_alt_24),
                model = it,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.dialog_add_img),
            )
        }
    }
}

@Preview
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    onColorClick:(Long) -> Unit = {},
    list:List<Long> = listOf(
        0xFFFFFF8D,//yellow
        0xFFA7FFEB,//green
        0xFFFF80AB,//red
        0xFF82B1FF,//blue
        0xFFFF9E80,//orange
        0xFFB388FF,//purple
        0xFFEA80FC,//pink
        0xFF80D8FF// cyan
    )
){
    var colorState by rememberSaveable {
        mutableStateOf(0xFFFFFF8D)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = true
    ){
        items(list){ color->
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        Color(color),
                        CircleShape
                    )
                    .border(
                        width = if (colorState == color) 5.dp else 3.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .clickable {
                        onColorClick(color)

                        colorState = color
                    },
                contentAlignment = Alignment.Center
            ){

            }
        }
    }
}

