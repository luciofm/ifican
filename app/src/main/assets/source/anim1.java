  // Anima o tamanho e posição do thumb para o local final...
  image.animate().setDuration(duration)
    .scaleX(1).scaleY(1)
    .translationX(0).translationY(0)
    .setInterpolator(sDecelerator)
    .setListener(new SimpleAnimatorListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        // Anima o texto com slide up...
        text1.setTranslationY(text1.getHeight());
        text1.animate().setDuration(duration / 2)
            .translationY(0).alpha(1)
            .setInterpolator(sDecelerator);
      }
  });

  ObjectAnimator bgAnim = ObjectAnimator.ofInt(background, "alpha", 0, 255);
  bgAnim.setDuration(duration * 2);
  bgAnim.start();
