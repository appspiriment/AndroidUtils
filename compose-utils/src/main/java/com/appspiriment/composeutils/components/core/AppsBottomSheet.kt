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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.wrappers.toUiImage
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.theme.Appspiriment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsBottomSheet(
    showSheet: Boolean,
    state: SheetState,
    title: UiText? = null,
    dismissSheet: () -> Unit,
    containerColor: Color = Appspiriment.colors.background,
    showCloseButton: Boolean = true,
    showDragHandle: Boolean = true,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentArrangement: Arrangement.Vertical = Arrangement.Top,
    titleAlignment: Arrangement.Horizontal = Arrangement.Center,
    titlePadding: PaddingValues = PaddingValues(16.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    content: @Composable() (ColumnScope.() -> Unit)
) {
    val bottomSheetContent: @Composable() (ColumnScope.() -> Unit) = {
        BottomSheetContent(
            title = title,
            dismissSheet = dismissSheet,
            showCloseButton = showCloseButton,
            contentAlignment = contentAlignment,
            contentArrangement = contentArrangement,
            titlePadding = titlePadding,
            titleAlignment = titleAlignment,
            contentPadding = contentPadding,
            content = content,
        )
    }
    if (showSheet) {
        if (showDragHandle) {
            ModalBottomSheet(
                onDismissRequest = dismissSheet,
                sheetState = state,
                shape = shape,
                containerColor = containerColor,
            ) {
                bottomSheetContent()
            }
        } else {
            ModalBottomSheet(
                onDismissRequest = dismissSheet,
                sheetState = state,
                shape = shape,
                dragHandle = null,
                containerColor = containerColor,
            ) {
                bottomSheetContent()
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    title: UiText? = null,
    dismissSheet: () -> Unit,
    showCloseButton: Boolean = true,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    titleAlignment: Arrangement.Horizontal = Arrangement.Center,
    contentArrangement: Arrangement.Vertical = Arrangement.Top,
    titlePadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    content: @Composable() (ColumnScope.() -> Unit)
) {

    if (showCloseButton || title != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(titlePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (showCloseButton) Arrangement.SpaceBetween else titleAlignment
        ) {
            if (showCloseButton) {
                HorizontalSpacer(Appspiriment.sizes.paddingXLarge)
            }
            title?.let {
                MalayalamText(
                    text = it,
                    style = Appspiriment.typography.textLargeSemiBold
                )
            }
            if (showCloseButton) {
                AppsIcon(
                    icon = Icons.Default.Close.toUiImage(),
                    modifier = Modifier
                        .clickable { dismissSheet() }
                        .size(Appspiriment.sizes.iconStandardLarge)
                )
            }

        }
    } else {
        Spacer(modifier = Modifier.padding(titlePadding))
    }

    Column(
        horizontalAlignment = contentAlignment,
        verticalArrangement = contentArrangement,
        modifier = Modifier.padding(contentPadding)
    ) {
        content()
    }
}