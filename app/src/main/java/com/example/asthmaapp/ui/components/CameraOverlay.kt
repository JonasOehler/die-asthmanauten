import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CameraOverlay() {
    val overlayColor = Color.Black.copy(alpha = 0.6f)
    val width = 250.dp
    val height = 100.dp
    val cornerRadius = 16.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val holeWidth = width.toPx()
            val holeHeight = height.toPx()

            val left = (canvasWidth - holeWidth) / 2
            val top = (canvasHeight - holeHeight) / 2

            drawRect(
                color = overlayColor,
                size = size
            )

            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(left, top),
                size = Size(holeWidth, holeHeight),
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
                blendMode = BlendMode.Clear
            )
        }

        Box(
            modifier = Modifier
                .size(width, height)
                .align(Alignment.Center)
                .border(2.dp, Color.White, RoundedCornerShape(cornerRadius))
        )
    }
}