package com.project.gains.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.project.gains.presentation.Dimension.MediumPadding1
import com.project.gains.presentation.Dimension.MediumPadding2
import com.project.gains.presentation.onboarding.Page
import com.project.gains.presentation.onboarding.pages

import com.project.gains.R
import com.project.gains.theme.GainsAppTheme

/*
* On boarding page composable, is the onboarding pages shown the first time you enter in the application
* */
@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(modifier = modifier){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = page.title,
            modifier=Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
        )
        Text(
            text = page.description,
            modifier=Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.bodyMedium,
        )

    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview(){
    GainsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}