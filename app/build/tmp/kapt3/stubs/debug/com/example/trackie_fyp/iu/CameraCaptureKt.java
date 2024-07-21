package com.example.trackie_fyp.iu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002\u001a,\u0010\u000e\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0002\u001aJ\u0010\u0011\u001a\u00020\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u0006H\u0002\u00a8\u0006\u0016"}, d2 = {"CameraCapture", "", "key", "", "userId", "onImageCaptured", "Lkotlin/Function1;", "Landroid/graphics/Bitmap;", "onError", "Landroidx/camera/core/ImageCaptureException;", "createTempFile", "Ljava/io/File;", "context", "Landroid/content/Context;", "processCapturedImage", "uri", "Landroid/net/Uri;", "takePicture", "imageCapture", "Landroidx/camera/core/ImageCapture;", "outputOptions", "Landroidx/camera/core/ImageCapture$OutputFileOptions;", "app_debug"})
public final class CameraCaptureKt {
    
    @androidx.compose.runtime.Composable
    public static final void CameraCapture(int key, int userId, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super android.graphics.Bitmap, kotlin.Unit> onImageCaptured, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super androidx.camera.core.ImageCaptureException, kotlin.Unit> onError) {
    }
    
    private static final void takePicture(androidx.camera.core.ImageCapture imageCapture, androidx.camera.core.ImageCapture.OutputFileOptions outputOptions, android.content.Context context, kotlin.jvm.functions.Function1<? super android.graphics.Bitmap, kotlin.Unit> onImageCaptured, kotlin.jvm.functions.Function1<? super androidx.camera.core.ImageCaptureException, kotlin.Unit> onError) {
    }
    
    private static final java.io.File createTempFile(android.content.Context context) {
        return null;
    }
    
    private static final void processCapturedImage(android.content.Context context, android.net.Uri uri, kotlin.jvm.functions.Function1<? super android.graphics.Bitmap, kotlin.Unit> onImageCaptured) {
    }
}