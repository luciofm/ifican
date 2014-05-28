    private void showLabel() {
        int width = getCompoundDrawablesWidth();
        int height = mLabel.getHeight();
        mLabel.setVisibility(View.VISIBLE);
        mLabel.setAlpha(0f);
        mLabel.setTranslationY(height);
        mLabel.setTranslationX(width);
        mLabel.animate()
              .alpha(1f)
              .translationY(0f)
              .translationX(0f)
              .setDuration(ANIMATION_DURATION)
              .setListener(null).start();
    }
