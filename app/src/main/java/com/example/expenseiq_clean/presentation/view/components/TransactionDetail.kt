package com.example.expenseiq_clean.presentation.view.components

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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

@Composable
@Preview(showBackground = true)
fun TranPreview(){
    TransactionDetailCard()
}