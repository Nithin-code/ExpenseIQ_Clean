package com.example.expenseiq_clean.presentation.view.add_expense

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseiq_clean.R
import com.example.expenseiq_clean.domain.model.ChipType
import com.example.expenseiq_clean.domain.model.DebitedCategory
import com.example.expenseiq_clean.domain.model.ExpenseCategory
import com.example.expenseiq_clean.domain.model.SpentViaCategory
import com.example.expenseiq_clean.ui.theme.AppTheme
import java.util.Date

@Composable
fun AddExpenseScreen(

) {
    var amount by remember {
        mutableStateOf("")
    }

    var isDatePickerVisible by remember {
        mutableStateOf(false)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(horizontal = 24.dp)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TopAppBar()

            AmountEditText(
                amountEntered = amount,
                onAmountChanged = { it ->
                    amount = it
                }
            )

            HorizontalChipSlider(
                title = "Amount Spent For: ",
                chipType = ChipType.ExpenseCategory,
                onChipSelected = {

                }
            )

            DatePickerRow(
                isDatePickerVisible = isDatePickerVisible,
                onNegativeButtonClicked = {
                    isDatePickerVisible = false
                },
                onPositiveButtonClicked = {
                    isDatePickerVisible = true
                }
            )

            HorizontalChipSlider(
                title = "Debited / Credited : ",
                chipType = ChipType.DebitedCategory,
                onChipSelected = {

                }
            )

            HorizontalChipSlider(
                title = "Spent Via : ",
                chipType = ChipType.SpentViaCategory,
                onChipSelected = {

                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = AppTheme.colors.incomeGreen)
                    .clickable{

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp
                        ),
                    text = "Save",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.textPrimary
                )
            }

        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.textPrimary
        )

        Spacer(
            modifier = Modifier.width(24.dp)
        )

        Text(
            text = "Add Your Expense",
            fontSize = 22.sp,
            color = AppTheme.colors.textPrimary,
        )

    }
}


@Composable
fun AmountEditText(
    amountEntered: String = "",
    onAmountChanged: (String) -> Unit
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        value = amountEntered,
        onValueChange = {
            onAmountChanged.invoke(it)
        },
        label = {
            Text("Enter Amount")
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.textPrimary,
            unfocusedTextColor = AppTheme.colors.textSecondary,
            focusedContainerColor = AppTheme.colors.cardBackground,
            unfocusedContainerColor = AppTheme.colors.cardBackground,
            focusedLabelColor = AppTheme.colors.textSecondary,
            unfocusedLabelColor = AppTheme.colors.textSecondary
        )
    )

}

@Composable
fun HorizontalChipSlider(
    selectedIndex: Int = 0,
    title: String = "",
    chipType: ChipType,
    onChipSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = AppTheme.colors.textPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (chipType) {
                is ChipType.DebitedCategory -> {
                    DebitedCategory.entries.forEach { debitedCategory ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .background(color = AppTheme.colors.cardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = debitedCategory.type,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = AppTheme.colors.textPrimary
                            )

                        }
                    }
                }

                is ChipType.ExpenseCategory -> {
                    ExpenseCategory.entries.forEach { expenseCategory ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .background(color = AppTheme.colors.cardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = expenseCategory.type,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = AppTheme.colors.textPrimary
                            )

                        }
                    }
                }

                is ChipType.SpentViaCategory -> {
                    SpentViaCategory.entries.forEach { spentViaCategory ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .background(color = AppTheme.colors.cardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = spentViaCategory.type,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = AppTheme.colors.textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerRow(
    modifier: Modifier = Modifier,
    isDatePickerVisible: Boolean = false,
    onPositiveButtonClicked: () -> Unit,
    onNegativeButtonClicked : () -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Select Calendar to Set Date: ",
            fontSize = 16.sp,
            color = AppTheme.colors.textPrimary,

            )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                modifier = Modifier
                    .size(24.dp)
                    .clickable{
                        onPositiveButtonClicked.invoke()
                    },
                contentDescription = null,
                tint = AppTheme.colors.textPrimary
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = AppTheme.colors.cardBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "day",
                    fontSize = 14.sp,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = AppTheme.colors.cardBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Month",
                    fontSize = 14.sp,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = AppTheme.colors.cardBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "year",
                    fontSize = 14.sp,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
        }
    }



    if (!isDatePickerVisible) return

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = AppTheme.colors.background
        ),
        onDismissRequest = {
           onNegativeButtonClicked.invoke()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { it->
                        println("selected date is ${Date(it)}")
                    }
                }
            ) {
                Text(
                    text = "Ok",
                    color = AppTheme.colors.textPrimary,
                    fontSize = 14.sp,
                )
            }
        },

        dismissButton = {
            TextButton(
                onClick = {
                    onNegativeButtonClicked.invoke()
                }
            ) {
                Text(
                    text = "Cancel",
                    color = AppTheme.colors.textPrimary,
                    fontSize = 14.sp,
                )
            }
        },

        ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = AppTheme.colors.cardBackground,
                titleContentColor = AppTheme.colors.textPrimary,
                weekdayContentColor = AppTheme.colors.textPrimary,
                headlineContentColor = AppTheme.colors.textPrimary,
                subheadContentColor = AppTheme.colors.textPrimary,
                dayContentColor = AppTheme.colors.textPrimary
            )
        )
    }


}

@Composable
@Preview(showBackground = true)
fun AddExpensePreview() {
    AddExpenseScreen()
}