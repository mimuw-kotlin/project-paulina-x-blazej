
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class TicketsUiTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun `test adding and deleting tickets`() =
        runComposeUiTest {
            setContent {
                var text by remember { mutableStateOf("Hello") }
                Text(
                    text = text,
                    modifier = Modifier.testTag("text")
                )
                Button(
                    onClick = { text = "Compose" },
                    modifier = Modifier.testTag("button")
                ) {
                    Text("Click me")
                }
            }

            // Tests the declared UI with assertions and actions of the Compose Multiplatform testing API
            onNodeWithTag("text").assertTextEquals("Hello")
            onNodeWithTag("button").performClick()
            onNodeWithTag("text").assertTextEquals("Compose")
        }
}