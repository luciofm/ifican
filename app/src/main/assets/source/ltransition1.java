// getView on Adapter
view.setViewName(dog.getname());

// OnItemClickListener
Dog dog = (Dog) view.getTag();

Intent intent = new Intent(this,
        LTransitionActivity.class);
intent.putExtra("DOG", dog);

ActivityOptions options; =
options = ActivityOptions
             .makeSceneTransitionAnimation(this,
                             view, "photo_hero");
startActivity(intent, options.toBundle());
