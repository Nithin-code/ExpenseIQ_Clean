package com.example.expenseiq_clean.presentation.view.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expenseiq_clean.R
import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.presentation.viewmodel.dashboard.DashBoardViewModel
import com.example.expenseiq_clean.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    onAddButtonClicked: ()-> Unit
) {

    val dashBoardViewModel = koinViewModel<DashBoardViewModel>()

    val screenState = dashBoardViewModel
        .dashBoardScreenState
        .collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(shape = CircleShape)
                    .background(color = AppTheme.colors.cardBackground)
                    .clickable{
                        onAddButtonClicked.invoke()
                    }
            ){
                Icon(
                    painter = painterResource(R.drawable.add_icon_24),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center),
                    tint = AppTheme.colors.textPrimary
                )
            }
        }
    ) { paddingValues ->

        AnimatedVisibility(
            visible = true,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.background)
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    CircularCardImage(
                        painter = painterResource(R.drawable.settings_icon),
                        onCardClicked = {

                        }
                    )
                    HeaderText(
                        viewModel = dashBoardViewModel
                    )
                    CircularCardImage(
                        painter = painterResource(R.drawable.notification_icon),
                        onCardClicked = {}
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                SpendDetails(
                    title = screenState.value.totalAmount
                )
                Spacer(modifier = Modifier.height(12.dp))
                SpendAnalysisDetails()
                Spacer(modifier = Modifier.height(24.dp))
                MiniStatementHeader()
                Spacer(modifier = Modifier.height(12.dp))
                MiniStatementList(
                    modifier = Modifier.weight(1f),
                    expenseList = screenState.value.topExpensesList
                )
            }
        }


    }
}


@Composable
fun CircularCardImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    onCardClicked : () -> Unit
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(shape = CircleShape)
            .background(color = AppTheme.colors.cardBackground)
            .clickable {
                onCardClicked.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp),
            colorFilter = ColorFilter.tint(color = AppTheme.colors.textPrimary)
        )
    }
}

@Composable
fun HeaderText(
    viewModel: DashBoardViewModel,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.calendar_icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(color = AppTheme.colors.textPrimary)
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Tue, 28 Oct",
            fontSize = 20.sp,
            color = AppTheme.colors.textPrimary
        )
    }

}

@Composable
fun SpendDetails(
    modifier: Modifier = Modifier,
    title : String = "Rs 313.31",
    subTitle : String = "Total Spend Amount This Month"
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = subTitle,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.colors.textPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )

    }

}

@Composable
fun SpendAnalysisDetails(){

    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 5.dp,
        color = AppTheme.colors.cardBackground
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = AppTheme.colors.cardBackground,
                    shape = RoundedCornerShape(12.dp)
                )
        ){
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Your Expense Analysis...",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = AppTheme.colors.textSecondary
            )
        }
    }

}

@Composable
fun MiniStatementHeader(
    title: String = "Recent Transactions",
    subTitle: String = "View All"
){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
            color = AppTheme.colors.textPrimary
        )

        Text(
            text = subTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = AppTheme.colors.textSecondary
        )

    }

}

@Composable
fun MiniStatementList(
    modifier: Modifier = Modifier,
    expenseList: List<Expense>
){

    if (expenseList.isNotEmpty()){
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(expenseList) { index,item ->
                TransactionDetail(
                    expense = item
                )
            }
        }
    }else{
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = AppTheme.colors.cardBackground)
        ){
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No Recent Transactions Found",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = AppTheme.colors.textSecondary
            )
        }
    }

}

@Composable
fun TransactionDetail(
    expense: Expense = Expense(
        amount = 2000.00,
        fromAccount = "ICICI",
        toAccount = "SBI",
        isCredited = false,
        date = "",
        category = ""
    )
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = AppTheme.colors.cardBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(shape = CircleShape)
                .background(color = AppTheme.colors.cardBackground)
        ){
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                modifier = Modifier
                    .size(24.dp).align(Alignment.Center),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "From: ",
                fontSize = 16.sp,
                color = AppTheme.colors.textPrimary
            )
            Text(
                text = "To: ",
                fontSize = 16.sp,
                color = AppTheme.colors.textPrimary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "ICICI Bank",
                fontSize = 16.sp,
                color = AppTheme.colors.textPrimary
            )
            Text(
                text = "Axis Bank",
                fontSize = 16.sp,
                color = AppTheme.colors.textPrimary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Rs.${expense.amount}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = if (expense.isCredited) AppTheme.colors.incomeGreen else AppTheme.colors.expenseRed
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = expense.date,
                color = AppTheme.colors.textPrimary
            )

        }

    }

}


@Preview(showBackground = true)
@Composable
fun Prev() {
    DashboardScreen(
        onAddButtonClicked = {

        }
    )
}