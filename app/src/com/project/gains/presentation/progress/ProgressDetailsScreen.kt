package com.project.gains.presentation.progress

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.PeriodMetricType
import com.project.gains.data.PlotType
import com.project.gains.data.TrainingData
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateRandomTrainingData
import com.project.gains.presentation.settings.ShareContentViewModel


import com.project.gains.theme.GainsAppTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ProgressDetailsScreen(
    navController: NavController,
    shareContentViewModel: ShareContentViewModel
) {
    val linkedApps by shareContentViewModel.linkedApps.observeAsState()

    var selectedMetric by remember { mutableStateOf(TrainingMetricType.BPM) }
    var selectedPeriod by remember { mutableStateOf(PeriodMetricType.MONTH) }
    var selectedPlot by remember { mutableStateOf(PlotType.BAR) }

    val trainingData: MutableList<TrainingData> = generateRandomTrainingData(10).toMutableList()

    var expandedPeriod by remember { mutableStateOf(false) }
    var expandedMetric by remember { mutableStateOf(false) }
    var expandedPlot by remember { mutableStateOf(false) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
            ) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp)
                    ) {
                        // Period Column
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = 3.dp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ), shape = RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Spacer(modifier = Modifier.weight(0.3f))

                                    androidx.compose.material3.Text(
                                        text = selectedPeriod.toString(),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)  // Use weight to make the text flexible
                                    )
                                    androidx.compose.material3.IconButton(
                                        onClick = { expandedPeriod = !expandedPeriod }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown Icon"
                                        )
                                    }
                                }
                            }

                            DropdownMenu(
                                expanded = expandedPeriod,
                                onDismissRequest = { expandedPeriod = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                    .padding(10.dp)
                            ) {
                                PeriodMetricType.entries.forEach { period ->
                                    DropdownMenuItem(
                                        text = {
                                            androidx.compose.material3.Text(
                                                text = period.toString().take(15) + if (period.toString().length > 15) "..." else "",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp
                                                ),
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        },
                                        onClick = {
                                            selectedPeriod = period
                                            expandedPeriod = false
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .border(
                                                border = BorderStroke(
                                                    width = 3.dp,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                ), shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Metric Column
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = 3.dp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ), shape = RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Spacer(modifier = Modifier.weight(0.3f))

                                    androidx.compose.material3.Text(
                                        text = selectedMetric.toString(),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(5f)  // Use weight to make the text flexible
                                    )
                                    androidx.compose.material3.IconButton(
                                        onClick = { expandedMetric = !expandedMetric }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown Icon"
                                        )
                                    }
                                }
                            }

                            DropdownMenu(
                                expanded = expandedMetric,
                                onDismissRequest = { expandedMetric = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                    .padding(10.dp)
                            ) {
                                TrainingMetricType.entries.forEach { metric ->
                                    DropdownMenuItem(
                                        text = {
                                            androidx.compose.material3.Text(
                                                text = metric.toString().take(15) + if (metric.toString().length > 15) "..." else "",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp
                                                ),
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        },
                                        onClick = {
                                            selectedMetric = metric
                                            expandedMetric = false
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .border(
                                                border = BorderStroke(
                                                    width = 3.dp,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                ), shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Plot Column
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = 3.dp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ), shape = RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Spacer(modifier = Modifier.weight(0.3f))
                                    androidx.compose.material3.Text(
                                        text = selectedPlot.toString(),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)  // Use weight to make the text flexible
                                    )
                                    androidx.compose.material3.IconButton(
                                        onClick = { expandedPlot = !expandedPlot }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown Icon"
                                        )
                                    }
                                }
                            }

                            DropdownMenu(
                                expanded = expandedPlot,
                                onDismissRequest = { expandedPlot = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surface
                                    )
                            ) {
                                PlotType.entries.forEach { plot ->
                                    DropdownMenuItem(
                                        text = {
                                            androidx.compose.material3.Text(
                                                text = plot.toString().take(15) + if (plot.toString().length > 15) "..." else "",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp
                                                ),
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        },
                                        onClick = {
                                            selectedPlot = plot
                                            expandedPlot = false
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .border(
                                                border = BorderStroke(
                                                    width = 3.dp,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                ), shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                            }
                        }
                    }

                    TrainingOverviewChart(
                        trainingData = trainingData,
                        selectedMetric = selectedMetric,
                        selectedPeriod = selectedPeriod,
                        selectedPlotType = selectedPlot
                    )
                }
            }
        }
    }
}





@Composable
fun TrainingOverviewChart(
    trainingData: List<TrainingData>,
    selectedMetric: TrainingMetricType,
    selectedPeriod: PeriodMetricType,
    selectedPlotType: PlotType
) {
    val values= remember { mutableListOf<Float>() }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        trainingData.forEachIndexed { _, data ->
            values.add(data.value.toFloat())
        }
        when (selectedPlotType) {
            PlotType.BAR -> {
                // Bar plot (Vertical bar plot)
                BarPlot(trainingData, selectedPeriod.toString(), selectedMetric.toString())
            }
            PlotType.PIE -> {
                // Pie plot (Circular pie chart)
                PiePlot(trainingData, selectedPeriod.toString())
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
        .padding(20.dp)
    ) {
        val totalBars = trainingData.size
        val totalSpacing = (totalBars - 1) * barSpacing.toPx()
        val barWidth = (size.width - totalSpacing) / totalBars
        val graphHeight = size.height

        // Draw X-axis label (Metric Type)
        drawIntoCanvas {
            val textWidth = with(density) { size.width.toDp() }
            val textHeight = with(density) { 16.dp.toPx() }

            it.nativeCanvas.drawText(
                valueType,
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
            size = Size(size.width, 4.dp.toPx()),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw Y-axis label
        drawIntoCanvas {
            val maxLabel = maxValue.toString()
            val textWidth = with(density) { 16.dp.toPx() }
            val textHeight = with(density) { 12.dp.toPx() }
            rotate(degrees = 270f, pivot = Offset(-textWidth -20 , textHeight + 900)) {

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
            size = Size(4.dp.toPx(), graphHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw X-axis labels (Bar indices)
        trainingData.forEachIndexed { index, _ ->
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
                size = Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

            // Draw actual bar
            drawRoundRect(
                color = color,
                topLeft = Offset(startX, startY),
                size = Size(barWidth, graphHeight * barHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )
        }
    }
}

@Composable
fun PiePlot(trainingData: List<TrainingData>, valueType: String) {
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
                val sliceColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

                colorMap[index] = sliceColor

                // Draw pie segment
                drawArc(
                    color = sliceColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = radius - innerRadius)
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
                        Paint().apply {
                            color = if (sliceColor != Color.Yellow) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                            textSize = 30f
                            textAlign = Paint.Align.CENTER
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

            trainingData.forEachIndexed { index, _ ->
                val legendX = 20.dp.toPx()
                val legendY = legendOffsetY + index * (legendBoxSize + 8.dp.toPx())

                // Legend box
                drawRoundRect(
                    color = colorMap[index] ?: Color.White,
                    topLeft = Offset(legendX, legendY),
                    size = Size(legendBoxSize, legendBoxSize),
                    cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                // Legend text
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "$valueType ${index + 1}",
                        legendX + legendBoxSize + 8.dp.toPx(),
                        legendY + legendBoxSize * 0.75f,
                        Paint().apply {
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

@Preview(showBackground = true)
@Composable
fun ProgressDetailsScreenPreview() {
    val navController = rememberNavController()
    val shareContentViewModel: ShareContentViewModel = hiltViewModel()
    ProgressDetailsScreen(
        navController = navController,
        shareContentViewModel = shareContentViewModel
    )
}
