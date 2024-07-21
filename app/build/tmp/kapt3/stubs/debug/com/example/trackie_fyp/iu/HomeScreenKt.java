package com.example.trackie_fyp.iu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0007\u001a$\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a\"\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a$\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00072\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a \u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015H\u0007\u001a;\u0010\u0018\u001a\u00020\u0001\"\b\b\u0000\u0010\u0019*\u00020\u001a2\u0006\u0010\u001b\u001a\u0002H\u00192\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u001dH\u0007\u00a2\u0006\u0002\u0010\u001f\u001a\u001e\u0010 \u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001dH\u0007\u001a\b\u0010!\u001a\u00020\u0007H\u0002\u001a\b\u0010\"\u001a\u00020\u0007H\u0002\u001a\u001a\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u00072\u0006\u0010\'\u001a\u00020\u0007\u001a\f\u0010(\u001a\u0004\u0018\u00010)*\u00020%\u00a8\u0006*"}, d2 = {"CategoryBreakdownSection", "", "expenses", "", "Lcom/example/trackie_fyp/DataClass/Expense;", "HomeMonthDropdownMenu", "selectedMonth", "", "onMonthSelected", "Lkotlin/Function1;", "HomeScreen", "navController", "Landroidx/navigation/NavHostController;", "userId", "expenseViewModel", "Lcom/example/trackie_fyp/models/ExpenseViewModel;", "HomeYearDropdownMenu", "selectedYear", "onYearSelected", "SummarySection", "totalIncome", "", "totalExpenses", "remainingBudget", "SwipeToDismissItem", "T", "", "transaction", "onEdit", "Lkotlin/Function0;", "onDelete", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "TransactionItem", "getCurrentMonth", "getCurrentYear", "matchesMonthAndYear", "", "", "month", "year", "toDate", "Ljava/util/Date;", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navController, int userId, @org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.models.ExpenseViewModel expenseViewModel) {
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
    
    private static final int getCurrentMonth() {
        return 0;
    }
    
    private static final int getCurrentYear() {
        return 0;
    }
    
    public static final boolean matchesMonthAndYear(@org.jetbrains.annotations.NotNull
    java.lang.String $this$matchesMonthAndYear, int month, int year) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public static final java.util.Date toDate(@org.jetbrains.annotations.NotNull
    java.lang.String $this$toDate) {
        return null;
    }
    
    @androidx.compose.runtime.Composable
    private static final void HomeMonthDropdownMenu(int selectedMonth, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onMonthSelected) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void HomeYearDropdownMenu(int selectedYear, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onYearSelected) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SummarySection(double totalIncome, double totalExpenses, double remainingBudget) {
    }
    
    @android.annotation.SuppressLint(value = {"DefaultLocale"})
    @androidx.compose.runtime.Composable
    public static final void CategoryBreakdownSection(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.trackie_fyp.DataClass.Expense> expenses) {
    }
}