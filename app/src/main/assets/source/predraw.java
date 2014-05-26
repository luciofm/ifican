image.getViewTreeObserver()
        .addOnPreDrawListener(new ViewTreeObserver
                              .OnPreDrawListener() {
    @Override
    public boolean onPreDraw() {
        image.getViewTreeObserver()
                       .removeOnPreDrawListener(this);

        int[] screenLocation = new int[2];
        image.getLocationOnScreen(screenLocation);
        leftDelta = info.left - screenLocation[0];
        topDelta = info.top - screenLocation[1];

        widthScale = (float) info.width / image.getWidth();
        heightScale = (float) info.height / image.getHeight();

        runEnterAnimation();

        return true;
    }
});