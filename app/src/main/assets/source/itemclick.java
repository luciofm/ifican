public class ViewInfo {
    public int left;
    public int top;
    public int width;
    public int height;
}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Dog dog = (Dog) view.getTag();
    ViewInfo info = new ViewInfo(view);

    Intent intent = new Intent(getActivity(), TransitionActivity.class);
    intent.putExtra("DOG", dog);
    intent.putExtra("INFO", info);
    startActivity(intent);
    getActivity().overridePendingTransition(0, 0);
}
