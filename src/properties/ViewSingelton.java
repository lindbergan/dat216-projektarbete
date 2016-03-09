package properties;


//A custom Singelton class that can be used to to track which view is currently active
public class ViewSingelton {

    private static ViewSingelton instance;
    private String viewName;


    private ViewSingelton() {
    }

    public static ViewSingelton getInstance() {

        if (instance == null) {
            return instance = new ViewSingelton();
        }
        return instance;
    }

    public String getCurrentViewName() {
        return viewName;
    }

    public void setCurrentViewName(String name) {
        viewName = name;
    }
}
