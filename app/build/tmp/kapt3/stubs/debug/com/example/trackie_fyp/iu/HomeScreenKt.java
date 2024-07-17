package com.example.trackie_fyp.iu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0007\u001a\u001a\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a\b\u0010\n\u001a\u00020\u0001H\u0007\u001a2\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u001a \u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0007\u001a;\u0010\u0017\u001a\u00020\u0001\"\b\b\u0000\u0010\u0018*\u00020\u00192\u0006\u0010\u001a\u001a\u0002H\u00182\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u001cH\u0007\u00a2\u0006\u0002\u0010\u001e\u001a\u001e\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00192\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u001cH\u0007\u001a\b\u0010 \u001a\u00020\rH\u0002\u001a\b\u0010!\u001a\u00020\u000fH\u0002\u001a\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\u0003H\u0002\u001a\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003H\u0002\u001a\u001a\u0010$\u001a\u00020%*\u00020\r2\u0006\u0010&\u001a\u00020\r2\u0006\u0010\'\u001a\u00020\u000f\u001a\n\u0010(\u001a\u00020)*\u00020\r\u00a8\u0006*"}, d2 = {"CategoryBreakdownSection", "", "expenses", "", "Lcom/example/trackie_fyp/DataClass/Expense;", "HomeScreen", "navController", "Landroidx/navigation/NavHostController;", "expenseViewModel", "Lcom/example/trackie_fyp/models/ExpenseViewModel;", "HomeScreenPreview", "MonthYearDropdownMenu", "selectedMonth", "", "selectedYear", "", "onMonthYearSelected", "Lkotlin/Function2;", "SummarySection", "totalIncome", "", "totalExpenses", "remainingBudget", "SwipeToDismissItem", "T", "", "transaction", "onEdit", "Lkotlin/Function0;", "onDelete", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "TransactionItem", "getCurrentMonth", "getCurrentYear", "getMonthList", "getYearList", "matchesMonthAndYear", "", "month", "year", "toDate", "Ljava/util/Date;", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.models.ExpenseViewModel expenseViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MonthYearDropdownMenu(@org.jetbrains.annotations.NotNull
    java.lang.String selectedMonth, int selectedYear, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onMonthYearSelected) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final <T extends java.lang.Object>void SwipeToDismissItem(@org.jetbrains.annotations.NotNull
    T transaction, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TransactionItem(@org.jetbrains.annotations.NotNull
    java.lang.Object transaction, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit) {
    }
    
    private static final java.lang.String getCurrentMonth() {
        return null;
    }
    
    private static final int getCurrentYear() {
        return 0;
    }
    
    private static final java.util.List<java.lang.String> getMonthList() {
        return null;
    }
    
    private static final java.util.List<java.lang.Integer> getYearList() {
        return null;
    }
    
    public static final boolean matchesMonthAndYear(@org.jetbrains.annotations.NotNull
    java.lang.String $this$matchesMonthAndYear, @org.jetbrains.annotations.NotNull
    java.lang.String month, int year) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.util.Date toDate(@org.jetbrains.annotations.NotNull
    java.lang.String $this$toDate) {
        return null;
    }
    
    @androidx.compose.runtime.Composable
    public static final void SummarySection(double totalIncome, double totalExpenses, double remainingBudget) {
    }
    
    @android.annotation.SuppressLint(value = {"DefaultLocale"})
    @androidx.compose.runtime.Composable
    public static final void CategoryBreakdownSection(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.trackie_fyp.DataClass.Expense> expenses) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable
    public static final void HomeScreenPreview() {
    }
}