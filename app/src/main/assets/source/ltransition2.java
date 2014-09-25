

// onCreate
dog = getIntent.getExtras().getParcelable("DOG");

getWindow().getEnterTransition()
        .addListener(new TransitionAdapter() {
  @Override
  public void onTransitionEnd(Transition transition) {
    animTextIn();
    getWindow().getEnterTransition()
        .removeListener(this);
  }
});
