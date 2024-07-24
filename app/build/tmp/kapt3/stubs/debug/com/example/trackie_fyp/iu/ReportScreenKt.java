package com.example.trackie_fyp.iu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a$\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007\u001a\u0014\u0010\u000f\u001a\u00020\u00102\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u001a\u0006\u0010\u0011\u001a\u00020\u0007\u00a8\u0006\u0012"}, d2 = {"ReportScreen", "", "navController", "Landroidx/navigation/NavHostController;", "dbHelper", "Lcom/example/trackie_fyp/DatabaseHelper;", "userId", "", "generateLineChartData", "Lcom/github/mikephil/charting/data/LineData;", "expenses", "", "Lcom/example/trackie_fyp/DataClass/Expense;", "month", "year", "generatePieChartData", "Lcom/github/mikephil/charting/data/PieData;", "getRandomColor", "app_debug"})
public final class ReportScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ReportScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DatabaseHelper dbHelper, int userId) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final com.github.mikephil.charting.data.LineData generateLineChartData(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.trackie_fyp.DataClass.Expense> expenses, int month, int year) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final com.github.mikephil.charting.data.PieData generatePieChartData(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.trackie_fyp.DataClass.Expense> expenses) {
        return null;
    }
    
    public static final int getRandomColor() {
        return 0;
    }
}