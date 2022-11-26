package com.tomaslab.composegooglepart3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipTimeScreen()
                }

        }
    }
}

@Composable
fun TipTimeScreen(){
    Column(modifier = Modifier
        .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ){

        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

       EditNumberField()
    }
}

@Composable
fun EditNumberField(){
    var amountInput by remember { mutableStateOf("") }
    TextField(
        value = amountInput,
        onValueChange = {amountInput = it}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        TipTimeScreen()
}