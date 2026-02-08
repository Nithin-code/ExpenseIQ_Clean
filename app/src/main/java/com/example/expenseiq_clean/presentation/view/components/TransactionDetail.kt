package com.example.expenseiq_clean.presentation.view.components

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseiq_clean.R
import com.example.expenseiq_clean.ui.theme.AppTheme
import java.util.Date


@Composable
fun TransactionDetailCard(
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.colors.cardBackground
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "AXIS Bank",
                    fontSize = 16.sp,
                    color = AppTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${Date(System.currentTimeMillis())}",
                    fontSize = 12.sp,
                    color = AppTheme.colors.textSecondary
                )

            }

            Text(
                text = "Rs : ${1200}",
                fontSize = 14.sp,
                color = AppTheme.colors.expenseRed
            )

        }

    }

}

@Preview
@Composable
fun ExpenseDetailsCard(
    modifier: Modifier = Modifier,
    toAccount: String = "Zomato Order",
    fromAccount: String = "Credit card",
    spentOn: String = "Sun, 08 Feb ’26",
    amount: Double = 200.00,
    isDebit: Boolean = false
){

    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.cardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(
                        shape = CircleShape,
                    )
                    .border(
                        width = 0.5.dp,
                        color = AppTheme.colors.textPrimary,
                        shape = CircleShape
                    )
                    .background(color = AppTheme.colors.cardBackground.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${toAccount.toCharArray()[0]}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.textSecondary
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = toAccount,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "spent via $fromAccount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.textSecondary
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Rs. $amount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isDebit) AppTheme.colors.expenseRed else AppTheme.colors.incomeGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = spentOn,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppTheme.colors.textSecondary
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun TranPreview(){
    TransactionDetailCard()
}