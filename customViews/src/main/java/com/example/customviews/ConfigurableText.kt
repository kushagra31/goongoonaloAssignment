package com.example.customviews

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

/**
 * Compose [Text] for our app
 * Use this when you want to show any text on UI
 * @param charSequence Text to be displayed
 * @param fontSize size of text in Integer
 * @param fontFamily Font family to be choose between [ConfigurableFontFamily.INTER] or [ConfigurableFontFamily.PUBLICA]
 * @param color text color when need to pass color from resources pass like
 * colorResources(id = color from resources) if color is backend driven do something like
 * val parsedColor = Color.parseColor("#XXXXXX") and then Color(value = parsedColor)
 * @param modifier an optional [Modifier] to configure visibility of text
 * @param isBold displayed text is in bold or normal
 * @param letterSpacing an optional letter spacing between fonts
 * @param textDecoration The decorations to paint on the text (e.g., an underline).
 * See [TextStyle.textDecoration].
 * @param textAlign The alignment of the text within the lines of the paragraph.
 * See [TextStyle.textAlign].
 * @param overflow How visual overflow should be handled.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if
 * necessary.
 */
@Composable
fun ConfigurableText(
    charSequence: CharSequence,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    val textAnnotated = charSequence as? AnnotatedString
    val textString = charSequence as? String
    if (textAnnotated != null) {
        Text(
            style = style,
            text = textAnnotated,
            color = color,
            textDecoration = textDecoration,
            textAlign = textAlign,
            overflow = overflow,
            maxLines = maxLines,
            modifier = modifier
        )
    } else if (textString != null) {
        Text(
            style = style,
            text = textString,
            color = color,
            textDecoration = textDecoration,
            textAlign = textAlign,
            overflow = overflow,
            maxLines = maxLines,
            modifier = modifier
        )
    }
}