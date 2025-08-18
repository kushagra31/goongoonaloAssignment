package com.example.customviews

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ConfigurableButtonType {
    SMALL,
    MEDIUM
}

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    type: ConfigurableButtonType = ConfigurableButtonType.SMALL,
    buttonText: String,
    @DrawableRes buttonIcon: Int? = null,
    textColor: Color = MaterialTheme.colorScheme.primary,
    bottomMargin: Dp = 8.dp,
    shouldShowLoader: Boolean = false,
    isEnabled: Boolean = true,
    backgroundColor: Color = Color.Green,
    onClick: () -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(
            4.dp,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(bottom = bottomMargin)
            .height(if (type == ConfigurableButtonType.SMALL) 36.dp else 44.dp)
            .clip(shape = RoundedCornerShape(46.dp))
            .background(color = if (isEnabled) backgroundColor else backgroundColor.copy(alpha = 0.5f))
            .clickable {
                if (isEnabled) {
                    onClick.invoke()
                }
            }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        buttonIcon?.let {
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = it),
                contentDescription = "Save",
                colorFilter = ColorFilter.tint(textColor)
            )
        }

        ConfigurableText(
            charSequence = buttonText,
            style = MaterialTheme.typography.titleSmall,
            color = textColor,
        )

        if (shouldShowLoader) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    type: ConfigurableButtonType = ConfigurableButtonType.SMALL,
    buttonText: String,
    @DrawableRes secondaryIcon: Int? = null,
    bottomMargin: Dp = 8.dp,
    textTint: Color? = null,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            4.dp,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(bottom = bottomMargin)
            .height(if (type == ConfigurableButtonType.SMALL) 36.dp else 44.dp)
            .clip(shape = RoundedCornerShape(46.dp))
            .background(color = backgroundColor)
            .clickable {
                onClick.invoke()
            }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        secondaryIcon?.let {
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = it),
                contentDescription = "cancel",
                colorFilter = ColorFilter.tint(color = textTint
                    ?: run { MaterialTheme.colorScheme.onBackground }
                )
            )
        }
        ConfigurableText(
            charSequence = buttonText,
            style = MaterialTheme.typography.titleSmall,
            color = textTint ?: run { MaterialTheme.colorScheme.onBackground },
        )
    }
}

@Composable
fun WarningButton(
    modifier: Modifier = Modifier,
    type: ConfigurableButtonType = ConfigurableButtonType.SMALL,
    buttonText: String,
    @DrawableRes buttonIcon: Int? = null,
    @ColorRes tint: Color = MaterialTheme.colorScheme.inversePrimary,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            4.dp,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(bottom = 8.dp)
            .height(if (type == ConfigurableButtonType.SMALL) 36.dp else 44.dp)
            .clip(shape = RoundedCornerShape(46.dp))
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .clickable {
                onClick.invoke()
            }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        buttonIcon?.let {
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = it),
                contentDescription = "Save",
                colorFilter = ColorFilter.tint(color = tint)
            )
        }

        ConfigurableText(
            charSequence = buttonText,
            style = MaterialTheme.typography.titleSmall,
            color = tint,
        )
    }
}