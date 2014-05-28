public static void shakeView(final View view) {
    ObjectAnimator physX = ObjectAnimator.ofFloat(view, "translationX", -12f, 12f);
    physX.setDuration(50);
    physX.setRepeatCount(10);
    physX.setRepeatMode(ObjectAnimator.RESTART);
    physX.addListener(new SimpleAnimatorListener() {
        @Override
        public void onAnimationEnd(Animator animation) {
            view.animate()
	    	.translationX(0f).setDuration(10);
        }
    });
    physX.start();
}
