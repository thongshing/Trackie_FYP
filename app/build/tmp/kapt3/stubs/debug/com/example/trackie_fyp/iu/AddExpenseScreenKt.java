package com.example.trackie_fyp.iu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a5\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u00a2\u0006\u0002\u0010\n\u001a\b\u0010\u000b\u001a\u00020\u0001H\u0007\u001a4\u0010\f\u001a\u00020\u00012\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012H\u0007\u00a8\u0006\u0013"}, d2 = {"AddExpenseScreen", "", "navController", "Landroidx/navigation/NavHostController;", "expenseId", "", "expenseViewModel", "Lcom/example/trackie_fyp/models/ExpenseViewModel;", "categoryViewModel", "Lcom/example/trackie_fyp/models/CategoryViewModel;", "(Landroidx/navigation/NavHostController;Ljava/lang/Integer;Lcom/example/trackie_fyp/models/ExpenseViewModel;Lcom/example/trackie_fyp/models/CategoryViewModel;)V", "AddExpenseScreenPreview", "CategoryDropdownMenu", "selectedCategory", "Lcom/example/trackie_fyp/DataClass/Category;", "onCategorySelected", "Lkotlin/Function1;", "categories", "", "app_debug"})
public final class AddExpenseScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AddExpenseScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.Nullable
    java.lang.Integer expenseId, @org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.models.ExpenseViewModel expenseViewModel, @org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.models.CategoryViewModel categoryViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CategoryDropdownMenu(@org.jetbrains.annotations.Nullable
    com.example.trackie_fyp.DataClass.Category selectedCategory, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.trackie_fyp.DataClass.Category, kotlin.Unit> onCategorySelected, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.trackie_fyp.DataClass.Category> categories) {
    }
    
    @androidx.compose.runtime.Composable
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void AddExpenseScreenPreview() {
    }
}