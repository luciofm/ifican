image.setVisibility(View.VISIBLE);
image.getViewTreeObserver()
	.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
    @Override
    public boolean onPreDraw() {
        image.getViewTreeObserver()
		.removeOnPreDrawListener(this);
        image.animate().alpha(1f)
		.scaleY(1f).scaleX(1f)
                .rotation(720)
		.setInterpolator(new AccelerateInterpolator())
                .setDuration(1000);
        return false;
    }
});
