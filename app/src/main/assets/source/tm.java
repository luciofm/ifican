public static void beginDelayedTransition(ViewGroup container) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        TransitionManager.beginDelayedTransition(container);
}

// https://github.com/guerwan/TransitionsBackport
// Backport para a versão 4.x API 14+
