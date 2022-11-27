package com.tomaslab.composegooglepart3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat
import androidx.compose.ui.graphics.Color


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

    val focusManager = LocalFocusManager.current

    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull()?:0.0
    val tipPrecent = tipInput.toDoubleOrNull()?:0.0
    val tip = calculateTip(amount,tipPrecent,roundUp)

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

       EditNumberField(
           label = R.string.bill_amount,
           value = amountInput,
           onValueChange = {amountInput = it},
           modifier = Modifier.fillMaxWidth(),
           keyboardOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Next

           ),
           keyboardActions = KeyboardActions (
               onNext = { focusManager.moveFocus(FocusDirection.Down)}
           )
       )

        Spacer(modifier = Modifier.height(5.dp))

        EditNumberField(
            label = R.string.how_was_the_service,
            value = tipInput,
            onValueChange ={tipInput = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions (onDone = { focusManager.clearFocus()})

        )

        Spacer(modifier = Modifier.height(4.dp))

        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChange = {roundUp = it})

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.tip_amount,tip),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditNumberField(
    @StringRes label:Int,
    value:String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions:KeyboardActions,
    modifier: Modifier = Modifier
){

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label))},
        modifier = modifier,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun RoundTheTipRow(
    roundUp:Boolean,
    onRoundUpChange:(Boolean)->Unit,
    modifier: Modifier = Modifier){

    Row (
        modifier= Modifier
            .fillMaxWidth()
            .size(48.dp),
            verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = stringResource(id = R.string.round_up_tip))

        Switch(
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChange
        )
    }
}

@VisibleForTesting
internal fun calculateTip(amount: Double,tipPercent: Double,roundUp: Boolean): String {
    var tip = tipPercent/100*amount
    if(roundUp)
        tip =kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        TipTimeScreen()
}