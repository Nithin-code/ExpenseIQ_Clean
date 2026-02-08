package com.example.expenseiq_clean.presentation.view.add_expense


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expenseiq_clean.domain.model.ChipType
import com.example.expenseiq_clean.domain.model.DebitedCategory
import com.example.expenseiq_clean.domain.model.ExpenseCategory
import com.example.expenseiq_clean.domain.model.SpentViaCategory
import com.example.expenseiq_clean.presentation.model.add_expense.AddExpenseUIEvent
import com.example.expenseiq_clean.presentation.viewmodel.add_expense.AddExpenseViewModel
import com.example.expenseiq_clean.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel
) {

    val screenState = viewModel
        .addExpenseScreenUIState
        .collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = "Enter Your Expense",
                        fontSize = 22.sp,
                        color = AppTheme.colors.textPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onBackButtonClicked()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = AppTheme.colors.textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.incomeGreen,
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .verticalScroll(state = rememberScrollState()),
        ) {
            AnimatedVisibility(
                visible = screenState.value.showBanner
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = AppTheme.colors.expenseRed.copy(alpha = 0.8f)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        text = "Please Enter Valid Expense",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textPrimary
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.background)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                CustomEditText(
                    title = "Enter Your Amount: ",
                    label = "Amount",
                    amountEntered = screenState.value.enteredAmount,
                    onAmountChanged = { it ->
                        viewModel.onAmountChange(it)
                    }
                )

                HorizontalChipSlider(
                    selectedExpenseCategoryName = screenState.value.selectedExpenseType,
                    title = "Amount Spent For: ",
                    chipType = ChipType.ExpenseCategory,
                    onExpenseChipSelected = {
                        viewModel.onChipSelected(
                            name = it,
                            chipType = ChipType.ExpenseCategory
                        )
                    }
                )

                AnimatedVisibility(
                    visible = screenState.value.selectedExpenseType == ExpenseCategory.OTHERS.type
                ) {
                    CustomEditText(
                        title = "Amount Send To: ",
                        amountEntered = screenState.value.enteredAmount,
                        onAmountChanged = { it ->
                            viewModel.onAmountChange(it)
                        },
                        label = "Name",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                }

                DatePickerRow(
                    isDatePickerVisible = screenState.value.shouldShowDatePickerDialog,
                    onNegativeButtonClicked = {
                        viewModel.onDatePickerSelected(null)
                    },
                    onPositiveButtonClicked = { dateStr, dateMillis ->
                        viewModel.onDatePickerSelected(dateMillis)
                    },
                    dayText = screenState.value.selectedDayDate,
                    monthText = screenState.value.selectedMonth,
                    yearText = screenState.value.selectedYear,
                )

                HorizontalChipSlider(
                    selectedDebitCategoryName = screenState.value.selectedDebitType,
                    title = "Debited / Credited : ",
                    chipType = ChipType.DebitedCategory,
                    onDebitChipSelected = {
                        viewModel.onChipSelected(
                            name = it,
                            chipType = ChipType.DebitedCategory
                        )
                    }
                )

                HorizontalChipSlider(
                    selectedSpentViaCategoryName = screenState.value.selectedSpentVia,
                    title = "Spent Via : ",
                    chipType = ChipType.SpentViaCategory,
                    onSpentViaChipSelected = {
                        viewModel.onChipSelected(
                            name = it,
                            chipType = ChipType.SpentViaCategory
                        )
                    }
                )

                AnimatedVisibility(
                    visible = screenState.value.selectedSpentVia == SpentViaCategory.OTHERS.type
                ) {
                    CustomEditText(
                        title = "Amount Sent From: ",
                        amountEntered = screenState.value.enteredAmount,
                        onAmountChanged = { it ->
                            viewModel.onAmountChange(it)
                        },
                        label = "Name",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = AppTheme.colors.incomeGreen)
                        .clickable {
                            viewModel.onSaveButtonClicked()
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
}

@Composable
fun CustomEditText(
    title: String = "",
    amountEntered: String = "",
    onAmountChanged: (String) -> Unit,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number
    ),
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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = amountEntered,
            onValueChange = {
                onAmountChanged.invoke(it)
            },
            label = {
                Text(label)
            },
            keyboardOptions = keyboardOptions,
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

}

@Composable
fun HorizontalChipSlider(
    selectedExpenseCategoryName: String = "",
    selectedDebitCategoryName: String = "",
    selectedSpentViaCategoryName: String = "",
    title: String = "",
    chipType: ChipType,
    onExpenseChipSelected: ((String) -> Unit)? = null,
    onDebitChipSelected: ((String) -> Unit)? = null,
    onSpentViaChipSelected: ((String) -> Unit)? = null,
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
                    DebitedCategory.entries.forEachIndexed { index, debitedCategory ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .background(color = AppTheme.colors.cardBackground)
                                .border(
                                    width = 1.dp,
                                    color = if (debitedCategory.type == selectedDebitCategoryName) AppTheme.colors.incomeGreen else AppTheme.colors.cardBackground,
                                    shape = RoundedCornerShape(50)
                                )
                                .clickable {
                                    onDebitChipSelected?.invoke(debitedCategory.type)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = debitedCategory.type,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = if (debitedCategory.type == selectedDebitCategoryName) AppTheme.colors.incomeGreen else AppTheme.colors.textPrimary
                            )

                        }
                    }
                }

                is ChipType.ExpenseCategory -> {
                    ExpenseCategory.entries.forEachIndexed { index, expenseCategory ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .background(color = AppTheme.colors.cardBackground)
                                .border(
                                    width = 1.dp,
                                    color = if (expenseCategory.type == selectedExpenseCategoryName) AppTheme.colors.incomeGreen else AppTheme.colors.cardBackground,
                                    shape = RoundedCornerShape(50)
                                )
                                .clickable {
                                    onExpenseChipSelected?.invoke(expenseCategory.type)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = expenseCategory.type,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = if (expenseCategory.type == selectedExpenseCategoryName) AppTheme.colors.incomeGreen else AppTheme.colors.textPrimary
                            )

                        }
                    }
                }

                is ChipType.SpentViaCategory -> {
                    SpentViaCategory.entries.forEachIndexed { index, spentViaCategory ->
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .background(color = AppTheme.colors.cardBackground)
                                .border(
                                    width = 1.dp,
                                    color = if (spentViaCategory.type == selectedSpentViaCategoryName) AppTheme.colors.incomeGreen else AppTheme.colors.cardBackground,
                                    shape = RoundedCornerShape(50)
                                )
                                .clickable {
                                    onSpentViaChipSelected?.invoke(spentViaCategory.type)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = spentViaCategory.type,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = if (spentViaCategory.type == selectedSpentViaCategoryName) AppTheme.colors.incomeGreen else AppTheme.colors.textPrimary
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
    dayText: String = "",
    monthText: String = "",
    yearText: String = "",
    onPositiveButtonClicked: (String, Long) -> Unit,
    onNegativeButtonClicked: () -> Unit,
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
                    .clickable {
                        onNegativeButtonClicked.invoke()
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
                    text = dayText,
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
                    text = monthText,
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
                    text = yearText,
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
                    datePickerState.selectedDateMillis?.let { it ->
                        println("${Date(it)}")
                        onPositiveButtonClicked.invoke(
                            "${Date(it)}",
                            it
                        )
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
    AddExpenseScreen(viewModel = koinViewModel<AddExpenseViewModel>())
}