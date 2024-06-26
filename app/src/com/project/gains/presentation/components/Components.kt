package com.project.gains.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries,
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.GymPost
import com.project.gains.data.Option
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.ProgressChartPreview
import com.project.gains.data.Song
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.TrainingType
import com.project.gains.data.Workout
import com.project.gains.data.bottomNavItems
import com.project.gains.data.generateOptions
import com.project.gains.presentation.events.CreateEvent
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.MusicEvent
import com.project.gains.presentation.events.SelectEvent
import com.project.gains.presentation.events.ShareContentEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.NewPlanScreen
import com.project.gains.theme.GainsAppTheme
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(45.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            tint = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun AddExerciseItem(
    exercise: Exercise,
    onItemClick: (Exercise) -> Unit,
    onItemClick2: () -> Unit,
    isSelected: Boolean,
    isToAdd: Boolean,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = exercise.gifResId ?: R.drawable.logo),
            contentDescription = exercise.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = exercise.name,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row( modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )  {
            if (isSelected){
            IconButton(onClick = {
                if (isToAdd){
                    onItemClick2()
                }else{
                    onItemClick(exercise)
                }
            }) {
                Icon(imageVector = if(isToAdd) Icons.Default.Add else Icons.Default.ArrowForwardIos, contentDescription = "Exercise Button", tint = MaterialTheme.colorScheme.surface)

            }}
        }
    }
}

@Composable
fun PeriodPopup(
    selectedPeriodMap: MutableList<PeriodMetricType>?,
    popupVisible: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onOptionSelected: (PeriodMetricType) -> Unit,
    selectedMetric: PeriodMetricType
) {
    if (popupVisible.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {

            },
            text = {
                Spacer(modifier = Modifier.height(10.dp))
                Column(Modifier.padding(top=20.dp)) {
                    selectedPeriodMap?.forEach { metric ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onOptionSelected(metric) }
                        ) {
                            Text(
                                text = metric.name,
                                color = if (metric == selectedMetric)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
            buttons = {
                Column {
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel",color = Color.Red)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(50.dp)
                                .background(Color.Gray)
                        )
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Optional padding to reduce width
        )
    }
}
@Composable
fun MusicPopup(popup: Boolean, musicHandler: (MusicEvent) -> Unit, currentSong: Song, totalTime: String) {
    if (popup) {
        val play = remember { mutableStateOf(false) }
        var currentTime by remember { mutableStateOf(0f) }
        val songTotalTime = 165f

        // Simulate a timer to update the current playback position
        LaunchedEffect(play.value) {
            if (play.value) {
                while (currentTime < songTotalTime && play.value) {
                    delay(100L) // Shorter delay for finer granularity
                    currentTime += 0.1f // Increment by 0.1 second

                    // Adjust the timer to handle seconds incrementing correctly
                    if (currentTime % 1.0f >= 0.9f) {
                        currentTime = (currentTime - (currentTime % 1.0f)) + 1f
                    }

                    if (currentTime >= songTotalTime) {
                        play.value = false
                        musicHandler(MusicEvent.Forward)
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(240.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(Color.Black)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spotify2),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Song: ${currentSong.title}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Artist: ${currentSong.singer}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Album: ${currentSong.album}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = currentTime / songTotalTime,
                    color = MaterialTheme.colorScheme.surface,
                    backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(16.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatTime(currentTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Text(
                        text = formatTime(songTotalTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            musicHandler(MusicEvent.Rewind)
                            currentTime = 0f
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription = "Rewind",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            play.value = !play.value
                            musicHandler(MusicEvent.Music)
                        }
                    ) {
                        Icon(
                            imageVector = if (play.value) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = if (play.value) "Stop" else "Play",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            musicHandler(MusicEvent.Forward)
                            currentTime = 0f
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "Forward",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

fun formatTime(time: Float): String {
    val totalSeconds = time.toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}


@Composable
fun ProgressChartCard(
    chartPreview: ProgressChartPreview,
    onItemClick: (ProgressChartPreview) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(chartPreview)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
        ),
        shape = RoundedCornerShape(8.dp) // Add rounded corners to the card
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = chartPreview.name,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = chartPreview.imageResId),
                contentDescription = null, // Provide a meaningful content description
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
fun SocialMediaIcon(icon: Int, onClick: () -> Unit, isSelected: Boolean) {
    androidx.compose.material.IconButton(onClick = onClick) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(6.dp) // Add padding between the border and the image icon
        ) {
            androidx.compose.material.Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(38.dp) // Adjust the size of the icon to fit within the padding
            )
        }
    }
}

@Composable
fun OptionCheckbox(
    option: Option,
    onOptionSelected: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.name,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    onOptionSelected(it)
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkedColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun TrainingOverviewChart(
    trainingData: List<TrainingData>,
    selectedMetric: TrainingMetricType,
    selectedPeriod: PeriodMetricType,
    shareHandler: (ShareContentEvent.SharePlot) -> Unit,
    selectedPlotType: ProgressChartPreview
) {
    val values= remember {
        mutableListOf<Float>()
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        trainingData.forEachIndexed { index, data ->
            values.add(data.value.toFloat())
        }
            when (selectedPlotType.imageResId) {
                R.drawable.plot2 -> {
                    // Bar plot (Vertical bar plot)
                    BarPlot(trainingData,selectedPeriod.toString(),selectedMetric.toString())
                }
                R.drawable.plot3 -> {
                    // Pie plot (Circular pie chart)
                    PiePlot(trainingData,selectedPeriod.toString(),selectedMetric.toString())
                }
            }
    }
}




@Composable
fun BarPlot(trainingData: List<TrainingData>, valueType: String, metricType: String) {
    val maxValue = trainingData.maxOf { it.value }
    val barSpacing: Dp = 4.dp
    val density = LocalDensity.current

    Canvas(modifier = Modifier
        .size(400.dp)
        .padding(20.dp)) {
        val totalBars = trainingData.size
        val totalSpacing = (totalBars - 1) * barSpacing.toPx()
        val barWidth = (size.width - totalSpacing) / totalBars
        val graphHeight = size.height

        // Draw X-axis label (Metric Type)
        drawIntoCanvas {
            val label = valueType
            val textWidth = with(density) { size.width.toDp() }
            val textHeight = with(density) { 16.dp.toPx() }

            it.nativeCanvas.drawText(
                label,
                (size.width - textWidth.toPx()) / 2,
                graphHeight + textHeight + 24.dp.toPx(),
                Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = density.run { 16f.sp.toPx() }
                    isAntiAlias = true
                }
            )
        }

        // Draw X-axis
        drawRoundRect(
            color = Color.Gray,
            topLeft = Offset(0f, graphHeight),
            size = androidx.compose.ui.geometry.Size(size.width, 4.dp.toPx()),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw Y-axis label
        drawIntoCanvas {
            val maxLabel = maxValue.toString()
            val textWidth = with(density) { 16.dp.toPx() }
            val textHeight = with(density) { 12.dp.toPx() }
            rotate(degrees = 90f, pivot = Offset(-textWidth -50 , textHeight + 900)) {

                it.nativeCanvas.drawText(
                    metricType,
                    -textWidth - 50,
                    textHeight + 900,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = density.run { 14f.sp.toPx() }
                        isAntiAlias = true
                    }
                )
            }

            it.nativeCanvas.drawText(
                maxLabel,
                -textWidth -50 ,
                 textHeight,
                Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = density.run { 14f.sp.toPx() }
                    isAntiAlias = true
                }
            )
        }

        // Draw Y-axis
        drawRoundRect(
            color = Color.Gray,
            topLeft = Offset(0f, 0f),
            size = androidx.compose.ui.geometry.Size(4.dp.toPx(), graphHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw X-axis labels (Bar indices)
        trainingData.forEachIndexed { index, data ->
            val startX = index * (barWidth + barSpacing.toPx())
            val label = "${index + 1}"

            drawIntoCanvas {
                val textWidth = with(density) { (barWidth - 8.dp.toPx()).toDp() }
                val textHeight = with(density) { 16.dp.toPx() }

                it.nativeCanvas.drawText(
                    label,
                    startX + (barWidth - textWidth.toPx()) / 2,
                    graphHeight + textHeight + 4.dp.toPx(),
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = density.run { 14f.sp.toPx() }
                        isAntiAlias = true
                    }
                )
            }
        }

        // Draw bars
        trainingData.forEachIndexed { index, data ->
            val barHeight = data.value / maxValue.toFloat()
            val startX = index * (barWidth + barSpacing.toPx())
            val startY = graphHeight * (1 - barHeight)

            // Random color generation for the bars
            val color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

            // Draw background bar
            drawRoundRect(
                color = Color.LightGray,
                topLeft = Offset(startX, startY),
                size = androidx.compose.ui.geometry.Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

            // Draw actual bar
            drawRoundRect(
                color = color,
                topLeft = Offset(startX, startY),
                size = androidx.compose.ui.geometry.Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )
        }
    }
}
@Composable
fun PiePlot(trainingData: List<TrainingData>, valueType: String, metricType: String) {
    val totalValue = trainingData.sumOf { it.value }
    val colorMap = HashMap<Int, Color>()
    Row(modifier = Modifier.padding(30.dp)) {
        // Canvas for pie plot
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = size.minDimension / 2
            val innerRadius = radius * 0.4f // Adjust inner radius to create the donut effect

            var startAngle = 0f
            val centerX = canvasWidth / 2
            val centerY = canvasHeight - radius / 2

            trainingData.forEachIndexed { index, data ->
                val sweepAngle = (data.value / totalValue.toFloat()) * 360

                // Calculate slice color
                val sliceColor =Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

                colorMap[index] = sliceColor

                // Draw pie segment
                drawArc(
                    color = sliceColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = radius - innerRadius)
                )

                // Calculate the middle angle for label position
                val middleAngle = startAngle + sweepAngle / 2
                val middleAngleRad = middleAngle * (PI / 180) // Convert to radians
                val labelRadius = (radius + innerRadius) / 1.3f

                val labelX = centerX + labelRadius * cos(middleAngleRad).toFloat()
                val labelY = centerY + labelRadius * sin(middleAngleRad).toFloat()

                // Draw percentage label
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "${(data.value / totalValue.toFloat() * 100).toInt()}%",
                        labelX,
                        labelY,
                        android.graphics.Paint().apply {
                            color = if (sliceColor != Color.Yellow) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                            textSize = 30f
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                        }
                    )
                }

                startAngle += sweepAngle
            }
        }

        Spacer(modifier = Modifier.width(30.dp))
        // Canvas for legend
        Canvas(modifier = Modifier.size(100.dp)) {
            val legendOffsetY = 20.dp.toPx()
            val legendBoxSize = 20.dp.toPx()
            val legendTextSize = 30f

            trainingData.forEachIndexed { index, data ->
                val legendX = 20.dp.toPx()
                val legendY = legendOffsetY + index * (legendBoxSize + 8.dp.toPx())

                // Legend box
                drawRoundRect(
                    color = colorMap[index] ?: Color.White,
                    topLeft = Offset(legendX, legendY),
                    size = androidx.compose.ui.geometry.Size(legendBoxSize, legendBoxSize),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                // Legend text
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "$valueType ${index + 1}",
                        legendX + legendBoxSize + 8.dp.toPx(),
                        legendY + legendBoxSize * 0.75f,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = legendTextSize
                            isAntiAlias = true
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun SocialMediaRow(
    icon: Int,
    isLinked: Boolean,
    linkHandler: (LinkAppEvent) -> Unit,
    clickedApps: MutableState<MutableList<Int>>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SocialMediaIcon(icon = icon, onClick = {  }, isSelected = isLinked)
        Spacer(modifier = Modifier.width(50.dp))
        if (isLinked || clickedApps.value.contains(icon)) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Linked Icon",
                modifier = Modifier.padding(20.dp)
            )
        } else if (!isLinked && !clickedApps.value.contains(icon)){
            IconButton(
                onClick =
                {
                    clickedApps.value = clickedApps.value.toMutableList().apply { add(icon) }
                    linkHandler(LinkAppEvent.LinkApp(icon)) },
                modifier = Modifier.size(60.dp),
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun GeneralCard(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .height(150.dp)
            .background(Color.Gray, RoundedCornerShape(16.dp))
            .clickable {
                onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Clip to the rounded corners
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 300f
                    ),
                    RoundedCornerShape(16.dp)
                )
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = currentRoute(navController)

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.onSurface,
        contentColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.height(64.dp) // Adjust the height of the BottomNavigation
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface
            val iconSize = 32.dp
            val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface

            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(iconSize), // Adjust the size of the icon
                            tint = iconColor
                        )
                        //Spacer(modifier = Modifier.height(6.dp)) // Add spacing between icon and text
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp, // Set the desired text size here
                        maxLines = 1, // Limit to one line to prevent overflow
                        color = textColor
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                alwaysShowLabel = true // This will hide labels when not selected
            )
        }
    }
}

@Composable
fun TopBar(navController: NavController, message: String, button: @Composable () -> Unit,button1: @Composable () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            button1()

            Text(
                text = message,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            button()
        }
    }
}
@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun NewPlanPagePopup(
    selectHandler:(SelectEvent)->Unit,
    showPopup: Boolean?, onItemClick: () -> Unit, navController: NavController,
    createHandler: (CreateEvent) -> Unit) {
    val allOptions = remember { generateOptions() } // List to store selected options
    val options = remember { mutableStateListOf<Option>() } // List to store selected options
    val selectedExerciseTypes =
        remember { mutableStateListOf<ExerciseType>() } // List to store selected options
    val selectedMetrics =
        remember { mutableStateListOf<TrainingMetricType>() } // List to store selected options
    val selectedMusic = remember { mutableStateOf(false) } // List to store selected options
    val selectedBackup = remember { mutableStateOf(false) } // List to store selected options

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {
        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }
    if (showPopup == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { selectHandler(SelectEvent.SelectPlanPopup(false)) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon"
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Create New Plan",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                item {
                    Text(
                        text = "Set the following options and press the generate plan button to create a personalized workout plan based on your needs.",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    androidx.compose.foundation.layout.Spacer(
                        modifier = androidx.compose.ui.Modifier.height(
                            10.dp
                        )
                    )
                }
                item {
                    Text(
                        text = "Choose if you want to have music while training",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) // Add padding for better spacing
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                RoundedCornerShape(16.dp)
                            ) // Optional background for emphasis
                            .padding(16.dp) // Inner padding for the text itself
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[0],
                        onOptionSelected = { isChecked ->
                            selectedMusic.value = true
                            onOptionSelected(
                                allOptions[0],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    androidx.compose.material.Text(
                        text = "Choose if you want to have backup on your workout",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) // Add padding for better spacing
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp) // Inner padding for the text itself
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[1],
                        onOptionSelected = { isChecked ->
                            selectedBackup.value = true
                            onOptionSelected(
                                allOptions[1],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    androidx.compose.material.Text(
                        text = "Choose the metrics to track in your progress",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) // Add padding for better spacing
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp) // Inner padding for the text itself
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[2],
                        onOptionSelected = { isChecked ->
                            selectedMetrics.add(TrainingMetricType.BPM)
                            onOptionSelected(
                                allOptions[2],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[3],
                        onOptionSelected = { isChecked ->
                            selectedMetrics.add(TrainingMetricType.KCAL)

                            onOptionSelected(
                                allOptions[3],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[4],
                        onOptionSelected = { isChecked ->
                            selectedMetrics.add(TrainingMetricType.DURATION)

                            onOptionSelected(
                                allOptions[4],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    androidx.compose.material.Text(
                        text = "Choose the muscle groups to include in your plan",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) // Add padding for better spacing
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp) // Inner padding for the text itself
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[5],
                        onOptionSelected = { isChecked ->
                            selectedExerciseTypes.add(ExerciseType.CHEST)
                            onOptionSelected(
                                allOptions[5],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[6],
                        onOptionSelected = { isChecked ->
                            selectedExerciseTypes.add(ExerciseType.BACK)

                            onOptionSelected(
                                allOptions[6],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[7],
                        onOptionSelected = { isChecked ->
                            selectedExerciseTypes.add(ExerciseType.SHOULDERS)

                            onOptionSelected(
                                allOptions[7],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[8],
                        onOptionSelected = { isChecked ->
                            selectedExerciseTypes.add(ExerciseType.ARMS)

                            onOptionSelected(
                                allOptions[8],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[9],
                        onOptionSelected = { isChecked ->
                            selectedExerciseTypes.add(ExerciseType.LEGS)
                            onOptionSelected(
                                allOptions[9],
                                isChecked
                            )
                        }
                    )
                }
                item {
                    com.project.gains.presentation.components.OptionCheckbox(
                        option = allOptions[10],
                        onOptionSelected = { isChecked ->
                            selectedExerciseTypes.add(ExerciseType.CORE)
                            onOptionSelected(
                                allOptions[10],
                                isChecked
                            )
                        }
                    )
                }


                item { Spacer(modifier = Modifier.height(10.dp)) }


                item {
                    Button(
                        onClick = {
                            createHandler(
                                CreateEvent.CreatePlan(
                                    selectedMetrics,
                                    selectedExerciseTypes,
                                    selectedMusic.value,
                                    selectedBackup.value
                                )
                            )

                            selectHandler(SelectEvent.SelectPlanPopup(false))
                            onItemClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = "GENERATE PLAN",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun ShareContentPagePopup(
    showPopup: MutableState<Boolean>, apps: MutableList<Int>, show: Boolean?,
    onItemClick: ()->Unit,
    navController: NavController ){
    var clickedApp by remember { mutableIntStateOf(1) }


    if (showPopup.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { showPopup.value=false }) {
                            Icon(imageVector = Icons.Default.Close , contentDescription = "Close Icon")
                        }
                    }  }

                item { Text(
                    text = "Share Your Content!",
                    style = MaterialTheme.typography.headlineMedium
                ) }
                item {
                    Text(
                        text = "Share Your Content with your friends showing your progress, your workouts and your plans",
                        style = MaterialTheme.typography.bodySmall,
                    ) }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item { Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
                    apps.forEach{app ->
                            SocialMediaIcon(icon = app, onClick = {
                                clickedApp = app
                            }, clickedApp == app)
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                } }

                item { Spacer(modifier = Modifier.height(20.dp)) }


                item {
                    if (apps.isEmpty()){
                        Text(
                            text = "You have no linked apps to link an app go to settings -> sharing preferences or click the link apps button below",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { showPopup.value = false
                                navController.navigate(Route.SettingScreen.route)},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                        ) {
                            Text(text = "LINK APPS")
                        }

                    }else{
                        Button(
                            onClick = {
                                onItemClick()
                                showPopup.value = false},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                        ) {
                            Text(text = "SHARE CONTENT")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun SetWorkoutPagePopup(
    selectHandler:(SelectEvent)-> Unit,
    showPopup: Boolean?, show: Boolean?, onItemClick: ()->Unit,
    onItemClick2: ()->Unit,
    generalViewModel:GeneralViewModel){
    var workoutTitle by remember { mutableStateOf(TextFieldValue("")) }
    val workoutTitleStored by generalViewModel.workoutTitle.observeAsState()
    val selectedExercises by generalViewModel.addedExercises.observeAsState()

     val removedExercises = remember {
         mutableStateOf(listOf<Exercise>())
     }


    if (showPopup==true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {

                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { selectHandler(SelectEvent.SelectPlanPopup(false))}) {
                            Icon(imageVector = Icons.Default.Close , contentDescription = "Close Icon")
                        }
                    }  }

                item { Text(
                    text = "Create Your Workout!",
                    style = MaterialTheme.typography.headlineMedium
                ) }
                item {
                    Text(
                        text = "Manually add each exercise to your workout day, press the button + to search for an exercise and then click save button to submit",
                        style = MaterialTheme.typography.bodySmall,
                    ) }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {

                        (if(workoutTitle.text=="") workoutTitleStored else workoutTitle)?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = { newValue ->
                                    workoutTitle = newValue
                                    selectHandler(SelectEvent.SelectWorkoutStored(newValue))
                                },
                                label = {
                                    Text(
                                        "Enter workout name...",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                shape = RoundedCornerShape(size = 20.dp),
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface), // Set the text color to white

                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                                )
                            )
                        }
                    Spacer(modifier = Modifier.height(16.dp))


                   Column(modifier = Modifier.fillMaxWidth(),
                       verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally) {
                       selectedExercises?.forEach {exercise ->
                           println(removedExercises)
                           if (!removedExercises.value.contains(exercise)) {
                               Row(
                                   horizontalArrangement = Arrangement.Start,
                                   verticalAlignment = Alignment.CenterVertically
                               ) {
                                   AddExerciseItem(
                                       modifier = Modifier.weight(1f),
                                       exercise = exercise,
                                       onItemClick = {},
                                       onItemClick2 = {},
                                       isSelected = true,
                                       isToAdd = false
                                   )
                                   Spacer(modifier = Modifier.width(5.dp))

                                   DeleteExerciseButton {
                                       removedExercises.value= listOf( exercise)
                                       selectHandler(SelectEvent.RemoveExerciseToAdd(exercise))
                                   }

                               }
                           }
                       }

                       Row(horizontalArrangement = Arrangement.Center,
                           verticalAlignment = Alignment.CenterVertically) {
                           TextButton(onClick = { /* Add exercise action */ }) {
                               Text(text = "Add new exercise", color = Color.Cyan)
                           }
                           Spacer(modifier = Modifier.width(80.dp))
                           Spacer(modifier = Modifier.width(20.dp))
                           AddExerciseButton {
                               onItemClick()

                           }
                       }
                   }
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    Button(
                        onClick = {
                            selectHandler(SelectEvent.SelectShowDialogWorkout(true))
                            selectHandler(SelectEvent.SelectPlanPopup(false))
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(text = "SAVE WORKOUT", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

            }
        }
    }
}

@Composable
fun PlanPagePopup(
    showPopup: Boolean?, workouts:MutableList<Workout>,
    selectHandler: (SelectEvent) -> Unit,
    createHandler: (CreateEvent) -> Unit,
    navController: NavController,
    generalViewModel: GeneralViewModel,
    showCompleteWorkout: Boolean?,
    showCompletePlan: Boolean?,) {


    val clicked by generalViewModel.clicked.observeAsState()// CREATE PLAN CLICKED
    val showPopup3 by generalViewModel.showPopup3.observeAsState()// LAST PLAN GENERATION SCREEN
    val showPopup4 by generalViewModel.showPopup4.observeAsState() // LAST  WORKOUT SET SCREEN


    if (showPopup == true) { // PLAN PAGE POPUP OR WORKOUT SET
                if (clicked == false && showPopup3 == false && showPopup4 == false) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(20.dp)
                            )
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(40.dp)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 290.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = { selectHandler(SelectEvent.SelectPlanPopup(false)) }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close Icon"
                                        )
                                    }
                                }
                            }
                            item {
                                Text(
                                    text = "Add Pre-Made Workout",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            item {
                                Text(
                                    text = "Choose a pre-made workout from our library or use the workout builder to create your own.",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                            item { Spacer(modifier = Modifier.height(10.dp)) }
                            item {
                                Button(
                                    onClick = {
                                        selectHandler(SelectEvent.SelectClicked(true))
                                        selectHandler(SelectEvent.SelectShowPopup4(false))
                                        selectHandler(SelectEvent.SelectShowPopup3(false))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp),
                                    //         colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)

                                ) {
                                    Text(text = "USE PLAN BUILDER")
                                }
                            }
                            item { Spacer(modifier = Modifier.height(10.dp)) }
                            item {
                                Button(
                                    onClick = {
                                        selectHandler(SelectEvent.SelectClicked(true))
                                        selectHandler(SelectEvent.SelectShowPopup4(true))
                                        selectHandler(SelectEvent.SelectShowPopup3(false))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp),
                                    // colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                                ) {
                                    Text(text = "ADD MANUAL WORKOUT")
                                }
                            }
                            workouts.forEach { workout ->
                                item {
                                    GeneralCard(
                                        imageResId = R.drawable.logo,
                                        title = workout.name,
                                        onItemClick = {
                                            selectHandler(SelectEvent.SelectWorkout(workout))
                                            navController.navigate(Route.WorkoutScreen.route)
                                        }
                                    )
                                }

                            }
                        }
                    }
                } else if (clicked == true && showPopup3 == false && showPopup4 == false) {
                 //INTERMEDIATE SCREEN
                        NewPlanScreen(createHandler, selectHandler,navController, showPopup3)

                } else if (clicked == true && showPopup3 == true && showPopup4 == false) {

                        // LAST SCREEN
                        NewPlanPagePopup(
                            selectHandler,
                            showPopup3,
                            {},
                            navController
                        ) {
                            selectHandler(SelectEvent.SelectShowDialogPlan(true))
                            selectHandler(SelectEvent.SelectShowPopup3(false)) }

                } else if (clicked == true && showPopup3 == false && showPopup4 == true) {

                        SetWorkoutPagePopup(
                            selectHandler,
                            showPopup4,
                            showCompleteWorkout,
                            {
                                selectHandler(SelectEvent.SelectIsToAdd(true))
                                navController.navigate(Route.TypedExerciseScreen.route)
                            },
                            generalViewModel = generalViewModel,
                            onItemClick2 = {
                                selectHandler(SelectEvent.SelectShowDialogWorkout(true))
                                selectHandler(SelectEvent.SelectClicked(true))
                                selectHandler(SelectEvent.SelectShowPopup4(false))
                                selectHandler(SelectEvent.SelectShowPopup3(true))
                            })
                    }

                }
}

@Composable
fun AddExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Cyan, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "+",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DeleteExerciseButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = Color.Red, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "-",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LogoUser(modifier: Modifier,res:Int, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(70.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(35.dp)),  // Half of the size to make it fully rounded
            contentScale = ContentScale.Crop
        )
    }
}



@Composable
fun NotificationCard(
    message: String,
    onClose: () -> Unit
) {
    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                androidx.compose.material.MaterialTheme.colors.secondary,
                RoundedCornerShape(16.dp)
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = androidx.compose.material.MaterialTheme.colors.secondary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            androidx.compose.material.Text(
                text = message,
                style = androidx.compose.material.MaterialTheme.typography.body2,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            androidx.compose.material.IconButton(onClick = onClose) {
                androidx.compose.material.Icon(
                    Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun FeedPost(gymPost: GymPost) {
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // User Info Section
            LogoUser(modifier = Modifier.size(60.dp), gymPost.userResourceId) {
                // Placeholder for user logo
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                androidx.compose.material.Text(
                    text = gymPost.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = gymPost.time,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.width(150.dp))
            Image(
                painter = painterResource(id = gymPost.randomSocialId),
                contentDescription = "Social Icon",
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

        }
        Spacer(modifier = Modifier.height(8.dp))

        // Image Section
        Image(
            painter = painterResource(id = gymPost.imageResourceId),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text Content Section
        androidx.compose.material.Text(
            text = gymPost.caption,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Separator

        // Interaction Section (Likes and Comments)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Likes Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                androidx.compose.material.Text(
                    text = gymPost.likes,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Comments Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Comment,
                    contentDescription = "Comment Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                androidx.compose.material.Text(
                    text = gymPost.comment,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

}


@Composable
fun WorkoutHeader(generalViewModel: GeneralViewModel) {
    val selectedLevel by generalViewModel.selectedLvl.observeAsState()
    val selectedLPeriod  by generalViewModel.selectedPeriod.observeAsState()
    val selectedTraining  by generalViewModel.selectedTrainingType.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material.Text(
            text = "GENERAL • $selectedTraining • TRAINING",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        androidx.compose.material.Text(
            text = "For gym • $selectedLevel",
            fontSize = 16.sp,

            )
        androidx.compose.material.Text(
            text = "For period • $selectedLPeriod",
            fontSize = 14.sp,
        )
        androidx.compose.material.Text(
            text = "Workouts done: 0",
            fontSize = 14.sp,
        )
    }
}

@Composable
fun WorkoutDaysList(workouts: MutableList<Workout>, onItemClick: () -> Unit) {

    val workoutsDays = mutableListOf<String>()

    val workoutsIds = mutableListOf<Int>()

    workouts.forEach { workout ->
        workoutsDays.add(workout.name)
        workoutsIds.add(workout.id)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.material.Text(
            text = "Workout days",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        workoutsDays.forEachIndexed { index, day ->
            androidx.compose.material.Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { onItemClick() },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material.Icon(
                        imageVector = if (index == 0) Icons.Default.Circle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (index == 0) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        androidx.compose.material.Text(
                            text = "workout day ${workoutsIds[index]}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        androidx.compose.material.Text(
                            text = day,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GifLoader(gif: Int) {

    val context = LocalContext.current


    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory()) // Use built-in decoder for Android P+
        }
        .build()
    val painter = // Adjust the size as needed
        rememberAsyncImagePainter(
            ImageRequest // Optional: Apply transformations
                .Builder(LocalContext.current).data(data = gif)
                .apply(block = fun ImageRequest.Builder.() {
                    size(Size.ORIGINAL) // Adjust the size as needed
                    transformations(CircleCropTransformation()) // Optional: Apply transformations
                }).build(), imageLoader = imageLoader
        )

    Image(
        painter = painter,
        contentDescription = null, // Provide content description if necessary
        modifier = Modifier.size(200.dp) // Example: Set image size
    )
}

@Composable
fun InstructionCard(text: String) {
    androidx.compose.material.Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            androidx.compose.material.Text(
                text = text,
                style = androidx.compose.material.MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun WarningCard(message: String) {
    androidx.compose.material.Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.errorContainer,
                RoundedCornerShape(16.dp)
            ),
        backgroundColor = MaterialTheme.colorScheme.errorContainer
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WarningAmber,
                contentDescription = "Warning",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ExerciseGif(exercise: Exercise, onClick: (Exercise) -> Unit) {
    val context = LocalContext.current
    val gifResourceId = R.drawable.gi

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(gifResourceId)
            .size(Size.ORIGINAL)
            .parameters(
                coil.request.Parameters.Builder()
                    .set("coil-request-animated", true)
                    .build()
            )
            .build(),
        contentDescription = "Exercise GIF",
        modifier = Modifier
            .size(200.dp)
            .clickable { onClick(exercise) }
            .graphicsLayer {
                // Add any additional transformations or animations if needed
            },
    )
}


@Preview
@Composable
fun previwe(){
    GainsAppTheme {
        FeedbackAlertDialog(
            title = "Content Shared",
            message = "",
            onDismissRequest = { /*TODO*/ },
            onConfirm = { /*TODO*/ },
            confirmButtonText ="" ,
            dismissButtonText = "",
            color = Color.Black,
            show = remember {
                mutableStateOf(true)
            }
        )
    }
}

@Composable
fun FeedbackAlertDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
    color: Color,
    show:MutableState<Boolean>
) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )  {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            text = {
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { show.value=false
                            onConfirm()
                                  },
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .size(60.dp),
                        colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm Icon",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(10.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            dismissButton = {
            },
            shape = RoundedCornerShape(20.dp),
        )
}
@Composable
fun SettingItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        androidx.compose.material.Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}



// PROGRESS SCREEN
@Composable
fun MetricPopup(
    selectedMetricMap: MutableList<TrainingMetricType>?,
    popupVisible: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onOptionSelected: (TrainingMetricType) -> Unit,
    selectedMetric: TrainingMetricType
) {
    if (popupVisible.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {

            },
            text = {
                Spacer(modifier = Modifier.height(10.dp))
                Column(Modifier.padding(top = 20.dp)) {
                    selectedMetricMap?.forEach { metric ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onOptionSelected(metric) }
                        ) {
                            Text(
                                text = metric.name,
                                color = if (metric == selectedMetric) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
            buttons = {
                Column {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = Color.Red)
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(50.dp)
                                .background(Color.Gray)
                        )
                        TextButton(
                            onClick = { popupVisible.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp) // Optional padding to reduce width
        )
    }
}


