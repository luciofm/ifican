
 public void revealCode(View view) {
   int cx = (view.getLeft() + view.getRight()) / 2;
   int cy = (view.getTop() + view.getBottom()) / 2;
   float radius = text2.getWidth();

   text2.setVisibility(View.VISIBLE);

   ViewAnimationUtils
      .createCircularReveal(text2, cx, cy, 0, radius).start();
 }
