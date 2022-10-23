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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirdtry.ui.theme.ThirdTryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirdTryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

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
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        TextFieldForEventInfo(label = R.string.title_label)
        TextFieldForEventInfo(label = R.string.date_label)
        TextFieldForEventInfo(label = R.string.time_label)
        TextFieldForEventInfo(label = R.string.location_label)
        TextFieldForEventInfo(label = R.string.extra_info_label)
    }
}

@Composable
fun TextFieldForEventInfo(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(label),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
        )
    }
}