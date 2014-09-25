

  public void animateTextIn() {
    final long duration = Utils.ANIM_DURATION;
    text1.setVisibility(View.VISIBLE);
    text1.setAlpha(0);
    text1.setTranslationY(text1.getHeight());
    text1.animate().setDuration(duration / 2)
         .translationY(0).alpha(1)
         .setInterpolator(sDecelerator);
  }
