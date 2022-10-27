package com.example.thirdtry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirdtry.ui.theme.ThirdTryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirdTryTheme {
                Scaffold() { padding ->
                    EventCreationScreen(Modifier.padding(padding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ThirdTryTheme {
        EventCreationScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdTryApp() {
    ThirdTryTheme {
        Scaffold() { padding ->
            EventCreationScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun EventCreationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Post an event:",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
        )
        TitleTextField()
        DateTextField()
        TimeTextField()
        LocationTextField()
        ExtraInfoTextField()
    }
}

@Composable
fun TextFieldForEventInfo(
    modifier: Modifier = Modifier,
    @StringRes exteriorLabel: Int,
) {
    var textFieldState by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(stringResource(exteriorLabel))
                },
        value = textFieldState,
        onValueChange = {
            textFieldState = it
                        },
    )
}

@Composable
fun CreateEventButton() {
    Button(onClick = { /*TODO*/ }) {
        Text("Create Event")
    }
}

@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
) {
    TextFieldForEventInfo(exteriorLabel = R.string.title_label)
}

@Composable
fun DateTextField(
    modifier: Modifier = Modifier,
) {
    TextFieldForEventInfo(exteriorLabel = R.string.date_label)
}

@Composable
fun TimeTextField(
    modifier: Modifier = Modifier,
) {
    TextFieldForEventInfo(exteriorLabel = R.string.time_label)
}

@Composable
fun LocationTextField(
    modifier: Modifier = Modifier,
) {
    TextFieldForEventInfo(exteriorLabel = R.string.location_label)
}

@Composable
fun ExtraInfoTextField(
    modifier: Modifier = Modifier,
) {
    TextFieldForEventInfo(exteriorLabel = R.string.extra_info_label)
}
