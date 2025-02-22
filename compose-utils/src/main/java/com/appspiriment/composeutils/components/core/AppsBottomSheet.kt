package com.appspiriment.composeutils.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.components.core.image.types.toUiImage
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.theme.Appspiriment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsBottomSheet(
    state: SheetState,
    title: UiText? = null,
    dismissSheet: () -> Unit,
    containerColor: Color = Appspiriment.colors.background,
    dismissible: Boolean = true,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = dismissSheet,
        sheetState = state,
        containerColor = containerColor,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if(dismissible)Arrangement.SpaceBetween else Arrangement.Center
        ) {
            if (dismissible) {
                HorizontalSpacer(Appspiriment.sizes.paddingXLarge)
            }
            title?.let {
                MalayalamText(
                    text = it,
                    style = Appspiriment.typography.textLargeSemiBold
                )
            }
            if (dismissible) {
                AppsIcon(
                    icon = Icons.Default.Close.toUiImage(),
                    modifier = Modifier.clickable { dismissSheet() })
            }

        }
        Column(
            horizontalAlignment = contentAlignment,
            verticalArrangement = contentArrangement,
            modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}