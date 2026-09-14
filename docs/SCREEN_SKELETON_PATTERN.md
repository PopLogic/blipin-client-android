# Screen Skeleton Pattern Guide

## Overview
This document describes the standardized screen skeleton pattern used across the Blipin application presentation layer. This pattern ensures consistency, maintainability, and scalability of screen implementation.

## Pattern Structure

Every screen module follows this consistent structure:

```
presentation/profile/presentation/[MODULE_NAME]/
├── [ModuleName]Screen.kt
├── [ModuleName]ScreenUiState.kt
├── [ModuleName]ScreenUiEffect.kt
├── [ModuleName]ScreenViewModel.kt
└── screen/
    └── [ModuleName]SuccessScreen.kt
```

## Components Explained

### 1. **[ModuleName]Screen.kt** - Main Screen Composable
The entry point for the screen. Responsibilities:
- Collect state from ViewModel
- Route state to appropriate content composables
- Render top app bar and Scaffold layout
- Handle navigation callbacks (onBack, etc.)

**Structure:**
```kotlin
@Composable
fun [ModuleName]Screen(
    onBack: () -> Unit,
    viewModel: [ModuleName]ScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    [ModuleName]ScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun [ModuleName]ScreenContent(...) {
    Scaffold(
        topBar = { [ModuleName]ScreenTopAppBar(...) },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is [ModuleName]ScreenUiState.Loading -> { /* Loading UI */ }
            is [ModuleName]ScreenUiState.Error -> { /* Error UI */ }
            is [ModuleName]ScreenUiState.Success -> { 
                [ModuleName]SuccessScreen(...)
            }
        }
    }
}

@Composable
private fun [ModuleName]ScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "[Title]".hardcoded(),
        onBack = onBack,
    )
}
```

### 2. **[ModuleName]ScreenUiState.kt** - State Definition
Sealed interface representing all possible UI states.

**Structure:**
```kotlin
sealed interface [ModuleName]ScreenUiState {
    data object Loading : [ModuleName]ScreenUiState
    data object Error : [ModuleName]ScreenUiState
    data class Success(
        val isLoading: Boolean,
        // Add screen-specific data fields here
    ) : [ModuleName]ScreenUiState
}
```

**Best Practices:**
- Keep states immutable (use `data class` or `data object`)
- Include only UI-relevant data in Success state
- Use `isLoading` flag for nested loading states
- Add error details when needed

### 3. **[ModuleName]ScreenUiEffect.kt** - One-Way Effects
Sealed interface for one-time events or side effects (navigation, snackbars, etc.)

**Structure:**
```kotlin
sealed interface [ModuleName]ScreenUiEffect {
    data object NavigateToDetail : [ModuleName]ScreenUiEffect
    data class ShowSnackbar(val message: String) : [ModuleName]ScreenUiEffect
}
```

**Common Effects:**
- Navigation events
- Snackbar messages
- Dialog triggers
- External state updates

### 4. **[ModuleName]ScreenViewModel.kt** - State Management
ViewModel extending `BaseViewModel<UiState, UiEffect>`.

**Structure:**
```kotlin
class [ModuleName]ScreenViewModel : BaseViewModel<[ModuleName]ScreenUiState, [ModuleName]ScreenUiEffect> {
    override val uiState = MutableStateFlow<[ModuleName]ScreenUiState>(
        [ModuleName]ScreenUiState.Success(false)
    )
    override val uiEffect = MutableSharedFlow<[ModuleName]ScreenUiEffect>()
    
    // Add business logic methods here
}
```

**Best Practices:**
- Initialize with appropriate default state (Loading, Success, or Error)
- Use `MutableStateFlow` for state
- Use `MutableSharedFlow` for effects
- Keep business logic separate from UI concerns
- Handle data loading, transformations, and validations

### 5. **screen/[ModuleName]SuccessScreen.kt** - Success State UI
Composable rendering the Success state content.

**Structure:**
```kotlin
@Composable
fun [ModuleName]SuccessScreen(
    uiState: [ModuleName]ScreenUiState.Success,
    innerPadding: PaddingValues,
    viewModel: [ModuleName]ScreenViewModel,
    modifier: Modifier = Modifier,
) {
    // Implement screen content here
    Column(modifier = modifier) {
    }
}
```

**Responsibilities:**
- Render the main content UI
- Handle user interactions (button clicks, list items, etc.)
- Call ViewModel methods for user actions
- Observe and react to effects from ViewModel

## Implementation Checklist

When implementing a new screen, follow this checklist:

- [ ] Create `[ModuleName]Screen.kt` with Scaffold and state routing
- [ ] Define `[ModuleName]ScreenUiState.kt` with Loading/Error/Success states
- [ ] Define `[ModuleName]ScreenUiEffect.kt` for side effects
- [ ] Implement `[ModuleName]ScreenViewModel.kt` with state initialization
- [ ] Create `screen/[ModuleName]SuccessScreen.kt` for Success UI
- [ ] Set appropriate title in TopAppBar using `.hardcoded()`
- [ ] Implement Loading and Error UI in main Screen
- [ ] Connect ViewModel methods to UI interactions
- [ ] Test state transitions and user interactions
- [ ] Document screen-specific behavior if needed

## Real-World Examples

### Example 1: Account Screen
**File:** `presentation/profile/presentation/account/AccountScreen.kt`

```kotlin
@Composable
fun AccountScreen(
    onBack: () -> Unit,
    viewModel: AccountScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    // ... routing logic
}
```

### Example 2: Comment Screen  
**File:** `presentation/profile/presentation/comment/CommentScreen.kt`

```kotlin
@Composable
fun CommentScreen(
    onBack: () -> Unit,
    viewModel: CommentScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    // ... routing logic
}
```

## Common Patterns

### Handling Loading States
```kotlin
is [ModuleName]ScreenUiState.Loading -> {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
```

### Handling Error States
```kotlin
is [ModuleName]ScreenUiState.Error -> {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Error loading content")
    }
}
```

### Collecting Effects in UI
```kotlin
LaunchedEffect(Unit) {
    viewModel.uiEffect.collect { effect ->
        when (effect) {
            is [ModuleName]ScreenUiEffect.NavigateToDetail -> onNavigate(effect.id)
            is [ModuleName]ScreenUiEffect.ShowSnackbar -> showSnackbar(effect.message)
        }
    }
}
```

## Current Implementations

The following screens use this skeleton pattern:

1. **Support** - `presentation/profile/presentation/support/`
2. **Account** - `presentation/profile/presentation/account/`
3. **Comment** - `presentation/profile/presentation/comment/`
4. **Place** - `presentation/profile/presentation/place/`
5. **Notification** - `presentation/profile/presentation/notification/`
6. **Developer** - `presentation/profile/presentation/developer/`

## Benefits of This Pattern

1. **Consistency** - All screens follow the same structure
2. **Predictability** - Developers know where to find code
3. **Scalability** - Easy to add features to existing screens
4. **Testability** - Clear separation of concerns enables unit testing
5. **Maintainability** - Changes are localized and easier to track
6. **Reusability** - Components can be easily extracted and shared

## Extending the Pattern

### Adding Business Logic
```kotlin
class [ModuleName]ScreenViewModel : BaseViewModel<...> {
    // Add use cases or repositories
    private val useCase = [ModuleName]UseCase()
    
    fun loadData() {
        // Implement business logic
    }
}
```

### Adding Complex State
```kotlin
data class Success(
    val items: List<Item> = emptyList(),
    val selectedItem: Item? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
) : [ModuleName]ScreenUiState
```

### Adding User Actions
```kotlin
sealed interface [ModuleName]ScreenUiAction {
    data class OnItemClicked(val id: String) : [ModuleName]ScreenUiAction
    data object OnRefresh : [ModuleName]ScreenUiAction
}
```

## FAQ

**Q: When should I use Loading vs. Error state?**
A: Use Loading when data is being fetched. Use Error when something goes wrong during fetching or processing.

**Q: Should I put all content in SuccessScreen or keep it in Screen?**
A: Keep structural layout (Scaffold, TopBar, state routing) in Screen. Put all content UI in SuccessScreen.

**Q: How do I handle user interactions?**
A: Call ViewModel methods from SuccessScreen, which update state or emit effects.

**Q: Can I have nested states?**
A: Use the `isLoading` flag in Success state for nested loading. Keep the main state simple.

**Q: How do I navigate between screens?**
A: Use UiEffect. Emit navigation effects from ViewModel, collect them in UI, and handle navigation.

## Related Documentation

- [BaseViewModel Documentation](../common/BaseViewModel.md)
- [Compose UI Patterns](../ui/ComposePatterns.md)
- [State Management Guide](../architecture/StateManagement.md)
