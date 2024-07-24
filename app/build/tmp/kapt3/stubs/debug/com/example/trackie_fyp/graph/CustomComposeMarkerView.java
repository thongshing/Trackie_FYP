package com.example.trackie_fyp.graph;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B&\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\b\b\u00a2\u0006\u0002\u0010\tJ \u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J\b\u0010\u0016\u001a\u00020\rH\u0016J\u0018\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J\u001c\u0010\u0018\u001a\u00020\u00072\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016R\u001f\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\b\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/trackie_fyp/graph/CustomComposeMarkerView;", "Lcom/github/mikephil/charting/components/IMarker;", "context", "Landroid/content/Context;", "content", "Lkotlin/Function1;", "Lcom/github/mikephil/charting/data/Entry;", "", "Landroidx/compose/runtime/Composable;", "(Landroid/content/Context;Lkotlin/jvm/functions/Function1;)V", "markerBitmap", "Landroid/graphics/Bitmap;", "offset", "Lcom/github/mikephil/charting/utils/MPPointF;", "paint", "Landroid/graphics/Paint;", "draw", "canvas", "Landroid/graphics/Canvas;", "posX", "", "posY", "getOffset", "getOffsetForDrawingAtPoint", "refreshContent", "e", "highlight", "Lcom/github/mikephil/charting/highlight/Highlight;", "app_debug"})
public final class CustomComposeMarkerView implements com.github.mikephil.charting.components.IMarker {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.github.mikephil.charting.data.Entry, kotlin.Unit> content = null;
    @org.jetbrains.annotations.Nullable
    private android.graphics.Bitmap markerBitmap;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint paint = null;
    @org.jetbrains.annotations.NotNull
    private com.github.mikephil.charting.utils.MPPointF offset;
    
    public CustomComposeMarkerView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.github.mikephil.charting.data.Entry, kotlin.Unit> content) {
        super();
    }
    
    @java.lang.Override
    public void refreshContent(@org.jetbrains.annotations.Nullable
    com.github.mikephil.charting.data.Entry e, @org.jetbrains.annotations.Nullable
    com.github.mikephil.charting.highlight.Highlight highlight) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.github.mikephil.charting.utils.MPPointF getOffset() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.github.mikephil.charting.utils.MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return null;
    }
    
    @java.lang.Override
    public void draw(@org.jetbrains.annotations.NotNull
    android.graphics.Canvas canvas, float posX, float posY) {
    }
}