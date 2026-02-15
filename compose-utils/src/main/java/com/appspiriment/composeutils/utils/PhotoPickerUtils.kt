package com.appspiriment.composeutils.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.HorizontalSpacer
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.wrappers.UiColor
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.toUiImage
import java.io.File

// --- Models ---

data class PhotoPickerActions(
    val openGallery: () -> Unit,
    val openCamera: () -> Unit
)

fun interface PhotoCropContract {
    fun onHandleCrop(sourceUri: Uri)
}

data class PhotoPickerConfig(
    val cameraIcon: UiImage = Icons.Default.Camera.toUiImage(),
    val galleryIcon: UiImage = Icons.Default.BrowseGallery.toUiImage(),
    val removeIcon: UiImage = Icons.Default.Delete.toUiImage(tint = UiColor.ColorResource(R.color.error)),
    val cameraText: UiText = UiText.StringResource(R.string.camera),
    val galleryText: UiText = UiText.StringResource(R.string.gallery),
    val removeText: UiText = UiText.StringResource(R.string.remove_photo),
    val sheetContainerColor: Color = Color.Unspecified,
    val onImageRemoved: (() -> Unit)? = null
)

sealed class PickerInteraction {
    data class Default(val config: PhotoPickerConfig = PhotoPickerConfig()) : PickerInteraction()
    data class Custom(val onClick: () -> Unit) : PickerInteraction()
}

// --- Logic ---

@Composable
fun rememberPhotoPicker(
    onImagePicked: (Uri) -> Unit,
    onTriggerCrop: ((Uri) -> Unit)? = null
): PhotoPickerActions {
    val context = LocalContext.current
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val handleResult: (Uri) -> Unit = { uri ->
        if (onTriggerCrop != null) onTriggerCrop(uri) else onImagePicked(uri)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { handleResult(it) } }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) tempUri?.let { handleResult(it) }
    }

    val cameraPermissionLauncher = rememberPermissionRequest(
        permissions = listOf(
            AppPermission(android.Manifest.permission.CAMERA, "Camera", isRequired = true)
        ),
        onActionGranted = {
            val uri = createLibraryTempUri(context)
            tempUri = uri
            cameraLauncher.launch(uri)
        }
    )

    return remember {
        PhotoPickerActions(
            openGallery = {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            openCamera = cameraPermissionLauncher
        )
    }
}

private fun createLibraryTempUri(context: Context): Uri {
    val tempFile = File(context.cacheDir, "camera_capture_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile)
}
@Composable
fun AppCircleImagePicker(
    modifier: Modifier = Modifier,
    currentImage: Any?, // The initial URL or Resource
    size: Dp = 120.dp,
    borderStroke: BorderStroke = BorderStroke(2.dp, Appspiriment.colors.primary),
    onImageProcessed: (Uri) -> Unit,
    cropContract: PhotoCropContract? = null,
    interaction: PickerInteraction = PickerInteraction.Default(),
    imageContent: @Composable (data: Any?) -> Unit // Slot only needs the active data
) {
    var showInternalSheet by remember { mutableStateOf(false) }
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPicker = rememberPhotoPicker(
        onImagePicked = { uri ->
            pickedImageUri = uri
            onImageProcessed(uri)
        },
        onTriggerCrop = { rawUri ->
            if (cropContract != null) {
                cropContract.onHandleCrop(rawUri)
            } else {
                pickedImageUri = rawUri
                onImageProcessed(rawUri)
            }
        }
    )

    val onBoxClick = {
        when (interaction) {
            is PickerInteraction.Default -> showInternalSheet = true
            is PickerInteraction.Custom -> interaction.onClick()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .border(borderStroke, CircleShape)
                .clickable { onBoxClick() }
        ) {
            // Logic: Use the locally picked image if it exists,
            // otherwise fall back to the remote/current image.
            imageContent(pickedImageUri ?: currentImage)

            // Camera Overlay Icon
            Surface(
                color = Color.Black.copy(alpha = 0.4f),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(size / 4)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    val icon = if (interaction is PickerInteraction.Default) {
                        interaction.config.cameraIcon
                    } else {
                        Icons.Default.Camera.toUiImage(tint = Appspiriment.uiColors.primary)
                    }
                    AppsIcon(icon = icon, modifier = Modifier.size(size / 8))
                }
            }
        }
    }

    if (showInternalSheet && interaction is PickerInteraction.Default) {
        ImageSourceBottomSheet(
            config = interaction.config,
            hasActiveImage = pickedImageUri != null || (currentImage != null && currentImage != ""),
            onDismiss = { showInternalSheet = false },
            onCameraClick = {
                showInternalSheet = false
                photoPicker.openCamera()
            },
            onGalleryClick = {
                showInternalSheet = false
                photoPicker.openGallery()
            },
            onRemoveClick = {
                showInternalSheet = false
                pickedImageUri = null
                interaction.config.onImageRemoved?.invoke()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSourceBottomSheet(
    config: PhotoPickerConfig,
    hasActiveImage: Boolean,
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = if (config.sheetContainerColor != Color.Unspecified)
            config.sheetContainerColor else Appspiriment.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, top = 8.dp)
        ) {
            SourceItem(icon = config.cameraIcon, text = config.cameraText, onClick = onCameraClick)
            SourceItem(icon = config.galleryIcon, text = config.galleryText, onClick = onGalleryClick)

            if (config.onImageRemoved != null && hasActiveImage) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Appspiriment.colors.disabledText
                )
                SourceItem(
                    icon = config.removeIcon,
                    text = config.removeText,
                    textColor = Color.Red,
                    onClick = onRemoveClick
                )
            }
        }
    }
}

@Composable
private fun SourceItem(
    icon: UiImage,
    text: UiText,
    textColor: Color = Appspiriment.colors.onMainSurface,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppsIcon(icon = icon, modifier = Modifier.size(24.dp))
        HorizontalSpacer(width = 16.dp)
        AppspirimentText(
            text = text,
            color = textColor,
            style = Appspiriment.typography.textMedium
        )
    }
}