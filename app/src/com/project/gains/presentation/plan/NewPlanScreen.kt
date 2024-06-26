package com.project.gains.presentation.plan
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.data.Level
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.TrainingType
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.onboarding.components.NewPlanPage
import com.project.gains.theme.GainsAppTheme

/*
* Composable to combine all the OnBoarding components*/
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NewPlanScreen(
    createHandler: (CreateEvent) -> Unit,
    selectHandler: (SelectEvent) -> Unit,

    navController: NavController,
    clicked: Boolean?
) {
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
    ) {

                HorizontalPager(state = pagerState) { index ->
                    NewPlanPage(
                        selectHandler = selectHandler,
                        page = pages[index],
                        pagerState = pagerState,
                        createHandler = createHandler,
                        navController = navController, clicked = clicked
                    )
                }

    }
}

data class PlanPages(
    val title: String,
    val pages:MutableList<PlanPage>
)

data class PlanPage(
    val title: String,
    @DrawableRes val image: Int
)


val pages = listOf(
    PlanPages(
        "Select what you think is your level",
        mutableListOf<PlanPage>(
            PlanPage( title = Level.BEGINNER.toString(),
            image = R.drawable.pexels3),
            PlanPage( title = Level.INTERMEDIATE.toString(),
                image = R.drawable.pexels1),
            PlanPage( title = Level.EXPERT.toString(), image = R.drawable.pexels2))),
        PlanPages(
            "Select the training type",
            mutableListOf<PlanPage>(
                PlanPage( title = TrainingType.STRENGTH.toString(),
                    image = R.drawable.pexels4),
                PlanPage( title = TrainingType.CALISTHENICS.toString(),
                    image = R.drawable.pexels3),
                PlanPage( title =TrainingType.CROSSFIT.toString(), image = R.drawable.pexels2))),
        PlanPages(
            "Select the training plan period",
            mutableListOf<PlanPage>(
                    PlanPage( title = PeriodMetricType.WEEK.toString(),
                        image = R.drawable.pexels1),
                    PlanPage( title =PeriodMetricType.MONTH.toString(),
                        image = R.drawable.pexels2),
                    PlanPage( title = PeriodMetricType.YEAR.toString(), image = R.drawable.pexels3)))
    )

@Preview(showBackground = true)
@Composable
fun NewPlanPagePreview(){
    GainsAppTheme {
        NewPlanScreen({}, {},rememberNavController(), remember {
           false
        })
    }
}